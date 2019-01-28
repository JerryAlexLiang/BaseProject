package liang.com.baseproject.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.HomeActivity;
import liang.com.baseproject.R;
import liang.com.baseproject.base.BaseActivity;
import me.wangyuwei.particleview.ParticleView;

/**
 * 创建日期：2019/1/24 on 11:11
 * 描述: APP启动界面(栗子动画)
 * 作者: liangyang
 */
public class SplashOneActivity extends BaseActivity {

    @BindView(R.id.pv_splash_logo)
    ParticleView pvSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_one);
        ButterKnife.bind(this);
        pvSplashLogo.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                HomeActivity.actionStart(SplashOneActivity.this);
                //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
        //开启栗子动画
        pvSplashLogo.startAnim();
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
