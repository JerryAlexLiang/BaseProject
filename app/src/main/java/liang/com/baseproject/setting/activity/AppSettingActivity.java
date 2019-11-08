package liang.com.baseproject.setting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.setting.presenter.AppSettingPresenter;
import liang.com.baseproject.setting.view.AppSettingView;
import liang.com.baseproject.utils.SettingUtils;
import liang.com.baseproject.utils.UserLoginUtils;
import liang.com.baseproject.widget.SearchEditText;

public class AppSettingActivity extends MVPBaseActivity<AppSettingView, AppSettingPresenter> implements AppSettingView {

    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_setting_security)
    LinearLayout llSettingSecurity;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.edit_search_view)
    SearchEditText editSearchView;
    @BindView(R.id.switch_button_night_mode)
    Switch switchButtonNightMode;
    private AlertView logoutAlertView;
    private boolean mDarkTheme;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AppSettingActivity.class);
        context.startActivity(intent);
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
        return false;
    }

    @Override
    protected AppSettingPresenter createPresenter() {
        return new AppSettingPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_app_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        //设置“退出登录按钮”显示与隐藏
        if (UserLoginUtils.getInstance().isLogin()) {
            //显示
            llLogout.setVisibility(View.VISIBLE);
        } else {
            llLogout.setVisibility(View.GONE);
        }

        //主题模式
        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
        //设置当前主题模式
        switchButtonNightMode.setChecked(mDarkTheme);
        initSwitchListener();

    }

    private void initSwitchListener() {
        switchButtonNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingUtils.getInstance().setDarkTheme(isChecked);
                MyApplication.setDarkModeStatus();
                buttonView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Recreate所有Activity
                        MyApplication.recreate();

                        //方式1: 直接退出当前Activity - 最佳实现方案
                        MainHomeActivity.actionStart(AppSettingActivity.this);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

//                        //方式2: 使用recreate()方法重启Activity(会有闪屏问题)
//                        recreate();

                        //方式3: 不掉用recreate()方法，而是自己重启当前activity并为activity设置启动和退出动画即可,但是仅仅这样的话主界面MainHomeActivity没有产生效果,
                        //需要发送一个广播通知MainHomeActivity->重启
                        //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
//                        AppSettingActivity.actionStart(AppSettingActivity.this);
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        finish();
                    }
                }, 300);
            }
        });
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(getResources().getString(R.string.setting));

        //初始化登出提示弹框
        initLogoutAlert();
    }

    private void initLogoutAlert() {
        logoutAlertView = new AlertView.Builder()
                .setContext(AppSettingActivity.this)
                .setTitle("提示")
                .setMessage(getString(R.string.ensure_go_to_logout))
                .setDestructive("取消", "确定")
                .setOthers(null)
                .setStyle(AlertView.Style.Alert)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                onShowToast("取消退出");
                                break;

                            case 1:
//                                ToastUtil.showShortToast("确定");
                                mPresenter.goToLogout();
                                break;
                        }
                    }
                })
                .build();
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.ll_logout, R.id.ll_setting_security})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.ll_logout:
                logoutAlertView.show();
                break;

            case R.id.ll_setting_security:
                AccountSecuritySetActivity.actionStart(this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            if (logoutAlertView.isShowing()) {
                logoutAlertView.dismiss();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 访问了logout 后，服务端会让客户端清除 Cookie（即cookie max-Age=0），
     * 如果客户端 Cookie 实现合理，可以实现自动清理，如果本地做了用户账号密码和保存，及时清理。
     */
    @Override
    public void logoutSuccess() {
        //本地清除账号密码数据
        UserLoginUtils.getInstance().logout();
        //订阅退出登录事件总线
        new LoginEvent(false).post();
        onShowToast("退出登录成功~");
        //退出当前界面
        finish();
    }

    @Override
    public void logoutFail(String content) {
        onShowToast(content);
    }

    @Override
    public void onShowToast(String content) {
        onShowTrueToast(content);
    }

    @Override
    public void onShowProgress() {
        showProgressDialog("Loading...", false);
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onRequestError() {

    }
}
