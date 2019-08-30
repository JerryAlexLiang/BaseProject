package liang.com.baseproject.home.fragment;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.presenter.HomeContainerPresenter;
import liang.com.baseproject.home.view.HomeContainerView;
import liang.com.baseproject.utils.JsonFormatUtils;
import liang.com.baseproject.utils.LogUtil;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvHome.setLayoutManager(linearLayoutManager);
        //初始化适配器

    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "执行onResume()");
        mPresenter.getArticleList(0);
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

    @Override
    public void onGetArticleListSuccess(HomeBean data) {
        LogUtil.e(TAG, "数据: ===>" + JsonFormatUtils.format(new Gson().toJson(data.getDatas())));
    }

    @Override
    public void onGetArticleListFail(String content) {

    }

    @Override
    public void onShowToast(String content) {

    }

    @Override
    public void onShowProgress() {

    }

    @Override
    public void onHideProgress() {

    }
}
