package liang.com.baseproject.home.fragment;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import com.liang.module_core.mvp.MVPBaseFragment;
import liang.com.baseproject.home.adapter.HomeContainerAdapter;
import liang.com.baseproject.home.entity.ArticleBean;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.presenter.HomeContainerPresenter;
import liang.com.baseproject.home.view.HomeContainerView;
import liang.com.baseproject.main.activity.AgentWebActivity;
import com.liang.module_core.utils.JsonFormatUtils;
import com.liang.module_core.utils.LogUtil;

/**
 * 创建日期：2019/3/7 on 13:23
 * 描述: 玩Android 开放API - 首页相关
 * 作者: liangyang
 */
public class HomeContainerFragment extends MVPBaseFragment<HomeContainerView, HomeContainerPresenter> implements HomeContainerView {

    private static final String TAG = HomeContainerFragment.class.getSimpleName();
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;

    private MainHomeActivity mActivity;

    //        private static final int PAGE_START = 376;
    private static final int PAGE_START = 0;
    private int currPage = PAGE_START;
    private HomeContainerAdapter homeContainerAdapter;
    private boolean setRefreshFooter;

    public HomeContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainHomeActivity) context;
    }

    @Override
    protected HomeContainerPresenter createPresenter() {
        return new HomeContainerPresenter();
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_home_container;
    }

    @Override
    protected void initView(View rootView) {
        //绑定View
        mPresenter.attachView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvHome.setLayoutManager(linearLayoutManager);
        //初始化适配器
        homeContainerAdapter = new HomeContainerAdapter();
        homeContainerAdapter.setEnableLoadMore(false);
        //开启动画
        homeContainerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvHome.setAdapter(homeContainerAdapter);

        mPresenter.getArticleList(PAGE_START);

        homeContainerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean item = homeContainerAdapter.getItem(position);
                if (item != null) {
                    LogUtil.d(TAG, "点击了:  " + Objects.requireNonNull(item).getTitle());
//                    AgentWebActivity.actionStart(getContext(), item.getId(), item.getTitle(), item.getLink());
                    AgentWebActivity.actionStart(getContext(), item);
                }
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currPage = PAGE_START;
                mPresenter.getArticleList(currPage);
            }
        });

        setRefreshFooter = isSetRefreshFooter();
        if (setRefreshFooter) {
            smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    currPage++;
                    mPresenter.getArticleList(currPage);
                }
            });
        } else {
            homeContainerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    currPage++;
                    mPresenter.getArticleList(currPage);
                }
            }, rvHome);
        }

        //自动刷新(替代第一次请求数据)
        smartRefreshLayout.autoRefresh();

        //添加头部
        View headerView = getLayoutInflater().inflate(R.layout.core_layout_web_error, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        homeContainerAdapter.addHeaderView(headerView);
        //默认出现了头部就不会显示Empty，和尾部  配置以下方法也支持同时显示setHeaderAndEmpty  setHeaderFooterEmpty
        homeContainerAdapter.setHeaderAndEmpty(true);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return true;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "执行onResume()");
        //同步刷新背景
        getSmartRefreshPrimaryColorsTheme(smartRefreshLayout, true, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "执行onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG, "执行onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "执行onDestroy()");
    }

    private HomeBean homeBeanData = null;

    @Override
    public void onGetArticleListSuccess(HomeBean data) {

        if (data.getDatas() == null) {
            homeContainerAdapter.setEmptyView(R.layout.rl_empty_container_view);
        }


        LogUtil.d(TAG, "数据: ===>" + JsonFormatUtils.format(new Gson().toJson(data.getDatas())));
        LogUtil.e(TAG, "当前数据源: " + " page= " + currPage);


        if (currPage == PAGE_START) {
            //第一页数据
            homeContainerAdapter.setNewData(data.getDatas());
//            homeContainerAdapter.addData(data.getDatas());
            LogUtil.d(TAG, "下拉刷新：  " + "数量: " + homeContainerAdapter.getData().size());
        } else {
            //请求更多数据,直接添加list中
            homeContainerAdapter.addData(data.getDatas());
            homeContainerAdapter.loadMoreComplete();
            LogUtil.d(TAG, "上拉加载更多：  " + "数量: " + homeContainerAdapter.getData().size());
        }

//        if (data.isOver() || data.getDatas().size() == 0) {
        if (data.getDatas().size() == 0 && currPage != PAGE_START) {
            homeContainerAdapter.loadMoreEnd();
//            smartRefreshLayout.setEnableLoadMore(false);
            onShowToast("没有更多数据了!");
            //设置是否在全部加载结束之后Footer跟随内容
            smartRefreshLayout.setNoMoreData(true);
            smartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        } else {
            if (!homeContainerAdapter.isLoadMoreEnable()) {
                homeContainerAdapter.setEnableLoadMore(true);
            }
            smartRefreshLayout.setEnableLoadMore(true);
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onGetArticleListFail(String content) {
        onShowToast(content);
        homeContainerAdapter.loadMoreFail();
        //这两个方法是在加载失败时调用的
        smartRefreshLayout.finishRefresh(false);
        smartRefreshLayout.finishLoadMore(false);
        homeContainerAdapter.setEmptyView(R.layout.rl_empty_container_view);
    }

    @Override
    public void onShowToast(String content) {
        onShowTrueToast(content);
    }

    @Override
    public void onShowProgress() {

    }

    @Override
    public void onHideProgress() {

    }

    @Override
    public void onRequestError(String content) {
        //这两个方法是在加载成功,并且还有数据的情况下调用的
        smartRefreshLayout.finishRefresh(false);
        smartRefreshLayout.finishLoadMore(false);
        homeContainerAdapter.loadMoreFail();
        View errorView = LayoutInflater.from(mActivity).inflate(R.layout.rl_empty_container_view, null);
        homeContainerAdapter.setEmptyView(errorView);
        LogUtil.d(TAG, content);
    }

}
