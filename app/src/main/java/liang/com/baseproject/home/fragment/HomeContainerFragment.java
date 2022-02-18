package liang.com.baseproject.home.fragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.google.gson.Gson;
import com.liang.model_middleware.impl.ServiceProvider;
import com.liang.module_core.mvp.MVPBaseFragment;
import com.liang.module_core.utils.GsonUtils;
import com.liang.module_core.utils.JsonFormatUtils;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.entity.HomeFunctionBean;
import liang.com.baseproject.home.adapter.HomeContainerAdapter;
import liang.com.baseproject.home.adapter.HomeFunctionContainerAdapter;
import liang.com.baseproject.home.entity.ArticleBean;
import liang.com.baseproject.home.entity.ArticleHomeBannerBean;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.presenter.HomeContainerPresenter;
import liang.com.baseproject.home.view.HomeContainerView;
import liang.com.baseproject.main.activity.AgentWebActivityX5;

/**
 * 创建日期：2019/3/7 on 13:23
 * 描述: 玩Android 开放API - 首页相关
 * 作者: liangyang
 */
public class HomeContainerFragment extends MVPBaseFragment<HomeContainerView, HomeContainerPresenter> implements HomeContainerView, OnItemClickListener {

    private static final String TAG = HomeContainerFragment.class.getSimpleName();
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private ConvenientBanner convenientBanner;

    Unbinder unbinder;

    private MainHomeActivity mActivity;

    //    private static final int PAGE_START = 460;
    private static final int PAGE_START = 0;
    private int currPage = PAGE_START;
    private HomeContainerAdapter homeContainerAdapter;
    private boolean setRefreshFooter;

    private List<ArticleHomeBannerBean> mBannerData;
    private HomeFunctionContainerAdapter functionContainerAdapter;
    private List<HomeFunctionBean> functionBeanList;
    private RecyclerView rvHomeFunctionContainer;

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

//        changeRefreshHeaderStyle(Constant.MaterialHeader);
//        changeGlobalRefreshHeaderStyle(Constant.REFRESH_HEADER_28402_TEMPLO);
//        changeRefreshHeaderStyle(Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);

        initArticleRvList();
        initHeader();
    }

    private void initHeader() {
        //添加头部
        initHeader1();
        initHeader2();
        //默认出现了头部就不会显示Empty，和尾部  配置以下方法也支持同时显示setHeaderAndEmpty  setHeaderFooterEmpty
        homeContainerAdapter.setHeaderWithEmptyEnable(true);
    }

    private void initHeader1() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_article_home_banner_header, null);
        convenientBanner = headerView.findViewById(R.id.convenient_banner);
        homeContainerAdapter.addHeaderView(headerView);
    }

    private void initHeader2() {
        View headerView2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_function_container, null);
        rvHomeFunctionContainer = headerView2.findViewById(R.id.rv_home_function_container);
        headerView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        homeContainerAdapter.addHeaderView(headerView2);
    }

    private void initBanner() {
        //加载FunctionContainer数据
        initHeader2Data();
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public BannerImageHolderView createHolder(View itemView) {
                        return new BannerImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.layout_iv_banner;
                    }
                }, mBannerData)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ui_shape_banner_indicator_pressed, R.drawable.ui_shape_banner_indicator_normal})
                .setOnItemClickListener(this);
    }

    private void initHeader2Data() {
        functionBeanList.clear();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rvHomeFunctionContainer.setLayoutManager(gridLayoutManager);
        functionContainerAdapter = new HomeFunctionContainerAdapter();
        rvHomeFunctionContainer.setAdapter(functionContainerAdapter);

        HomeFunctionBean bean1 = new HomeFunctionBean(0, R.drawable.gank_icon_submit, "天气");
        HomeFunctionBean bean2 = new HomeFunctionBean(1, R.drawable.ic_calendar_black_19dp, "日历");
        HomeFunctionBean bean3 = new HomeFunctionBean(2, R.drawable.im_hover_bzfamily, "EyePetizer");
        HomeFunctionBean bean4 = new HomeFunctionBean(3, R.drawable.icon_function_more, "更多");
        functionBeanList.add(bean1);
        functionBeanList.add(bean2);
        functionBeanList.add(bean3);
        functionBeanList.add(bean4);
        functionContainerAdapter.addData(functionBeanList);
    }

    @Override
    public void onItemClick(int position) {
        ArticleHomeBannerBean articleHomeBannerBean = mBannerData.get(position);
        if (articleHomeBannerBean != null) {
            String url = articleHomeBannerBean.getUrl();
            int id = articleHomeBannerBean.getId();
            String title = articleHomeBannerBean.getTitle();
            Integer localImages = articleHomeBannerBean.getLocalImages();

            if (localImages == null) {
                //AgentWebActivity.actionStart(getContext(), id, title, url);
                AgentWebActivityX5.actionStart(getContext(), id, title, url);
            } else {
                if (getContext() != null) {
                    ServiceProvider.getCard3DModuleService().startCard3DActivity(getContext());
                }
            }

        }
    }

    public class BannerImageHolderView extends Holder<ArticleHomeBannerBean> {

        private ImageView imageView;
        private TextView textView;

        public BannerImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.iv_banner_view);
            textView = itemView.findViewById(R.id.tv_banner_title);
        }

        @Override
        public void updateUI(ArticleHomeBannerBean data) {
            textView.setText(data.getTitle());

            if (data.getLocalImages() != null) {
                Glide.with(getContext())
                        .load(data.getLocalImages())
                        .into(imageView);
            } else {
                Glide.with(getContext())
                        .load(data.getImagePath())
                        .into(imageView);
            }
        }
    }

    private void initArticleRvList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvHome.setLayoutManager(linearLayoutManager);
        //初始化适配器
        homeContainerAdapter = new HomeContainerAdapter();
