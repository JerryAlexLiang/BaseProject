package liang.com.baseproject.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.utils.SPUtils;
import liang.com.baseproject.widget.CustomProgressDialog;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLACK;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLUE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_GREEN;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_RED;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_THEME;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_TRANSLATE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_WHITE;

/**
 * 创建日期：2019/1/24 on 11:04
 * 描述: MVPBaseActivity - 通用标题栏模式
 * 作者: liangyang
 */
public abstract class MVPBaseActivity<V, T extends MVPBasePresenter<V>> extends AppCompatActivity {

    private static final String TAG = MVPBaseActivity.class.getSimpleName();

    protected T mPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsRequestDataRefresh = false;

    /**
     * 管理所有的activity资源对象
     */
    private static List<MVPBaseActivity> activities;
    public Activity mActivity;
    private CustomProgressDialog customProgressDialog;

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

    /**
     * 判断子Activity是否需要刷新功能
     *
     * @return false
     */
    public Boolean isSetRefresh() {
        return false;
    }

    protected abstract T createPresenter();

    abstract protected int provideContentViewId();//用于引入布局文件

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

        //允许为空，不是所有都要实现MVP模式
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }

        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }

        //引入布局文件
        setContentView(provideContentViewId());//布局

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

        SmartRefreshLayout smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        if (smartRefreshLayout != null) {
            getSmartRefreshPrimaryColorsTheme(smartRefreshLayout);
        }

        if (isSetRefresh()) {
            //判断子Activity是否需要刷新功能
            setupSwipeRefresh();
        }

        ButterKnife.bind(this);
    }

    /**
     * 判断子Activity是否需要刷新功能
     */
    private void setupSwipeRefresh() {
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        if (mRefreshLayout != null) {
            //设置下拉刷新进度条颜色变化
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
                    R.color.refresh_progress_3);
            //第一个参数scale是刷新圆形进度是否缩放,true表示缩放,圆形进度图会从小到大展示出来,为false就不缩放
            //第二个参数start和end是刷新进度条展示的相对于默认的展示位置,start和end组成一个范围，在这个y轴范围就是那个圆形进度ProgressView展示的位置
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(this::requestDataRefresh);
        }
    }

    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }

    /**
     * 刷新数据
     */
    public void setRefresh(boolean requestDataRefresh) {
        if (mRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            //不刷新
            mIsRequestDataRefresh = false;
            mRefreshLayout.postDelayed(() -> {
                if (mRefreshLayout != null) {
                    mRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        } else {
            //刷新效果
            mRefreshLayout.setRefreshing(true);
        }
    }

    /**
     * 本地化存储操作(存入) - 设置状态栏颜色
     *
     * @param ACTIONBAR_COLOR
     */
    public void setActionBarTheme(int ACTIONBAR_COLOR) {
        SPUtils.put(MVPBaseActivity.this, ACTIONBAR_COLOR_THEME, ACTIONBAR_COLOR);
    }

    /**
     * 本地化存储操作(取出) - 设置状态栏颜色
     * //toolbar_layout  CollapsingToolbarLayout  app:contentScrim="?attr/colorPrimary"  app:statusBarScrim="?attr/colorPrimary"
     */
    public void getActionBarTheme(FrameLayout baseActionBar, CollapsingToolbarLayout baseToolBar) {
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, ACTIONBAR_COLOR_THEME, 0);
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
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, ACTIONBAR_COLOR_THEME, 0);
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

    public void getSmartRefreshPrimaryColorsTheme(SmartRefreshLayout smartRefreshLayout) {
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);

        if (smartRefreshLayout != null) {
            //下拉刷新沉浸式水滴头部View
            smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(this));
            //上滑加载更多三点渐变动画底部View
            smartRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        }
        switch (actionBarColorInt) {
            case ACTIONBAR_COLOR_BLUE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_RED:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.red, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_BLACK:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_WHITE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_TRANSLATE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.translate, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_GREEN:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.palegreen, R.color.white);
                }
                break;
        }
    }

    //    private void initHeaderAndFooterView(SmartRefreshLayout smartRefreshLayout) {
//        //设置样式后面的背景颜色
////        alarmMessageSrlRefreshLayout.setPrimaryColorsId(R.color.color_blue, android.R.color.white);
//
//        //设置不同的头部、底部样式
////        //沉浸式圆圈头部刷新View
//        //沉浸式...+雷达
////        alarmMessageSrlRefreshLayout.setRefreshHeader(new BezierRadarHeader(this));
//        //沉浸式水滴
////        alarmMessageSrlRefreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));
////        alarmMessageSrlRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
////        alarmMessageSrlRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
//        // alarmMessageSrlRefreshLayout.setRefreshHeader(new PhoenixHeader(getActivity()));
//
//        //进度圆圈+正在加载...->加载完成
//        alarmMessageSrlRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
//        //三点渐变动画底部View
//        alarmMessageSrlRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
//    }

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
    public void addActivity(MVPBaseActivity baseActivity, Class<? extends Activity> target) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        activities.add(baseActivity);
        Log.d(TAG, "addActivity:   " + target);
    }

    public void finishAll() {
        for (MVPBaseActivity activity : activities) {
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
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void showProgressDialog(String content, boolean cancelable) {
        if (customProgressDialog == null) {
            customProgressDialog = new CustomProgressDialog(mActivity, content, cancelable);
        }
        if (!customProgressDialog.isShow()) {
            customProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShow()) {
            customProgressDialog.dismiss();
        }
    }
}
