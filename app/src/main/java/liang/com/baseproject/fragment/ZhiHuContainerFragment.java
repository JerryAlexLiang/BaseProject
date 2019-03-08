package liang.com.baseproject.fragment;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import liang.com.baseproject.View.ZhihuView;
import liang.com.baseproject.base.BasePresenter;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.presenter.ZhihuPresenter;
import liang.com.baseproject.utils.NetUtil;
import liang.com.baseproject.utils.ToastUtil;

/**
 * 创建日期：2019/3/7 on 13:23
 * 描述: 知乎日报  MVPBaseFragment<NiceGankView, NiceGankPresenter> implements NiceGankView
 * MVPBaseFragment<IZhihuFgView, ZhihuFgPresenter> implements IZhihuFgView
 * 作者: liangyang
 */
public class ZhiHuContainerFragment extends MVPBaseFragment<ZhihuView,ZhihuPresenter> implements ZhihuView{

    @BindView(R.id.recycler_view_zhihu)
    RecyclerView recyclerViewZhihu;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;

    public ZhiHuContainerFragment() {
        // Required empty public constructor
    }


    @Override
    protected ZhihuPresenter createPresenter() {
        return new ZhihuPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(View rootView) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewZhihu.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (NetUtil.isNetworkAvailable(getContext())){
            //设置进入时自动刷新
            setDataRefresh(true);
            //请求知乎最新消息
            mPresenter.getZhihuLatestData();
            //RecyclerView滑动监听事件(结合底部布局刷新视图)
            mPresenter.scrollRecycleViewListener();
        }
    }

    @Override
    public void setToastShow(String content) {
        ToastUtil.setCustomToast(getContext(), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_wrong),
                true, content, Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getZhihuLatestData();
    }

    @Override
    public void setDataRefresh(Boolean isRefresh) {
        setRefresh(isRefresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerViewZhihu;
    }

    @Override
    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }
}
