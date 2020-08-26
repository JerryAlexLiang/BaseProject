package liang.com.baseproject.setting.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import com.liang.module_core_java.mvp.MVPBaseActivity;
import com.liang.module_core_java.mvp.MVPBasePresenter;
import com.liang.module_core_java.utils.GsonUtils;
import com.liang.module_core_java.utils.LogUtil;
import com.liang.module_core_java.utils.SPUtils;
import com.liang.module_core_java.widget.CustomGestureView;

import static liang.com.baseproject.Constant.Constant.IS_GESTURE_FLAG;

public class SettingPatternPswActivity extends MVPBaseActivity implements CustomGestureView.GestureCallBack {

    private static final String TAG = SettingPatternPswActivity.class.getSimpleName();
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
    @BindView(R.id.gesture_view)
    CustomGestureView gestureView;
    private int jumpFlg;
    private int flag;

    public static void actionStart(Context context, int jumpFlg, int flag) {
        Intent intent = new Intent(context, SettingPatternPswActivity.class);
        intent.putExtra("jumpFlg", jumpFlg);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, int requestCode) {
        Intent intent = new Intent(context, SettingPatternPswActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingPatternPswActivity.class);
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
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting_pattern_psw;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        jumpFlg = intent.getIntExtra("jumpFlg", 0);
        flag = intent.getIntExtra("flag", 0);
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(R.string.gesture_set);

        gestureView.setGestureCallBack(this);
        //不调用这个方法会造成第二次启动程序直接进入手势识别而不是手势设置
        gestureView.clearCache();
    }

    @OnClick(R.id.base_actionbar_left_icon)
    public void onViewClicked() {
//        boolean isFirstOpen = (boolean) SPUtils.get(this, IS_FIRST_FLAG, true);
//        if (isFirstOpen) {
//            MainHomeActivity.actionStart(this);
//        }
        finish();
    }

    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<CustomGestureView.GestureBean> data, boolean success) {
        if (stateFlag == CustomGestureView.STATE_LOGIN) {
            //设置手势图案成功，保存标志
            SPUtils.put(this, IS_GESTURE_FLAG, true);
            MainHomeActivity.actionStart(this);
            finish();
            LogUtil.d(TAG, "设置图案手势密码: " + GsonUtils.toJson(data));
        }
    }
}
