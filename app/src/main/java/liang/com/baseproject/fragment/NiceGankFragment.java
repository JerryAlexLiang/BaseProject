package liang.com.baseproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.liang.module_core.mvp.MVPBaseFragment;
import com.liang.module_core.utils.NetUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.View.NiceGankView;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.presenter.NiceGankPresenter;

/**
 * 创建日期：2019/2/25 on 19:22
 * 描述: 干货集中营API- 颜如玉View （另一MVP模式写法）  extends MVPBaseFragment<IGankFgView,GankFgPresenter> implements IGankFgView
 * 作者: liangyang
 */
public class NiceGankFragment extends MVPBaseFragment<NiceGankView, NiceGankPresenter> implements NiceGankView {

    @BindView(R.id.recycler_view_nice_gank)
    RecyclerView recyclerViewNiceGank;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private GridLayoutManager gridLayoutManager;

    private MainHomeActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainHomeActivity) context;
    }

    @Override
    protected NiceGankPresenter createPresenter() {
        return new NiceGankPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_nice_gank;
    }

    @Override
    protected void initView(View rootView) {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewNiceGank.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (NetUtil.isNetworkAvailable(getContext())) {
            setDataRefresh(true);
//            mPresenter.getNiceGankData();
            mPresenter.getNiceGankGirlData();
            mPresenter.scrollRecycleViewListener();
            mPresenter.swipeRefreshListener();
        }
    }

    @Override
    public void setToastShow(String str) {
        onShowErrorToast(str);
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
//        mPresenter.getNiceGankData();
        mPresenter.getNiceGankGirlData();
    }

    @Override
    public void setDataRefresh(Boolean isRefresh) {
        setRefresh(isRefresh);
    }

    @Override
    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerViewNiceGank;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
