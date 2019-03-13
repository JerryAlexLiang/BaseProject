package liang.com.baseproject.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.View.ZhihuView;
import liang.com.baseproject.adapter.ZhihuListAdapter;
import liang.com.baseproject.base.BasePresenter;
import liang.com.baseproject.entity.ZhihuLastNewsRes;
import liang.com.baseproject.retrofit.BaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;

/**
 * 创建日期：2019/3/7 on 14:19
 * 描述: 知乎日报  ---  根据时间戳进行分页加载
 * 作者: liangyang
 */
public class ZhihuPresenter extends BasePresenter<ZhihuView> {

    private Context context;
    private ZhihuLastNewsRes zhihuData;
    private ZhihuView zhihuView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private boolean isLoadMore = false; // 是否加载过更多
    //适配器
    private ZhihuListAdapter zhihuListAdapter;

    public ZhihuPresenter(Context context) {
        this.context = context;
    }

    //请求知乎最新消息
    public void getZhihuLatestData() {
        zhihuView = getView();

        if (zhihuView != null) {
            recyclerView = zhihuView.getRecyclerView();
            linearLayoutManager = zhihuView.getLinearLayoutManager();

            //请求网络数据
            RetrofitHelper
                    .getInstanceChangeBaseUrl(UrlConstants.ZHIHU_BASE_URL)
                    .getMyService()
                    .getZhihuLatestData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<ZhihuLastNewsRes>() {
                        @Override
                        public void onNext(ZhihuLastNewsRes zhihuLastNewsRes) {
                            super.onNext(zhihuLastNewsRes);
                            disPlayZhihuLatestData(zhihuLastNewsRes);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            loadError(e);
                        }
                    });

        }
    }

    //请求过往消息
    public void getZhihuBeforeData(String time) {
        zhihuView = getView();

        if (zhihuView != null) {
            recyclerView = zhihuView.getRecyclerView();
            linearLayoutManager = zhihuView.getLinearLayoutManager();

            //请求网络数据
            RetrofitHelper
                    .getInstanceChangeBaseUrl(UrlConstants.ZHIHU_BASE_URL)
                    .getMyService()
                    .getZhihuBeforeData(time)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<ZhihuLastNewsRes>() {
                        @Override
                        public void onNext(ZhihuLastNewsRes zhihuLastNewsRes) {
                            super.onNext(zhihuLastNewsRes);
                            disPlayZhihuLatestData(zhihuLastNewsRes);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            loadError(e);
                        }
                    });
        }
    }

    private void loadError(Throwable e) {
        e.printStackTrace();
        zhihuView.setDataRefresh(false);
        zhihuView.setToastShow("接口异常 " + e.getMessage());
    }

    String time;

    private void disPlayZhihuLatestData(ZhihuLastNewsRes zhihuLastNewsRes) {
        if (isLoadMore) {
            //加载更多
            if (time == null) {
                //不更新
                zhihuListAdapter.updateLoadStatus(zhihuListAdapter.LOAD_NONE);
                zhihuView.setDataRefresh(false);
                return;
            } else {
                //有数据追加
                zhihuData.getStories().addAll(zhihuLastNewsRes.getStories());
            }
            //刷新适配器
            zhihuListAdapter.notifyDataSetChanged();
        } else {
            //不加载更多
            zhihuData = zhihuLastNewsRes;
            zhihuListAdapter = new ZhihuListAdapter(context, zhihuData);
            recyclerView.setAdapter(zhihuListAdapter);
            zhihuListAdapter.notifyDataSetChanged();
        }
        zhihuView.setDataRefresh(false);
        //更新时间数据
        time = zhihuLastNewsRes.getDate();
    }

    /**
     * RecyclerView滑动监听事件(结合底部布局刷新视图)
     */
    public void scrollRecycleViewListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 判断是否滑动了最后一个 Item
             */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (linearLayoutManager.getItemCount() == 1) {
                        //底部视图刷新方式-无
                        zhihuListAdapter.updateLoadStatus(zhihuListAdapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == linearLayoutManager.getItemCount()) {
                        //底部视图刷新方式-上拉刷新
                        zhihuListAdapter.updateLoadStatus(zhihuListAdapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        //底部视图刷新方式-加载更多
                        zhihuListAdapter.updateLoadStatus(zhihuListAdapter.LOAD_MORE);
                        //请求数据，请求过往消息
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getZhihuBeforeData(time);
//                            }
//                        }, 1000);
                        new Handler().postDelayed(() -> getZhihuBeforeData(time), 1000);
                    }
                }
            }

            /**
             * 在这里获取到 RecyclerView 的最后一个 Item 的位置
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
