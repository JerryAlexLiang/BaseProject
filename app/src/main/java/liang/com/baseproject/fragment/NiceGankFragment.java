package liang.com.baseproject.fragment;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.View.NiceGankView;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.presenter.NiceGankPresenter;
import liang.com.baseproject.utils.NetUtil;
import liang.com.baseproject.utils.ToastUtil;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (NetUtil.isNetworkAvailable(getContext())) {
            setDataRefresh(true);
            mPresenter.getNiceGankData();
            mPresenter.scrollRecycleViewListener();
            mPresenter.swipeRefreshListener();
        }
    }

    @Override
    public void setToastShow(String str) {
        ToastUtil.setCustomToast(getContext(), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_wrong),
                true, str, Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getNiceGankData();
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
        // TODO: inflate a fragment view
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
