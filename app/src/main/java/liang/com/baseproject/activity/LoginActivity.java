package liang.com.baseproject.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.fragment.LoginFragment;
import liang.com.baseproject.fragment.RegisterFragment;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.SPUtils;
import per.goweii.percentimageview.percentimageview.PercentImageView;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_THEME;

public class LoginActivity extends MVPBaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
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
    @BindView(R.id.ll_logo)
    LinearLayout llLogo;
    @BindView(R.id.percent_image_one)
    PercentImageView percentImageOne;
    @BindView(R.id.percent_image_two)
    PercentImageView percentImageTwo;
    @BindView(R.id.vp_register_login_container)
    ViewPager vpRegisterLoginContainer;
    @BindView(R.id.rl_vp_container)
    RelativeLayout rlVpContainer;
    @BindView(R.id.rl_login_container)
    RelativeLayout rlLoginContainer;

    private List<Fragment> fragmentList = new ArrayList<>();

    private boolean isRunning = false;
    private AnimatorSet mSet1;
    private AnimatorSet mSet2;

    public static void actionStart() {
        Intent intent = new Intent(MyApplication.getAppContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getAppContext().startActivity(intent);
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        //初始化视图
        initView();
        //初始化Fragment
        initVpFragment();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            baseActionbar.setElevation(0);
        }
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(getResources().getString(R.string.login));
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
    }

    private void initVpFragment() {
        //初始化数据源
        fragmentList.add(new LoginFragment());
        fragmentList.add(new RegisterFragment());

        //初始化适配器
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList, null);
        //设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        vpRegisterLoginContainer.setOffscreenPageLimit(3);
        //绑定适配器
        vpRegisterLoginContainer.setAdapter(fragmentViewPagerAdapter);

        //ViewPager滑动监听事件
        vpRegisterLoginContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        baseActionbarTitle.setText(getResources().getString(R.string.login));
                        break;

                    case 1:
                        baseActionbarTitle.setText(getResources().getString(R.string.register));
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onStart() {
        isRunning = true;
        mSet1 = startCircleAnim(percentImageOne);
        mSet2 = startCircleAnim(percentImageTwo);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionBarTheme(rlLoginContainer);
    }

    @Override
    protected void onStop() {
        isRunning = false;
        stopCircleAnim();
        super.onStop();
    }

    @OnClick(R.id.base_actionbar_left_icon)
    public void onViewClicked() {
        finish();
    }

    private AnimatorSet startCircleAnim(View target) {
        LogUtil.d(TAG, "startAnim");
        if (target == null) {
            return null;
        }
        float[] xy = calculateRandomXY();
        AnimatorSet set = createTranslationAnimator(target, xy[0], xy[1]);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isRunning) {
                    startCircleAnim(target);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        set.start();
        return set;
    }

    private void stopCircleAnim() {
        LogUtil.d(TAG, "stopAnim");
        if (mSet1 != null) {
            mSet1.cancel();
            mSet1 = null;
        }
        if (mSet2 != null) {
            mSet2.cancel();
            mSet2 = null;
        }
    }

    private final long mMaxMoveDuration = 20000L;
    private final int mMaxMoveDistanceX = 200;
    private final int mMaxMoveDistanceY = 20;

    private AnimatorSet createTranslationAnimator(View target, float toX, float toY) {
        float fromX = target.getTranslationX();
        float fromY = target.getTranslationY();
        long duration = calculateDuration(fromX, fromY, toX, toY);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(target, "translationX", fromX, toX);
        animatorX.setDuration(duration);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(target, "translationY", fromY, toY);
        animatorY.setDuration(duration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        return set;
    }

    private Random mRandom = new Random();

    private float[] calculateRandomXY() {
        float x = mRandom.nextInt(mMaxMoveDistanceX) - (mMaxMoveDistanceX * 0.5F);
        float y = mRandom.nextInt(mMaxMoveDistanceY) - (mMaxMoveDistanceY * 0.5F);
        return new float[]{x, y};
    }

    private long calculateDuration(float x1, float y1, float x2, float y2) {
        float distance = (float) Math.abs(Math.sqrt(Math.pow(Math.abs((x1 - x2)), 2) + Math.pow(Math.abs((y1 - y2)), 2)));
        float maxDistance = (float) Math.abs(Math.sqrt(Math.pow(mMaxMoveDistanceX, 2) + Math.pow(mMaxMoveDistanceY, 2)));
        long duration = (long) (mMaxMoveDuration * (distance / maxDistance));
        LogUtil.d(TAG, "calculateDuration  distance=  " + distance + ", duration=" + duration);
        return duration;
    }

    public void getActionBarTheme(RelativeLayout rlLoginContainer) {
        int actionBarColorInt = (int) SPUtils.get(LoginActivity.this, ACTIONBAR_COLOR_THEME, 0);
        LogUtil.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case 0:
                if (rlLoginContainer != null) {
                    rlLoginContainer.setBackgroundColor(Color.RED);
                }
                break;

            case 1:
                if (rlLoginContainer != null) {
                    rlLoginContainer.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                }
                break;

            case 2:
                if (rlLoginContainer != null) {
                    rlLoginContainer.setBackgroundColor(Color.BLACK);
                }
                break;

            case 3:
                if (rlLoginContainer != null) {
                    rlLoginContainer.setBackgroundColor(Color.WHITE);
                }
                break;

            case 4:
                if (rlLoginContainer != null) {
                    rlLoginContainer.setBackgroundColor(Color.TRANSPARENT);
                }
                break;

            case 5:
                if (rlLoginContainer != null) {
                    rlLoginContainer.setBackgroundColor(getResources().getColor(R.color.palegreen));
                }
                break;
        }
    }

    /**
     * 切换注册Fragment,Activity与子Fragment通信
     */
    public void changeToRegisterFragment() {
        if (vpRegisterLoginContainer != null) {
            vpRegisterLoginContainer.setCurrentItem(1);
            baseActionbarTitle.setText(getResources().getString(R.string.register));
        }
    }

    /**
     * 切换登录Fragment,Activity与子Fragment通信
     */
    public void changeToLoginFragment() {
        if (vpRegisterLoginContainer != null) {
            vpRegisterLoginContainer.setCurrentItem(0);
            baseActionbarTitle.setText(getResources().getString(R.string.login));
        }
    }
}
