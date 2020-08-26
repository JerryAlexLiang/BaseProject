package liang.com.baseproject.presenter;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.View.NiceGankView;
import liang.com.baseproject.adapter.NiceGankAdapter;

import com.liang.module_core_java.mvp.MVPBasePresenter;

import liang.com.baseproject.entity.GankRes;
import liang.com.baseproject.entity.NiceGankRes;

import com.liang.module_core_java.retrofit.BaseObserver;
import com.liang.module_core_java.retrofit.RetrofitHelper;

import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.retrofit.UrlService;

/**
 * 创建日期：2019/2/25 on 19:21
 * 描述: 干货集中营API- 颜如玉View （另一MVP模式写法）
 * 作者: liangyang
 */
public class NiceGankPresenter extends MVPBasePresenter<NiceGankView> {

    private Context context;
    private List<GankRes> list;
    private int page = 1;     //根据页数进行分页加载
    private int lastVisibleItem;
    private boolean isLoadMore = false; // 是否加载过更多
    private NiceGankView niceGankView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private NiceGankAdapter niceGankAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public NiceGankPresenter(Context context) {
        this.context = context;
    }

    /**
     * 颜如玉  干货-福利API
     */
    public void getNiceGankData() {
        niceGankView = getView();

        if (niceGankView != null) {
            recyclerView = niceGankView.getRecyclerView();
            gridLayoutManager = niceGankView.getGridLayoutManager();

            if (isLoadMore) {
                page = page + 1;
            }

            //请求网络数据
            RetrofitHelper
//                    .getGankSingletonInstance(UrlConstants.GANK_BASE_URL)
                    .getInstanceChangeBaseUrl(UrlConstants.GANK_BASE_URL);
            RetrofitHelper
                    .getMyService(UrlService.class)
                    .getNiceGankData(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<NiceGankRes>() {
                        @Override
                        public void onNext(NiceGankRes niceGankRes) {
                            super.onNext(niceGankRes);
                            displayData(niceGankRes);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            loadError(e);
                        }
                    });
        }

    }

    /**
     * RecyclerView滑动监听事件
     */
    public void scrollRecycleViewListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    if (gridLayoutManager.getItemCount() == 1) {
                        return;
                    }
                    if (lastVisibleItem + 1 == gridLayoutManager.getItemCount()) {
                        //设置SwipeRefreshLayout刷新
                        niceGankView.setDataRefresh(true);
                        isLoadMore = true;
                        new Handler().postDelayed(() -> getNiceGankData(), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    private void loadError(Throwable e) {
        e.printStackTrace();
        niceGankView.setDataRefresh(false);
        niceGankView.setToastShow("接口异常 " + e.getMessage());
    }

    private void displayData(NiceGankRes niceGankRes) {
        List<GankRes> gankResList = niceGankRes.getResults();

        if (isLoadMore) {
            if (gankResList == null) {
                niceGankView.setDataRefresh(false);
                return;
            } else {
                list.addAll(gankResList);
            }
            //刷新适配器
            niceGankAdapter.notifyDataSetChanged();
        } else {
            list = gankResList;
            niceGankAdapter = new NiceGankAdapter(context, list);
            recyclerView.setAdapter(niceGankAdapter);
            niceGankAdapter.notifyDataSetChanged();
        }
        niceGankView.setDataRefresh(false);
    }

    public void swipeRefreshListener() {
        niceGankView = getView();
        if (niceGankView != null) {
            swipeRefreshLayout = niceGankView.getSwipeRefreshLayout();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    isLoadMore = false;
                    page = 1;
                    new Handler().postDelayed(() -> getNiceGankData(), 1000);
                }
            });
        }
    }
}
