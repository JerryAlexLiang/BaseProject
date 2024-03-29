package com.liang.module_core.mvp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.liang.module_core.R;
import com.liang.module_core.app.BaseApplication;
import com.liang.module_core.constant.Constant;
import com.liang.module_core.utils.SPUtils;
import com.liang.module_core.utils.ToastUtil;
import com.liang.module_core.widget.refreshWidget.MyRefreshLottieHeader;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 创建日期：2019/1/24 on 11:04
 * 描述: BaseActivity
 * 作者: liangyang
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    /**
     * 管理所有的activity资源对象
     */
    private static List<BaseActivity> activities;
    public Activity mActivity;

    private String lottieFileName = "";
    private MyRefreshLottieHeader myRefreshLottieHeader;

    /**
     * 是否注册事件分发，默认不绑定
     */
    protected abstract boolean isRegisterEventBus();

    protected abstract boolean isSetRefreshHeader();

    protected abstract boolean isSetRefreshFooter();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //全透明状态栏(不带阴影)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //半透明状态栏(带阴影)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }

        //本地化存储操作(取出) - 设置状态栏颜色
        FrameLayout baseActionBar = findViewById(R.id.base_actionbar);
        if (baseActionBar != null) {
            getActionBarTheme(baseActionBar, null);
        }

        CollapsingToolbarLayout baseToolBar = findViewById(R.id.collapsing_layout);
        if (baseToolBar != null) {
            getActionBarTheme(null, baseToolBar);
        }

        RelativeLayout splashRelativeLayout = findViewById(R.id.splash_container_layout);
        if (splashRelativeLayout != null) {
            getSplashTheme(splashRelativeLayout);
        }

        //初始化Header
        myRefreshLottieHeader = new MyRefreshLottieHeader(BaseApplication.getAppContext());

        SmartRefreshLayout smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        if (smartRefreshLayout != null) {
            getSmartRefreshPrimaryColorsTheme(smartRefreshLayout, isSetRefreshHeader(), isSetRefreshFooter());
        }
    }

    /**
     * 本地化存储操作(存入) - 设置状态栏颜色
     *
     * @param ACTIONBAR_COLOR
     */
    public void setActionBarTheme(int ACTIONBAR_COLOR) {
        SPUtils.put(BaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, ACTIONBAR_COLOR);
    }

    /**
     * 本地化存储操作(取出) - 设置状态栏颜色
     */
    public void getActionBarTheme(FrameLayout baseActionBar, CollapsingToolbarLayout baseToolBar) {
        int actionBarColorInt = (int) SPUtils.get(BaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case Constant.ACTIONBAR_COLOR_BLUE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.title_bar_blue));
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(getResources().getColor(R.color.title_bar_blue));
                    baseToolBar.setStatusBarScrimColor(getResources().getColor(R.color.title_bar_blue));
                }
                break;

            case Constant.ACTIONBAR_COLOR_RED:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.title_bar_red));
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(getResources().getColor(R.color.title_bar_red));
                    baseToolBar.setStatusBarScrimColor(getResources().getColor(R.color.title_bar_red));
                }
                break;

            case Constant.ACTIONBAR_COLOR_BLACK:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.BLACK);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.BLACK);
                    baseToolBar.setStatusBarScrimColor(Color.BLACK);
                }
                break;

            case Constant.ACTIONBAR_COLOR_WHITE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.WHITE);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.WHITE);
                    baseToolBar.setStatusBarScrimColor(Color.WHITE);
                }
                break;

            case Constant.ACTIONBAR_COLOR_TRANSLATE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.TRANSPARENT);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.TRANSPARENT);
                    baseToolBar.setStatusBarScrimColor(Color.TRANSPARENT);
                }
                break;

            case Constant.ACTIONBAR_COLOR_GREEN:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.title_bar_green));
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(getResources().getColor(R.color.title_bar_green));
                    baseToolBar.setStatusBarScrimColor(getResources().getColor(R.color.title_bar_green));
                }
                break;
        }
    }

    public void getSplashTheme(RelativeLayout relativeLayout) {
        int actionBarColorInt = (int) SPUtils.get(BaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case Constant.ACTIONBAR_COLOR_BLUE:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.title_bar_blue));
                }
                break;

            case Constant.ACTIONBAR_COLOR_RED:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.title_bar_red));
                }
                break;

            case Constant.ACTIONBAR_COLOR_BLACK:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.BLACK);
                }
                break;

            case Constant.ACTIONBAR_COLOR_WHITE:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.WHITE);
                }
                break;

            case Constant.ACTIONBAR_COLOR_TRANSLATE:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.TRANSPARENT);
                }
                break;

            case Constant.ACTIONBAR_COLOR_GREEN:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.title_bar_green));
                }
                break;
        }
    }

    public void getSmartRefreshPrimaryColorsTheme(SmartRefreshLayout smartRefreshLayout, boolean isSetRefreshHeader, boolean isSetRefreshFooter) {
        int actionBarColorInt = (int) SPUtils.get(BaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, 0);
        String refreshHeaderStyle = (String) SPUtils.get(BaseApplication.getAppContext(), Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);

        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                setRefreshHeader(smartRefreshLayout, refreshHeaderStyle);
            }

            if (isSetRefreshFooter) {
                //上滑加载更多三点渐变动画底部View
                smartRefreshLayout.setRefreshFooter(new BallPulseFooter(BaseApplication.getAppContext()).setSpinnerStyle(SpinnerStyle.Scale));
            }
        }

        switch (actionBarColorInt) {
            case Constant.ACTIONBAR_COLOR_BLUE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_RED:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.accent, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_BLACK:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_WHITE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_TRANSLATE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.transparent, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_GREEN:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.assist, R.color.white);
                }
                break;
        }
    }

    /**
     * 替换RefreshHeader
     */
    public void setRefreshHeader(SmartRefreshLayout smartRefreshLayout, String refreshHeaderStyle) {
        switch (refreshHeaderStyle) {
            case Constant.REFRESH_HEADER_34115_ROCKET_LUNCH:
                lottieFileName = "lottie/34115-rocket-lunch.json";
                myRefreshLottieHeader.setAnimationViewJson(lottieFileName);
                setLottieRefreshHeader(smartRefreshLayout);
                break;

            case Constant.REFRESH_HEADER_28402_TEMPLO:
                lottieFileName = "lottie/28402-templo.json";
                myRefreshLottieHeader.setAnimationViewJson(lottieFileName);
                setLottieRefreshHeader(smartRefreshLayout);
                break;

            case Constant.MaterialHeader:
                smartRefreshLayout.setRefreshHeader(new MaterialHeader(BaseApplication.getAppContext()));
                break;

            case Constant.WaterDropHeader:
                smartRefreshLayout.setRefreshHeader(new WaterDropHeader(BaseApplication.getAppContext()));
                break;

            case Constant.WaveSwipeHeader:
                smartRefreshLayout.setRefreshHeader(new WaveSwipeHeader(BaseApplication.getAppContext()));
                break;

            case Constant.BezierCircleHeader:
                smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(BaseApplication.getAppContext()));
                break;

            case Constant.PhoenixHeader:
                smartRefreshLayout.setRefreshHeader(new PhoenixHeader(BaseApplication.getAppContext()));
                break;

            case Constant.TaurusHeader:
                smartRefreshLayout.setRefreshHeader(new TaurusHeader(BaseApplication.getAppContext()));
                break;

            case Constant.FlyRefreshHeader:
                smartRefreshLayout.setRefreshHeader(new FlyRefreshHeader(BaseApplication.getAppContext()));
                break;

            case Constant.ClassicsHeader:
                smartRefreshLayout.setRefreshHeader(new ClassicsHeader(BaseApplication.getAppContext()));
                break;

            case Constant.BezierRadarHeader:
                smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(BaseApplication.getAppContext()));
                break;

            case Constant.DeliveryHeader:
                smartRefreshLayout.setRefreshHeader(new DeliveryHeader(BaseApplication.getAppContext()));
                break;

            case Constant.DropBoxHeader:
                smartRefreshLayout.setRefreshHeader(new DropBoxHeader(BaseApplication.getAppContext()));
                break;

            case Constant.FunGameBattleCityHeader:
                smartRefreshLayout.setRefreshHeader(new FunGameBattleCityHeader(BaseApplication.getAppContext()));
                break;

            case Constant.FunGameHitBlockHeader:
                smartRefreshLayout.setRefreshHeader(new FunGameHitBlockHeader(BaseApplication.getAppContext()));
                break;

            case Constant.StoreHouseHeader:
                smartRefreshLayout.setRefreshHeader(new StoreHouseHeader(BaseApplication.getAppContext()));
                break;

            case Constant.TwoLevelHeader:
                smartRefreshLayout.setRefreshHeader(new TwoLevelHeader(BaseApplication.getAppContext()));
                break;
        }
    }

    /**
     * 设置自定义RefreshHeader
     */
    private void setLottieRefreshHeader(SmartRefreshLayout smartRefreshLayout) {
        smartRefreshLayout.setHeaderMaxDragRate(2);
        smartRefreshLayout.setRefreshHeader(myRefreshLottieHeader);
    }

    /**
     * 全局替换RefreshHeader
     */
    public void changeRefreshHeaderStyle(String refreshHeaderStyle) {
        switch (refreshHeaderStyle) {
            case Constant.REFRESH_HEADER_34115_ROCKET_LUNCH:
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);
                break;

            case Constant.REFRESH_HEADER_28402_TEMPLO:
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_28402_TEMPLO);
                break;

            case Constant.MaterialHeader:
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.MaterialHeader);
                break;

            case Constant.WaterDropHeader:
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.WaterDropHeader);
                break;

            case Constant.WaveSwipeHeader:
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.WaveSwipeHeader);
                break;

            case Constant.BezierCircleHeader:
                //水滴下拉
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.BezierCircleHeader);
                break;

            case Constant.PhoenixHeader:
                //大楼动画
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.PhoenixHeader);
                break;

            case Constant.TaurusHeader:
                //飞机滑翔动画效果
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.TaurusHeader);
                break;

            case Constant.FlyRefreshHeader:
                //飞机效果2
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FlyRefreshHeader);
                break;

            case Constant.ClassicsHeader:
                //经典带时间的刷新
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.ClassicsHeader);
                break;

            case Constant.BezierRadarHeader:
                //雷达动画
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.BezierRadarHeader);
                break;

            case Constant.DeliveryHeader:
                //快递交付动画
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.DeliveryHeader);
                break;

            case Constant.DropBoxHeader:
                //礼物盒子动画效果
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.DropBoxHeader);
                break;

            case Constant.FalsifyHeader:
                //无动画效果
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FalsifyHeader);
                break;

            case Constant.FunGameBattleCityHeader:
                //子弹游戏效果
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FunGameBattleCityHeader);
                break;

            case Constant.FunGameHitBlockHeader:
                //碰球游戏效果
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FunGameHitBlockHeader);
                break;

            case Constant.StoreHouseHeader:
                //StoreHouse文字渐变效果
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.StoreHouseHeader);
                break;

            case Constant.TwoLevelHeader:
                //二楼
                SPUtils.put(BaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.TwoLevelHeader);
                break;

        }
    }

    /**
     * google官方在安卓6.0以上版本才推出的深色状态栏字体api
     * <item name="android:windowLightStatusBar">true</item>
     */
    public void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //恢复状态栏白色字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 管理所有的activity资源对象  添加Activity
     */
    public void addActivity(BaseActivity baseActivity, Class<? extends Activity> target) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        activities.add(baseActivity);
        Log.d(TAG, "addActivity:   " + target);
    }

    public void finishAll() {
        for (BaseActivity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void finishPreAll() {
        for (int i = 0; i < activities.size() - 2; i++) {
            activities.get(i).finish();
        }
    }

    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null) {
            intent.putExtra(getPackageName(), bundle);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public void onShowToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.core_icon_true),
                false, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void onShowTrueToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.core_icon_true),
                true, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void onShowErrorToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.core_icon_wrong),
                true, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void fullScreen(boolean enable) {
        if (enable) {
            if (Build.VERSION.SDK_INT >= 19) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    /**
     * 禁止跟随系统字体大小调节
     */
    @Override
    public Resources getResources() {
        //还原字体大小
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            //非默认值
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }
}
