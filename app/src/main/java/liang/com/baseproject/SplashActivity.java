package liang.com.baseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wangyuwei.particleview.ParticleView;

/**
 * 创建日期：2019/1/24 on 11:11
 * 描述: APP启动界面(栗子动画)
 * 作者: liangyang
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.pv_splash_logo)
    ParticleView pvSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        pvSplashLogo.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                MainActivity.actionStart(SplashActivity.this);
                //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
        //开启栗子动画
        pvSplashLogo.startAnim();
    }
}
