package liang.com.baseproject.login.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.setting.activity.SettingPatternPswActivity;
import liang.com.baseproject.utils.GsonUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.SPUtils;
import liang.com.baseproject.utils.UserLoginUtils;
import liang.com.baseproject.widget.CustomGestureView;

public class GestureLoginActivity extends MVPBaseActivity implements CustomGestureView.GestureCallBack {

    private static final String TAG = GestureLoginActivity.class.getSimpleName();

    @BindView(R.id.cv_gesture_avatar)
    CircleImageView cvGestureAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.gesture_view)
    CustomGestureView gestureView;
    @BindView(R.id.tv_gesture_forget_pwd)
    TextView tvGestureForgetPwd;
    @BindView(R.id.tv_gesture_switch_login)
    TextView tvGestureSwitchLogin;
    @BindView(R.id.rl_bottom_container)
    RelativeLayout rlBottomContainer;
    @BindView(R.id.rl_gesture_login_container)
    RelativeLayout rlGestureLoginContainer;

    private int gestureFlg;

    int imageArray[] = {R.drawable.welcomimg1, R.drawable.welcomimg2, R.drawable.welcomimg3, R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6, R.drawable.welcomimg7, R.drawable.welcomimg8,
            R.drawable.welcomimg9, R.drawable.welcomimg10, R.drawable.welcomimg11, R.drawable.welcomimg12};

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
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gesture_login;
    }

    public static void actionStart(Context context, int gestureFlg) {
        Intent intent = new Intent(context, GestureLoginActivity.class);
        intent.putExtra("gestureFlg", gestureFlg);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getIntentData();
    }

    private void getIntentData() {
        gestureFlg = getIntent().getIntExtra("gestureFlg", -1);
    }

    private void initView() {
        gestureView.setGestureCallBack(this);
        gestureView.clearCacheLogin();

        Random random = new Random();
        int index = random.nextInt(11);
        Resources resources = getResources();
        Drawable currentDrawable = resources.getDrawable(imageArray[index]);
        rlGestureLoginContainer.setBackground(currentDrawable);

        if (UserLoginUtils.getInstance().isLogin()) {
            cvGestureAvatar.setBorderColor(getResources().getColor(R.color.yellow));
            tvUserName.setText(UserLoginUtils.getInstance().getLoginUserBean().getUsername());

            Glide.with(GestureLoginActivity.this).asBitmap().load(UserLoginUtils.getInstance().getLocalUserIcon()).into(cvGestureAvatar);
        } else {
            tvUserName.setText("Welcome");
            cvGestureAvatar.setBorderColor(getResources().getColor(R.color.colorBlue));

            Glide.with(GestureLoginActivity.this).asBitmap().load(R.drawable.icon_user_logo).into(cvGestureAvatar);
        }
    }

    /**
     * 手势CODE   GESTURE_FLG_CODE = {1,2,3};
     * 1:删除密码
     * 2:修改密码
     * 3:解锁成功
     */
    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<CustomGestureView.GestureBean> data, boolean success) {
        LogUtil.d(TAG, "解锁图案手势密码: " + GsonUtils.toJson(data));
        if (success) {
            LogUtil.d(TAG, "gestureFlg = " + gestureFlg);

            if (gestureFlg == Constant.GESTURE_FLG_CODE[0]) {
                //删除密码（1:删除密码）
                SPUtils.put(this, Constant.IS_GESTURE_FLAG, false);
                gestureView.clearCache();
                //清空手势密码成功
                onShowToast(getString(R.string.gesture_unlock_clear_success));
                finish();
            } else if (gestureFlg == Constant.GESTURE_FLG_CODE[1]) {
                //修改密码（2:修改密码）
                //验证手势密码成功,请重新设置
                onShowToast(getString(R.string.gesture_unlock_verify_success));
                //跳转设置手势密码界面
                SettingPatternPswActivity.actionStart(this);
                //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            } else if (gestureFlg == Constant.GESTURE_FLG_CODE[2]) {
                //3:解锁成功
                MainHomeActivity.actionStart(this);
                //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

        } else {

        }
    }
}
