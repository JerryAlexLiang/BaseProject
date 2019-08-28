package liang.com.baseproject.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import liang.com.baseproject.R;
import liang.com.baseproject.setting.activity.AppSettingActivity;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.entity.Userbean;
import liang.com.baseproject.utils.ImageLoaderUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.utils.UserLoginUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends MVPBaseFragment {

    private static final String TAG = MineFragment.class.getSimpleName();
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.civ_user_icon)
    CircleImageView civUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.ll_user_id)
    LinearLayout llUserId;
    @BindView(R.id.tv_user_coin)
    TextView tvUserCoin;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.ll_user_coin)
    LinearLayout llUserCoin;
    @BindView(R.id.user_info_container)
    RelativeLayout userInfoContainer;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    Unbinder unbinder1;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    Unbinder unbinder2;
    private MainHomeActivity mActivity;
    private boolean login;

    public MineFragment() {
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
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    /**
     * 接收登录消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (isDetached()) {
            return;
        }
        boolean login = event.isLogin();
        Userbean loginUserBean = UserLoginUtils.getInstance().getLoginUserBean();
        LogUtil.e(TAG, "登录用户信息Event: " + new Gson().toJson(loginUserBean) + "  是否已登录: " + login);
        //更新用户UI
        initUserInfo();
        //请求获取积分接口
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置SmartRefreshLayout容器越界回弹
        initSmartRefreshLayoutPureScroll();
        //加载数据
        initUserInfo();
    }

    private void initUserInfo() {
        if (UserLoginUtils.getInstance().isLogin()) {
            //已登录，填充数据
            Userbean loginUserBean = UserLoginUtils.getInstance().getLoginUserBean();
            LogUtil.e(TAG, "登录用户信息: " + new Gson().toJson(loginUserBean));

            String localUserIcon = UserLoginUtils.getInstance().getLocalUserIcon();
            String localBg = UserLoginUtils.getInstance().getLocalBg();

            if (!TextUtils.isEmpty(localUserIcon) && !TextUtils.isEmpty(localBg)) {
                ImageLoaderUtils.loadImage(getActivity(), false, civUserIcon, localUserIcon, 0, 0, 0);
                ImageLoaderUtils.loadImage(getActivity(), false, ivBlur, localBg, 0, 0, 0);
            }

            tvUserName.setText(loginUserBean.getUsername());
            tvUserId.setText(loginUserBean.getId() + "");
            llUserId.setVisibility(View.VISIBLE);
            llUserCoin.setVisibility(View.VISIBLE);
        } else {
            //未登录，初始化视图
            civUserIcon.setImageResource(R.drawable.icon_user_logo);
            civUserIcon.setCircleBackgroundColorResource(R.color.translate);
            tvUserName.setText(getString(R.string.go_to_login));
            llUserId.setVisibility(View.GONE);
            llUserCoin.setVisibility(View.GONE);
        }
    }

    /**
     * 设置SmartRefreshLayout容器越界回弹
     */
    private void initSmartRefreshLayoutPureScroll() {
        smartRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                ivBlur.getLayoutParams().height = rlUserInfo.getMeasuredHeight() + offset;
                ivBlur.requestLayout();
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                ivBlur.getLayoutParams().height = rlUserInfo.getMeasuredHeight() - offset;
                ivBlur.requestLayout();
            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });

    }

    @OnClick({R.id.civ_user_icon, R.id.user_info_container, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_user_icon:
                if (UserLoginUtils.getInstance().isLogin()) {
                    ToastUtil.showShortToast("开发中...");
                } else {
                    if (Build.VERSION.SDK_INT > 20) {
                        Bundle options = ActivityOptions.makeSceneTransitionAnimation(mActivity, civUserIcon, "navUserIocn").toBundle();
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent, options);
                    } else {
                        LoginActivity.actionStart();
                    }
                }

                break;

            case R.id.user_info_container:
                boolean doIfNeedLogin = UserLoginUtils.getInstance().doIfNeedLogin();
                if (doIfNeedLogin) {
                    ToastUtil.showShortToast("开发中...");
                }
                break;

            case R.id.ll_setting:
                AppSettingActivity.actionStart(mActivity);
                break;
        }
    }

}
