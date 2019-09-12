package liang.com.baseproject.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.login.activity.GestureLoginActivity;
import liang.com.baseproject.login.activity.WelcomeGuideActivity;
import liang.com.baseproject.utils.APKVersionCodeUtils;
import liang.com.baseproject.utils.SPUtils;
import me.wangyuwei.particleview.ParticleView;

import static liang.com.baseproject.Constant.Constant.IS_FIRST_FLAG;
import static liang.com.baseproject.Constant.Constant.IS_GESTURE_FLAG;

/**
 * 创建日期：2019/1/24 on 11:11
 * 描述: APP启动界面(栗子动画)
 * 作者: liangyang
 */
public class SplashOneActivity extends MVPBaseActivity {

    @BindView(R.id.pv_splash_logo)
    ParticleView pvSplashLogo;
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;

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
        return R.layout.activity_splash_one;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_splash_one);
        ButterKnife.bind(this);
        pvSplashLogo.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
//                MainHomeActivity.actionStart(SplashOneActivity.this);
//                //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                finish();

                boolean isFirstOpen = (boolean) SPUtils.get(SplashOneActivity.this, IS_FIRST_FLAG, true);
                if (isFirstOpen) {
                    //是第一次打开APP，则进入欢迎页面
                    WelcomeGuideActivity.actionStart(SplashOneActivity.this);
                    //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    return;
                } else {
                    //非第一次进入，则跳转到登录界面
                    initJump();
                }
            }
        });
        //开启栗子动画
        pvSplashLogo.startAnim();
        //获取当前版本号
        tvVersionCode.setText(String.format("Version:%s", APKVersionCodeUtils.getVerName(this)));
    }

    private void initJump() {
        //手势识别登录界面
        boolean isGesture = (boolean) SPUtils.get(this, IS_GESTURE_FLAG, false);
        if (isGesture) {
            //已开启手势登录功能，则进入手势登录界面
            /**
             * 手势CODE   GESTURE_FLG_CODE = {1,2,3};
             * 1:删除密码
             * 2:修改密码
             * 3:解锁成功
             */
//            Intent intent = new Intent(this, GestureLoginActivity.class);
//            //等于3为认证成功
//            intent.putExtra("gestureFlg", Constant.GESTURE_FLG_CODE[2]);
//            startActivity(intent);
            //等于3为认证成功
            GestureLoginActivity.actionStart(this,Constant.GESTURE_FLG_CODE[2]);
            //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            return;
        }

        //没有开启手势登录功能，则进入主界面
        MainHomeActivity.actionStart(this);
        //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

//    private void fullScreen(boolean enable) {
//        if (enable) {
//            WindowManager.LayoutParams lp = getWindow().getAttributes();
//            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            getWindow().setAttributes(lp);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        } else {
//            WindowManager.LayoutParams attr = getWindow().getAttributes();
//            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setAttributes(attr);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//    }
}
