package liang.com.baseproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.login.activity.LoginActivity;

/**
 * 创建日期：2019/3/7 on 13:23
 * 描述: 玩Android 开放API - 首页相关
 * 作者: liangyang
 */
public class HomeContainerFragment extends MVPBaseFragment {

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
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
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

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_three,null);
//        return view;
//    }
}
