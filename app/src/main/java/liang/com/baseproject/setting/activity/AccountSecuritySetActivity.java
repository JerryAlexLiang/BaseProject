package liang.com.baseproject.setting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.mvp.MVPBasePresenter;
import liang.com.baseproject.login.activity.GestureLoginActivity;
import com.liang.module_core.utils.SPUtils;

/**
 * 创建日期：2019/9/11 on 9:30
 * 描述: 账号与安全设置页面
 * 作者: liangyang
 */
public class AccountSecuritySetActivity extends MVPBaseActivity {

    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    @BindView(R.id.ll_modify_password)
    LinearLayout llModifyPassword;
    @BindView(R.id.switch_button_gesture)
    Switch switchButtonGesture;
    @BindView(R.id.ll_setting_security)
    LinearLayout llSettingSecurity;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_gesture_pwd_modify)
    LinearLayout llGesturePwdModify;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AccountSecuritySetActivity.class);
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
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_account_security_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        boolean isGesture = (boolean) SPUtils.get(this, Constant.IS_GESTURE_FLAG, false);
        if (isGesture) {
            switchButtonGesture.setChecked(true);
            llGesturePwdModify.setVisibility(View.VISIBLE);
        } else {
            switchButtonGesture.setChecked(false);
            llGesturePwdModify.setVisibility(View.GONE);
        }

        initSwitchListener();
    }

    private void initSwitchListener() {
        switchButtonGesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isGesture = (boolean) SPUtils.get(AccountSecuritySetActivity.this, Constant.IS_GESTURE_FLAG, false);
                if (!isGesture) {
                    //之前没有开启手势登录，开启手势密码->跳转设置手势密码界面
                    SettingPatternPswActivity.actionStart(AccountSecuritySetActivity.this, 1);
                } else {
                    //已开启->关闭手势密码->跳转手势密码解锁界面
                    //等于1为删除密码
                    Intent intent = new Intent(AccountSecuritySetActivity.this, GestureLoginActivity.class);
                    intent.putExtra("gestureFlg", Constant.GESTURE_FLG_CODE[0]);
                    startActivityForResult(intent, 1);
                }
            }
        });

//        switchButtonGesture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isGesture = (boolean) SPUtils.get(AccountSecuritySetActivity.this, Constant.IS_GESTURE_FLAG, false);
//                if (!isGesture) {
//                    //之前没有开启手势登录，开启手势密码->跳转设置手势密码界面
//                    SettingPatternPswActivity.actionStart(AccountSecuritySetActivity.this, 1);
//                } else {
//                    //已开启->关闭手势密码->跳转手势密码解锁界面
//                    //等于1为删除密码
//                    Intent intent = new Intent(AccountSecuritySetActivity.this, GestureLoginActivity.class);
//                    intent.putExtra("gestureFlg", Constant.GESTURE_FLG_CODE[0]);
////                    jumpToActivity(intent, 1);
//                    startActivityForResult(intent, 1);
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //反注册，调用setChecked之前unregister listener,解决switchButton当代码setCheck时与onCheckedChanged冲突
            //场景，在某一activity中有一个switchButton，进入该页面时通过保存的boolean值通过setCheck(boolean)控制switchButton开启还是关闭，
            // 而该switchButton也注册了OnCheckedChangeListener事件，并在里面做相应的操作
            switchButtonGesture.setOnCheckedChangeListener(null);
            initData();
        }
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(R.string.set_securty);
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.ll_modify_password, R.id.ll_gesture_pwd_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.ll_modify_password:
                onShowToast("待开发...");
                break;

            case R.id.ll_gesture_pwd_modify:

                break;
        }
    }
}
