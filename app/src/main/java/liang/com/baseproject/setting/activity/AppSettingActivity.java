package liang.com.baseproject.setting.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.setting.presenter.AppSettingPresenter;
import liang.com.baseproject.setting.view.AppSettingView;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.utils.UserLoginUtils;

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
    private AlertView logoutAlertView;

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
        return false;
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
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(getResources().getString(R.string.setting));

        //设置“退出登录按钮”显示与隐藏
        if (UserLoginUtils.getInstance().isLogin()){
            //显示
            llLogout.setVisibility(View.VISIBLE);
        }else {
            llLogout.setVisibility(View.GONE);
        }

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
                                ToastUtil.showShortToast("取消退出");
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

    @OnClick({R.id.base_actionbar_left_icon, R.id.ll_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.ll_logout:
                logoutAlertView.show();
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
        ToastUtil.setCustomToast(this, BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                false, content, getResources().getColor(R.color.toast_bg), Color.WHITE, Gravity.BOTTOM, Toast.LENGTH_SHORT);
    }

    @Override
    public void onShowProgress() {
        showProgressDialog("Loading...", false);
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }
}