//        homeContainerAdapter.setEnableLoadMore(false);
        //开启动画
        homeContainerAdapter.setAnimationEnable(true);
//        homeContainerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeContainerAdapter.setAdapterAnimation(new SlideInLeftAnimation());
        rvHome.setAdapter(homeContainerAdapter);

//        mPresenter.getArticleList(PAGE_START);

        homeContainerAdapter.setOnItemClickListener((adapter, view, position) -> {
            ArticleBean item = homeContainerAdapter.getItem(position);
            if (item != null) {
                LogUtil.d(TAG, "点击了:  " + Objects.requireNonNull(item).getTitle());
//                    AgentWebActivity.actionStart(getContext(), item.getId(), item.getTitle(), item.getLink());
//                AgentWebActivity.actionStart(getContext(), item);
                AgentWebActivityX5.actionStart(getContext(), item);
            }
        });

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            currPage = PAGE_START;
            mPresenter.getArticleList(currPage);
            mPresenter.getArticleHomeBanner();
        });

        setRefreshFooter = isSetRefreshFooter();
        if (setRefreshFooter) {
            smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
                currPage++;
                mPresenter.getArticleList(currPage);
            });
        } else {
//            homeContainerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//                @Override
//                public void onLoadMoreRequested() {
//                    currPage++;
//                    mPresenter.getArticleList(currPage);
//                }
//            }, rvHome);
            homeContainerAdapter.getLoadMoreModule().setAutoLoadMore(true);
            //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
            homeContainerAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        }

        //自动刷新(替代第一次请求数据)
        smartRefreshLayout.autoRefresh();
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
        //开始自动翻页
        if (convenientBanner != null) {
            convenientBanner.startTurning();
        }
        //加载FunctionContainer数据
        if (functionBeanList != null) {
            initHeader2Data();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "执行onPause()");
        //停止自动翻页
        if (convenientBanner != null) {
            convenientBanner.stopTurning();
        }
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
//            homeContainerAdapter.loadMoreComplete();
            LogUtil.d(TAG, "上拉加载更多：  " + "数量: " + homeContainerAdapter.getData().size());
        }

//        if (data.isOver() || data.getDatas().size() == 0) {
        if (data.getDatas().size() == 0 && currPage != PAGE_START) {
//            homeContainerAdapter.loadMoreEnd();
            onShowToast("没有更多数据了!");
            //设置是否在全部加载结束之后Footer跟随内容
            smartRefreshLayout.setNoMoreData(true);
            smartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
        } else {
//            if (!homeContainerAdapter.isLoadMoreEnable()) {
//                homeContainerAdapter.setEnableLoadMore(true);
//            }
            smartRefreshLayout.setEnableLoadMore(true);
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onGetArticleListFail(String content) {
        onShowToast(content);
//        homeContainerAdapter.loadMoreFail();
        //这两个方法是在加载失败时调用的
        smartRefreshLayout.finishRefresh(false);
        smartRefreshLayout.finishLoadMore(false);
        homeContainerAdapter.setEmptyView(R.layout.rl_empty_container_view);
    }

    @Override
    public void getArticleHomeBannerSuccess(List<ArticleHomeBannerBean> data) {
        if (mBannerData == null) {
            mBannerData = new ArrayList<>();
        }
        if (functionBeanList == null) {
            functionBeanList = new ArrayList<>();
        }

        mBannerData.clear();

        ArticleHomeBannerBean bean = new ArticleHomeBannerBean();
        bean.setDesc("3D旋转汽车模型");
        bean.setTitle("3D旋转汽车模型");
        bean.setId(1);
        bean.setLocalImages(R.drawable.app_icon_card1);

        mBannerData.add(bean);

        mBannerData.addAll(data);

        LogUtil.d(TAG,"Banner Data: "+ GsonUtils.toJson(mBannerData));

        initBanner();
    }

    @Override
    public void getArticleHomeBannerFail(String content) {
        ToastUtil.onShowErrorToast(getContext(), "Banner数据获取失败：" + content);
        LogUtil.d(TAG, content);
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
//        homeContainerAdapter.loadMoreFail();
        View errorView = LayoutInflater.from(mActivity).inflate(R.layout.rl_empty_container_view, null);
        homeContainerAdapter.setEmptyView(errorView);
        LogUtil.d(TAG, content);
    }
}
