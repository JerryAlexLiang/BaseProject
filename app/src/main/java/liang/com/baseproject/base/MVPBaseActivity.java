package liang.com.baseproject.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.utils.SPUtils;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_THEME;

/**
 * 创建日期：2019/1/24 on 11:04
 * 描述: MVPBaseActivity - 通用标题栏模式
 * 作者: liangyang
 */
public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    private static final String TAG = MVPBaseActivity.class.getSimpleName();

    protected T mPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsRequestDataRefresh = false;

    /**
     * 管理所有的activity资源对象
     */
    private static List<MVPBaseActivity> activities;
    public Activity mActivity;

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

        //引入布局文件
        setContentView(provideContentViewId());//布局

        //本地化存储操作(取出) - 设置状态栏颜色
        FrameLayout baseToolbar = findViewById(R.id.base_toolbar);
        if (baseToolbar != null) {
            getActionBarTheme(baseToolbar);
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
        if (!requestDataRefresh){
            //不刷新
            mIsRequestDataRefresh = false;
            mRefreshLayout.postDelayed(()->{
                if (mRefreshLayout!=null){
                    mRefreshLayout.setRefreshing(false);
                }
            },1000);
        }else {
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
     *
     * @param baseToolbar
     */
    public void getActionBarTheme(FrameLayout baseToolbar) {
        int actionBarColorInt = (int) SPUtils.get(MVPBaseActivity.this, ACTIONBAR_COLOR_THEME, 0);
        Log.d(TAG, "setActionBarTheme: " + actionBarColorInt);
        switch (actionBarColorInt) {
            case 0:
                baseToolbar.setBackgroundColor(Color.RED);
                break;

            case 1:
                baseToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;

            case 2:
                baseToolbar.setBackgroundColor(Color.BLACK);
                break;

            case 3:
                baseToolbar.setBackgroundColor(Color.WHITE);
                break;

            case 4:
                baseToolbar.setBackgroundColor(Color.TRANSPARENT);
                break;

            case 5:
                baseToolbar.setBackgroundColor(getResources().getColor(R.color.palegreen));
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
    }
}
