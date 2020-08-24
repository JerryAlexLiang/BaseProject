package liang.com.baseproject.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liang.model_middleware.impl.ServiceProvider;
import com.liang.model_middleware.router.AppRouter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.base.BaseActivity;
import liang.com.baseproject.base.PermissionActivity;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.fragment.JuheNewsContainerFragment;
import liang.com.baseproject.fragment.NiceGankFragment;
import liang.com.baseproject.home.activity.SearchWanArticleActivity;
import liang.com.baseproject.home.fragment.HomeContainerFragment;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.map.MapLocationActivity;
import liang.com.baseproject.mine.MineFragment;
import liang.com.baseproject.receiver.NetBroadcastReceiver;
import liang.com.baseproject.receiver.NetEvent;
import liang.com.baseproject.utils.CheckPermission;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.NetUtil;
import liang.com.baseproject.utils.UserLoginUtils;
import liang.com.baseproject.utils.WifiUtils;

import static liang.com.baseproject.Constant.Constant.NETWORK_MOBILE;
import static liang.com.baseproject.Constant.Constant.NETWORK_WIFI;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainHomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainHomeActivity.class.getSimpleName();
    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_actionbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionBar;
    @BindView(R.id.my_view_pager)
//    MyViewPage myViewPager;
            ViewPager myViewPager;
    @BindView(R.id.main_rg_rb_one)
    RadioButton mainRgRbOne;
    @BindView(R.id.main_rg_rb_two)
    RadioButton mainRgRbTwo;
    @BindView(R.id.main_rg_rb_three)
    RadioButton mainRgRbThree;
    @BindView(R.id.main_rg_rb_four)
    RadioButton mainRgRbFour;
    @BindView(R.id.radio_group_container_home)
    RadioGroup radioGroupContainerHome;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.iv_net_warn)
    ImageView ivNetWarn;
    @BindView(R.id.tv_net_title)
    TextView tvNetTitle;
    @BindView(R.id.rl_net_bar)
    RelativeLayout rlNetBar;

    /**
     * android 6.0 或以上权限申请
     */
    private static final int REQUEST_CODE = 0;                        //权限请求码
    @BindView(R.id.rl_main_page_container)
    RelativeLayout rlMainPageContainer;

    private CheckPermission mCheckPermission;                        //检测权限工具

    //配置需要取的权限
    static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,                // SD卡写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,                // SD卡读取权限
            Manifest.permission.ACCESS_FINE_LOCATION,                //Android9.0之后获取Wifi SSID 需要定位权限
            Manifest.permission.CAMERA,
    };

    private NetBroadcastReceiver netReceiver;

    //FragmentList
    private List<Fragment> fragmentList = new ArrayList<>();
    //标题列表
    private List<String> titleList = new ArrayList<>();
    private HomeContainerFragment homeContainerFragment;
    private JuheNewsContainerFragment juheNewsContainerFragment;
    private NiceGankFragment niceGankFragment;
    private MineFragment mineFragment;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;

    //记录ViewPage当前的选中界面
    private int currentPosition = 0;
    private boolean currentNetStatus;
    private ImageView navUserIocn;
    private TextView navUserName;
    private TextView navUserMail;
    private TextView navWifiName;
    private View navViewHeaderView;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化路由ARouter
        ARouter.getInstance().inject(this);

        fullScreen(false);
        setContentView(R.layout.activity_main_home);
        ButterKnife.bind(this);
        addActivity(this, MainHomeActivity.class);

        //请求权限
        initPermission();
        //注册监听网络状态的广播接收者
        initReceive();

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarLeftIcon.setImageResource(R.drawable.icon_drawer_menu);
        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));

        initViewPage();
        initDrawerLayout();

    }

    private void initReceive() {
        netReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, intentFilter);
    }

    @Subscribe
    public void onEventMainThread(NetEvent event) {
        setNetState(event.isNet(), event.networkStatus);
    }

    private void setNetState(boolean net, int networkStatus) {
        currentNetStatus = net;
        if (net) {
            //获取网络状态
            rlNetBar.setVisibility(View.GONE);
            getNetStatus(networkStatus);
        } else {
            rlNetBar.setVisibility(View.VISIBLE);
            navWifiName.setText("没有连接网络...");
        }
    }

    private void getNetStatus(int networkStatus) {
        if (networkStatus == NETWORK_MOBILE) {
            //移动网络
            navWifiName.setText("移动数据上网");
        } else if (networkStatus == NETWORK_WIFI) {
            //无线网络
            //Wifi的SSID(名称)
            String wifiName = WifiUtils.getWifiName(this);
            LogUtil.d(TAG, "wifi名称: " + wifiName);
            navWifiName.setText("Wifi: " + wifiName);
        }
    }


    /**
     * 进入权限设置页面
     */
    private void initPermission() {
        //SDK版本小于23时候不做检测
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        mCheckPermission = new CheckPermission(this);
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PERMISSION)) {
            startPermissionActivity();
        }
    }

    private void startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //权限请求码
            if (resultCode == PermissionActivity.PERMISSION_DENIEG) {
                //拒绝时，没有获取到主要权限，无法运行，关闭页面
                finish();
            } else {
                //注册监听网络状态的广播接收者
                initReceive();
            }
        }
    }

    /**
     * 接收登录消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        boolean login = event.isLogin();
        LogUtil.e("ppppp1", "登录状态: " + login);
        //更新用户UI
        initUserInfo();
    }

    private void initUserInfo() {
        if (UserLoginUtils.getInstance().isLogin()) {
            UserBean loginUserBean = UserLoginUtils.getInstance().getLoginUserBean();
            String nickname = loginUserBean.getNickname();
            int id = loginUserBean.getId();
            String localUserIcon = UserLoginUtils.getInstance().getLocalUserIcon();
            String localBg = UserLoginUtils.getInstance().getLocalBg();

            LogUtil.e("ppppp2", "头像地址: " + localUserIcon);
            LogUtil.e("ppppp3", "背景地址: " + localBg);

            navUserMail.setText("ID: " + id);
            if (!TextUtils.isEmpty(nickname)) {
                navUserName.setText(nickname);
            }

            if (!TextUtils.isEmpty(localUserIcon)) {
                Glide.with(MainHomeActivity.this).asBitmap().load(localUserIcon).into(navUserIocn);
            } else {
                navUserIocn.setBackgroundResource(R.drawable.icon_user_logo);
            }

            if (!TextUtils.isEmpty(localBg)) {
                Glide.with(MainHomeActivity.this).load(localBg).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        navViewHeaderView.setBackground(resource);
                    }
                });
            } else {
                navViewHeaderView.setBackgroundResource(R.drawable.icon_info_bg);
            }
        } else {
            Glide.with(mActivity).asBitmap().load(R.drawable.icon_user_logo).into(navUserIocn);
            navViewHeaderView.setBackgroundResource(R.drawable.icon_info_bg);
            navUserMail.setText("ID: ");
            navUserName.setText("昵称");
        }
    }

    private void initDrawerLayout() {
        //nav_header
        navViewHeaderView = navView.getHeaderView(0);
        navUserIocn = navViewHeaderView.findViewById(R.id.nav_icon_user_image);
        navUserName = navViewHeaderView.findViewById(R.id.nav_tv_user_name);
        navUserMail = navViewHeaderView.findViewById(R.id.nav_tv_user_mail);
        navWifiName = navViewHeaderView.findViewById(R.id.nav_tv_wifi_ssid);
        //设置跑马灯效果
        navWifiName.setSelected(true);
        //nav_menu
        setupDrawerContent(navView);

        initUserInfo();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawerView.setClickable(true);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navUserIocn.setOnClickListener(this);
        navUserName.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_icon_user_image:
                if (UserLoginUtils.getInstance().isLogin()) {
                    onShowToast("开发中...");
                } else {
                    if (Build.VERSION.SDK_INT > 20) {
                        Bundle options = ActivityOptions.makeSceneTransitionAnimation(MainHomeActivity.this, navUserIocn, "navUserIocn").toBundle();
                        Intent intent = new Intent(MainHomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent, options);
                    } else {
                        LoginActivity.actionStart();
                    }
                }
                break;

            case R.id.nav_tv_user_name:
                boolean ifNeedLogin = UserLoginUtils.getInstance().doIfNeedLogin();
                if (ifNeedLogin) {
                    onShowToast("开发中...");
                }
                break;

            default:
                break;
        }
    }

    private void setupDrawerContent(NavigationView navView) {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemOrder = menuItem.getOrder();
                switch (menuItem.getItemId()) {
                    case R.id.menu_nav_weather:
//                        ServiceProvider.getWeatherService().startWeatherActivity(MainHomeActivity.this);

                        ARouter.getInstance().build(AppRouter.MODULE_WEATHER_PATH).navigation();

                        break;

                    case R.id.menu_nav_scan:
                        //扫描界面
                        onShowToast("扫一扫");
                        break;

                    case R.id.menu_nav_night_theme:
                        //改变主题
                        ThemeSettingActivity.actionStart(MainHomeActivity.this);
                        break;

                    case R.id.menu_nav_location:
                        MapLocationActivity.actionStart(MainHomeActivity.this);
                        break;

                    case R.id.menu_nav_follow:
                        //闪屏
                        SplashTwoActivity.actionStart(MainHomeActivity.this);
                        break;

                    case R.id.menu_nav_feedback:
                        onShowToast("反馈");
                        break;

                    case R.id.menu_nav_laboratory:
                        TestCodeActivity.actionStart(MainHomeActivity.this);
                        break;

                    case R.id.menu_nav_setting:
                        SettingActivity.actionStart(MainHomeActivity.this);
                        break;
                }
                // 关闭侧滑菜单
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initViewPage() {
        homeContainerFragment = new HomeContainerFragment();
        juheNewsContainerFragment = new JuheNewsContainerFragment();
        niceGankFragment = new NiceGankFragment();
        mineFragment = new MineFragment();

        fragmentList.add(homeContainerFragment);
        fragmentList.add(juheNewsContainerFragment);
        fragmentList.add(niceGankFragment);
        fragmentList.add(mineFragment);

        titleList.add("推荐");
        titleList.add("聚合新闻");
        titleList.add("颜如玉");
        titleList.add("我的");

        //ViewPager的适配器
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        myViewPager.setCurrentItem(currentPosition);
        baseToolbarTitle.setText(titleList.get(currentPosition));
        myViewPager.setOffscreenPageLimit(3);//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
//        myViewPager.setRightDistance(getWindowManager().getDefaultDisplay().getWidth());
        myViewPager.setAdapter(fragmentViewPagerAdapter);

        //ViewPager滑动监听事件
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        //记录ViewPage当前的选中界面
                        currentPosition = 0;
                        mainRgRbOne.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转搜索文章界面
                                goToSearchWanAndroidArticle();
                            }
                        });
                        baseActionBar.setVisibility(View.VISIBLE);
                        if (currentNetStatus) {
                            rlNetBar.setVisibility(View.GONE);
                        } else {
                            rlNetBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 1:
                        currentPosition = 1;
                        mainRgRbTwo.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_more));
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onShowToast("切换模式");
                            }
                        });
                        baseActionBar.setVisibility(View.VISIBLE);
                        if (currentNetStatus) {
                            rlNetBar.setVisibility(View.GONE);
                        } else {
                            rlNetBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 2:
                        currentPosition = 2;
                        mainRgRbThree.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        baseActionBar.setVisibility(View.VISIBLE);
                        if (currentNetStatus) {
                            rlNetBar.setVisibility(View.GONE);
                        } else {
                            rlNetBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 3:
                        currentPosition = 3;
                        mainRgRbFour.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onShowToast("特色产品");
                            }
                        });
                        baseActionBar.setVisibility(View.GONE);
                        rlNetBar.setVisibility(View.GONE);
                        break;
                }
                baseToolbarTitle.setText(titleList.get(currentPosition));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radioGroupContainerHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_rg_rb_one:
                        //切换fragment
//                        switchFragment(0);
                        myViewPager.setCurrentItem(0);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转搜索文章界面
                                goToSearchWanAndroidArticle();
                            }
                        });
                        baseActionBar.setVisibility(View.VISIBLE);
                        if (currentNetStatus) {
                            rlNetBar.setVisibility(View.GONE);
                        } else {
                            rlNetBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case R.id.main_rg_rb_two:
                        //切换fragment
//                        switchFragment(1);
                        myViewPager.setCurrentItem(1);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_more));
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onShowToast("切换模式");
                            }
                        });
                        baseActionBar.setVisibility(View.VISIBLE);
                        if (currentNetStatus) {
                            rlNetBar.setVisibility(View.GONE);
                        } else {
                            rlNetBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case R.id.main_rg_rb_three:
                        //切换fragment
//                        switchFragment(2);
                        myViewPager.setCurrentItem(2);
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        baseActionBar.setVisibility(View.VISIBLE);
                        if (currentNetStatus) {
                            rlNetBar.setVisibility(View.GONE);
                        } else {
                            rlNetBar.setVisibility(View.VISIBLE);
                        }
                        break;

                    case R.id.main_rg_rb_four:
                        //切换fragment
//                        switchFragment(3);
                        myViewPager.setCurrentItem(3);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onShowToast("特色产品");
                            }
                        });
                        baseActionBar.setVisibility(View.GONE);
                        rlNetBar.setVisibility(View.GONE);
                        break;
                }
                baseToolbarTitle.setText(titleList.get(currentPosition));
            }
        });
    }

    /**
     * 跳转搜索文章界面
     */
    private void goToSearchWanAndroidArticle() {
        SearchWanArticleActivity.actionStart(mActivity);
    }

    /**
     * 如果布局是在FrameLayout中放入fragment，切换fragment的方法
     *
     * @param i
     */
    private void switchFragment(int i) {
        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //选择fragment
        Fragment fragment = fragmentList.get(i);

        if (!fragment.isAdded()) {
            //隐藏当前，增加fragment
            transaction.hide(fragmentList.get(currentPosition)).add(R.id.my_view_pager, fragment);
        } else {
            //隐藏当前，显示可添加
            transaction.hide(fragmentList.get(currentPosition)).show(fragment);
        }

        //提交事务
        transaction.commit();
        currentPosition = i;
    }


    private long waitTime = 2000;
    private long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                onShowTrueToast("再按一次退出应用");
                touchTime = currentTime;
            } else {
                finish();
            }
            return true;
        } else if (KeyEvent.KEYCODE_HOME == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionBarTheme(baseActionBar, null);
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.rl_net_bar, R.id.base_actionbar_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.rl_net_bar:
//                NetUtil.openWifiSetting(MainHomeActivity.this);
//                NetUtil.openSystemSetting(MainHomeActivity.this);
//                NetUtil.openMobileNetSetting(MainHomeActivity.this);
                NetUtil.openNetSetting(MainHomeActivity.this);
                break;

            case R.id.base_actionbar_right_icon:
                //跳转搜索文章界面
                goToSearchWanAndroidArticle();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netReceiver);
        LogUtil.d(TAG, "onDestroy()");
    }

    /**
     * 根据子Fragment显示隐藏标题栏
     */
    public void showBaseActionBar() {
        baseActionBar.setVisibility(View.VISIBLE);
    }

    public void hideBaseActionBar() {
        baseActionBar.setVisibility(View.GONE);
    }

    public RelativeLayout getMainPageContainer() {
        return rlMainPageContainer;
    }
}
