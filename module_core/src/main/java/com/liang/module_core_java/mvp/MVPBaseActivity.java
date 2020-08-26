package com.liang.module_core_java.mvp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liang.module_core_java.R;
import com.liang.module_core_java.app.BaseApplication;
import com.liang.module_core_java.constant.Constant;
import com.liang.module_core_java.widget.CustomProgressDialog;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import com.liang.module_core_java.utils.SPUtils;
import com.liang.module_core_java.utils.ToastUtil;


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
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);

//        smartRefreshLayout.setRefreshHeader(new MaterialHeader(MyApplication.getAppContext())); //经典Swip
//        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(MyApplication.getAppContext())); //弹性水滴效果
//        smartRefreshLayout.setRefreshHeader(new WaveSwipeHeader(MyApplication.getAppContext()));//下坠水滴效果
//        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(MyApplication.getAppContext())); //水滴下拉
//        smartRefreshLayout.setRefreshHeader(new PhoenixHeader(MyApplication.getAppContext()));  //大楼动画
//        smartRefreshLayout.setRefreshHeader(new TaurusHeader(MyApplication.getAppContext()));  //飞机滑翔动画效果
//        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(MyApplication.getAppContext()));   //经典带时间的刷新
//        smartRefreshLayout.setRefreshHeader(new TwoLevelHeader(MyApplication.getAppContext()));   //经典带时间的刷新
//        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(MyApplication.getAppContext()));  //雷达动画
//        smartRefreshLayout.setRefreshHeader(new DeliveryHeader(MyApplication.getAppContext()));   //快递交付动画
//        smartRefreshLayout.setRefreshHeader(new DropBoxHeader(MyApplication.getAppContext())); //礼物盒子动画效果
//        smartRefreshLayout.setRefreshHeader(new FalsifyHeader(MyApplication.getAppContext()));  //无动画效果
//        smartRefreshLayout.setRefreshHeader(new FlyRefreshHeader(MyApplication.getAppContext()));
//        smartRefreshLayout.setRefreshHeader(new FunGameBattleCityHeader(MyApplication.getAppContext())); //子弹游戏效果
//        smartRefreshLayout.setRefreshHeader(new FunGameHitBlockHeader(MyApplication.getAppContext()));   //碰球游戏效果
//        smartRefreshLayout.setRefreshHeader(new StoreHouseHeader(MyApplication.getAppContext()));  //StoreHouse文字渐变效果
        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                //下拉刷新沉浸式水滴头部View
                smartRefreshLayout.setRefreshHeader(new MaterialHeader(BaseApplication.getAppContext()));//经典Swip
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

    /*
        //官网给的一些设置属性方法
        //下面示例中的值等于默认值
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）

        refreshLayout.setHeaderHeight(100);//Header标准高度（显示下拉高度>=标准高度 触发刷新）
        refreshLayout.setHeaderHeightPx(100);//同上-像素为单位 （V1.1.0删除）
        refreshLayout.setFooterHeight(100);//Footer标准高度（显示上拉高度>=标准高度 触发加载）
        refreshLayout.setFooterHeightPx(100);//同上-像素为单位 （V1.1.0删除）

        refreshLayout.setFooterHeaderInsetStart(0);//设置 Header 起始位置偏移量 1.0.5
        refreshLayout.setFooterHeaderInsetStartPx(0);//同上-像素为单位 1.0.5 （V1.1.0删除）
        refreshLayout.setFooterFooterInsetStart(0);//设置 Footer 起始位置偏移量 1.0.5
        refreshLayout.setFooterFooterInsetStartPx(0);//同上-像素为单位 1.0.5 （V1.1.0删除）

        refreshLayout.setHeaderMaxDragRate(2);//最大显示下拉高度/Header标准高度
        refreshLayout.setFooterMaxDragRate(2);//最大显示下拉高度/Footer标准高度
        refreshLayout.setHeaderTriggerRate(1);//触发刷新距离 与 HeaderHeight 的比率1.0.4
        refreshLayout.setFooterTriggerRate(1);//触发加载距离 与 FooterHeight 的比率1.0.4

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnablePureScrollMode(false);//是否启用纯滚动模式
        refreshLayout.setEnableNestedScroll(false);//是否启用嵌套滚动
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableFooterFollowWhenLoadFinished(false);//是否在全部加载结束之后Footer跟随内容1.0.4
        refreshLayout.setEnableOverScrollDrag(false);//是否启用越界拖动（仿苹果效果）1.0.4

        refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
        refreshLayout.srlEnableClipHeaderWhenFixedBehind(true);//是否剪裁Header当时样式为FixedBehind时1.0.5
        refreshLayout.srlEnableClipFooterWhenFixedBehind(true);//是否剪裁Footer当时样式为FixedBehind时1.0.5

        refreshLayout.setDisableContentWhenRefresh(false);//是否在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(false);//是否在加载的时候禁止列表的操作

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener());//设置多功能监听器
        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider());//设置滚动边界判断
        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter());//自定义滚动边界

        refreshLayout.setRefreshHeader(new ClassicsHeader(context));//设置Header
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));//设置Footer
        refreshLayout.setRefreshContent(new View(context));//设置刷新Content（用于非xml布局代替addView）1.0.4

        refreshLayout.autoRefresh();//自动刷新
        refreshLayout.autoLoadMore();//自动加载
        refreshLayout.autoRefresh(400);//延迟400毫秒后自动刷新
        refreshLayout.autoLoadMore(400);//延迟400毫秒后自动加载
        refreshLayout.finishRefresh();//结束刷新
        refreshLayout.finishLoadMore();//结束加载
        refreshLayout.finishRefresh(3000);//延迟3000毫秒后结束刷新
        refreshLayout.finishLoadMore(3000);//延迟3000毫秒后结束加载
        refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
        refreshLayout.finishLoadMore(false);//结束加载（加载失败）
        refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
        refreshLayout.closeHeaderOrFooter();//关闭正在打开状态的 Header 或者 Footer（1.1.0）
        refreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态 1.0.4（1.1.0删除）
        refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
     */

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
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                false, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void onShowTrueToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                true, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void onShowErrorToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_wrong),
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
