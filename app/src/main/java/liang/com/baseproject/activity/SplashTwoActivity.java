package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.base.BaseActivity;

public class SplashTwoActivity extends BaseActivity {

    @BindView(R.id.iv_start)
    ImageView ivStart;
    @BindView(R.id.tv_imgAuthor)
    TextView tvImgAuthor;
    private ScaleAnimation mScaleAnim;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SplashTwoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_two);
        ButterKnife.bind(this);
        mScaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mScaleAnim.setFillAfter(true);
        mScaleAnim.setDuration(5000);

        addActivity(this,SplashTwoActivity.class);

        mScaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    MainHomeActivity.actionStart(SplashTwoActivity.this);
                }
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finishAll();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivStart.startAnimation(mScaleAnim);
    }
}
