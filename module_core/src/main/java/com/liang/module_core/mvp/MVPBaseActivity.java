package com.liang.module_core.mvp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.liang.module_core.R;
import com.liang.module_core.app.BaseApplication;
import com.liang.module_core.constant.Constant;
import com.liang.module_core.utils.SPUtils;
import com.liang.module_core.utils.ToastUtil;
import com.liang.module_core.widget.CustomProgressDialog;
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
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


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
    private String lottieFileName = "";
    private MyRefreshLottieHeader myRefreshLottieHeader;

    /**
     * 管理所有的activity资源对象
     */
    private static List<MVPBaseActivity> activities;
    public Activity mActivity;
    private CustomProgressDialog customProgressDialog;

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
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
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
        SPUtils.put(MVPBaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, ACTIONBAR_COLOR);
    }

    /**
     * 本地化存储操作(取出) - 设置状态栏颜色
     * //toolbar_layout  CollapsingToolbarLayout  app:contentScrim="?attr/colorPrimary"  app:statusBarScrim="?attr/colorPrimary"
     */
    public void getActionBarTheme(FrameLayout baseActionBar, CollapsingToolbarLayout baseCollapsingToolBar) {
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case Constant.ACTIONBAR_COLOR_BLUE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.title_bar_blue));
                }
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(getResources().getColor(R.color.title_bar_blue));
                    baseCollapsingToolBar.setStatusBarScrimColor(getResources().getColor(R.color.title_bar_blue));
                }
                break;

            case Constant.ACTIONBAR_COLOR_RED:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.title_bar_red));
                }
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(getResources().getColor(R.color.title_bar_red));
                    baseCollapsingToolBar.setStatusBarScrimColor(getResources().getColor(R.color.title_bar_red));
                }
                break;

            case Constant.ACTIONBAR_COLOR_BLACK:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.BLACK);
                }
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(Color.BLACK);
                    baseCollapsingToolBar.setStatusBarScrimColor(Color.BLACK);
                }
                break;

            case Constant.ACTIONBAR_COLOR_WHITE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.WHITE);
                }
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(Color.WHITE);
                    baseCollapsingToolBar.setStatusBarScrimColor(Color.WHITE);
                }
                break;

            case Constant.ACTIONBAR_COLOR_TRANSLATE:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(Color.TRANSPARENT);
                }
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(Color.TRANSPARENT);
                    baseCollapsingToolBar.setStatusBarScrimColor(Color.TRANSPARENT);
                }
                break;

            case Constant.ACTIONBAR_COLOR_GREEN:
                if (baseActionBar != null) {
                    baseActionBar.setBackgroundColor(getResources().getColor(R.color.title_bar_green));
                }
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(getResources().getColor(R.color.title_bar_green));
                    baseCollapsingToolBar.setStatusBarScrimColor(getResources().getColor(R.color.title_bar_green));
                }
                break;
        }
    }

    public void getSplashTheme(RelativeLayout relativeLayout) {
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, 0);
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
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, Constant.ACTIONBAR_COLOR_THEME, 0);
        String refreshHeaderStyle = (String) SPUtils.get(BaseApplication.getAppContext(), Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);

        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                setRefreshHeader(smartRefreshLayout, refreshHeaderStyle);
            }

            if (isSetRefreshFooter) {
                //上滑加载更多三点渐变动画底部View
//                smartRefreshLayout.setRefreshFooter(new BallPulseFooter(MyApplication.getAppContext()).setSpinnerStyle(SpinnerStyle.Scale));
                smartRefreshLayout.setRefreshFooter(new ClassicsFooter(BaseApplication.getAppContext()));
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
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);
                break;

            case Constant.REFRESH_HEADER_28402_TEMPLO:
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_28402_TEMPLO);
                break;

            case Constant.MaterialHeader:
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.MaterialHeader);
                break;

            case Constant.WaterDropHeader:
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.WaterDropHeader);
                break;

            case Constant.WaveSwipeHeader:
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.WaveSwipeHeader);
                break;

            case Constant.BezierCircleHeader:
                //水滴下拉
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.BezierCircleHeader);
                break;

            case Constant.PhoenixHeader:
                //大楼动画
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.PhoenixHeader);
                break;

            case Constant.TaurusHeader:
                //飞机滑翔动画效果
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.TaurusHeader);
                break;

            case Constant.FlyRefreshHeader:
                //飞机效果2
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FlyRefreshHeader);
                break;

            case Constant.ClassicsHeader:
                //经典带时间的刷新
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.ClassicsHeader);
                break;

            case Constant.BezierRadarHeader:
                //雷达动画
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.BezierRadarHeader);
                break;

            case Constant.DeliveryHeader:
                //快递交付动画
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.DeliveryHeader);
                break;

            case Constant.DropBoxHeader:
                //礼物盒子动画效果
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.DropBoxHeader);
                break;

            case Constant.FalsifyHeader:
                //无动画效果
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FalsifyHeader);
                break;

            case Constant.FunGameBattleCityHeader:
                //子弹游戏效果
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FunGameBattleCityHeader);
                break;

            case Constant.FunGameHitBlockHeader:
                //碰球游戏效果
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.FunGameHitBlockHeader);
                break;

            case Constant.StoreHouseHeader:
                //StoreHouse文字渐变效果
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.StoreHouseHeader);
                break;


            case Constant.TwoLevelHeader:
                //二楼
                SPUtils.put(MVPBaseActivity.this, Constant.REFRESH_HEADER_STYLE, Constant.TwoLevelHeader);
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


    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void jumpToActivity(Class activity, int requestCode) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, requestCode);
    }

    public void jumpToActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
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


//    /**
//     * 监控键盘状态
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideKeyboard(v, ev)) {
//                hideKeyboard(v.getWindowToken());
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    /**
//     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
//     *
//     * @param v
//     * @param event
//     * @return
//     */
//    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] l = {0, 0};
//            v.getLocationInWindow(l);
//            int left = l[0],
//                    top = l[1],
//                    bottom = top + v.getHeight(),
//                    right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击EditText的事件，忽略它。
//                return false;
//            } else {
//                v.clearFocus();
//                return true;
//            }
//        }
//        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
//        return false;
//    }
//
//    /**
//     * 获取InputMethodManager，隐藏软键盘
//     *
//     * @param token
//     */
//    private void hideKeyboard(IBinder token) {
//        if (token != null) {
//            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }

}
