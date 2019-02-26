package liang.com.baseproject.fragment;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import liang.com.baseproject.R;
import liang.com.baseproject.View.NiceGankView;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.presenter.NiceGankPresenter;
import liang.com.baseproject.utils.NetUtil;
import liang.com.baseproject.utils.ToastUtil;

/**
 * 创建日期：2019/2/25 on 19:22
 * 描述: 干货集中营API- 颜如玉View （另一MVP模式写法）  extends MVPBaseFragment<IGankFgView,GankFgPresenter> implements IGankFgView
 * 作者: liangyang
 */
public class NiceGankFragment extends MVPBaseFragment<NiceGankView, NiceGankPresenter> implements NiceGankView{

    @BindView(R.id.recycler_view_nice_gank)
    RecyclerView recyclerViewNiceGank;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (NetUtil.isNetworkAvailable(getContext())){
            setDataRefresh(true);
            mPresenter.getNiceGankData();
            mPresenter.scrollRecycleViewListener();
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
}
