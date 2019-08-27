package liang.com.baseproject.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.utils.SPUtils;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLACK;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLUE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_GREEN;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_RED;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_THEME;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_TRANSLATE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_WHITE;

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

    /**
     * 是否注册事件分发，默认不绑定
     */
//    protected boolean isRegisterEventBus() {
//        return false;
//    }
    protected abstract boolean isRegisterEventBus();

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
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
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

        CollapsingToolbarLayout baseToolBar = findViewById(R.id.toolbar_layout);
        if (baseToolBar != null) {
            getActionBarTheme(null, baseToolBar);
        }

        RelativeLayout splashRelativeLayout = findViewById(R.id.splash_container_layout);
        if (splashRelativeLayout != null) {
            getSplashTheme(splashRelativeLayout);
        }
    }

    /**
     * 本地化存储操作(存入) - 设置状态栏颜色
     *
     * @param ACTIONBAR_COLOR
     */
    public void setActionBarTheme(int ACTIONBAR_COLOR) {
        SPUtils.put(BaseActivity.this, ACTIONBAR_COLOR_THEME, ACTIONBAR_COLOR);
    }

    /**
     * 本地化存储操作(取出) - 设置状态栏颜色
     */
    public void getActionBarTheme(FrameLayout baseActionBar, CollapsingToolbarLayout baseToolBar) {
        int actionBarColorInt = (int) SPUtils.get(BaseActivity.this, ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case ACTIONBAR_COLOR_BLUE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(getResources().getColor(R.color.colorBlue));
                    baseToolBar.setStatusBarScrimColor(getResources().getColor(R.color.colorBlue));
                }
                break;

            case ACTIONBAR_COLOR_RED:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.RED);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.RED);
                    baseToolBar.setStatusBarScrimColor(Color.RED);
                }
                break;

            case ACTIONBAR_COLOR_BLACK:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.BLACK);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.BLACK);
                    baseToolBar.setStatusBarScrimColor(Color.BLACK);
                }
                break;

            case ACTIONBAR_COLOR_WHITE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.WHITE);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.WHITE);
                    baseToolBar.setStatusBarScrimColor(Color.WHITE);
                }
                break;

            case ACTIONBAR_COLOR_TRANSLATE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.TRANSPARENT);
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(Color.TRANSPARENT);
                    baseToolBar.setStatusBarScrimColor(Color.TRANSPARENT);
                }
                break;

            case ACTIONBAR_COLOR_GREEN:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.palegreen));
                }
                if (baseToolBar != null) {
                    baseToolBar.setContentScrimColor(getResources().getColor(R.color.palegreen));
                    baseToolBar.setStatusBarScrimColor(getResources().getColor(R.color.palegreen));
                }
                break;
        }
    }

    public void getSplashTheme(RelativeLayout relativeLayout) {
        int actionBarColorInt = (int) SPUtils.get(BaseActivity.this, ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case ACTIONBAR_COLOR_BLUE:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                }
                break;

            case ACTIONBAR_COLOR_RED:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.RED);
                }
                break;

            case ACTIONBAR_COLOR_BLACK:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.BLACK);
                }
                break;

            case ACTIONBAR_COLOR_WHITE:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.WHITE);
                }
                break;

            case ACTIONBAR_COLOR_TRANSLATE:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(Color.TRANSPARENT);
                }
                break;

            case ACTIONBAR_COLOR_GREEN:
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.palegreen));
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
