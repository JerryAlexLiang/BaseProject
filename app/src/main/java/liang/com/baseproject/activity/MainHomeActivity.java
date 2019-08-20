package liang.com.baseproject.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.base.BaseActivity;
import liang.com.baseproject.base.PermissionActivity;
import liang.com.baseproject.fragment.FourFragment;
import liang.com.baseproject.fragment.JuheNewsContainerFragment;
import liang.com.baseproject.fragment.ZhiHuContainerFragment;
import liang.com.baseproject.fragment.NiceGankFragment;
import liang.com.baseproject.receiver.NetBroadcastReceiver;
import liang.com.baseproject.receiver.NetEvent;
import liang.com.baseproject.utils.CheckPermission;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.NetUtil;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.utils.WifiUtils;

import static liang.com.baseproject.Constant.Constant.NETWORK_MOBILE;
import static liang.com.baseproject.Constant.Constant.NETWORK_WIFI;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainHomeActivity extends BaseActivity {

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

    private CheckPermission mCheckPermission;                        //检测权限工具

    //配置需要取的权限
    static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,                // SD卡写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,                // SD卡读取权限
            Manifest.permission.ACCESS_FINE_LOCATION,                //Android9.0之后获取Wifi SSID 需要定位权限
    };

    private NetBroadcastReceiver netReceiver;

    //FragmentList
    private List<Fragment> fragmentList = new ArrayList<>();
    //标题列表
    private List<String> titleList = new ArrayList<>();
    private JuheNewsContainerFragment juheNewsContainerFragment;
    private NiceGankFragment niceGankFragment;
    private ZhiHuContainerFragment zhiHuContainerFragment;
    private FourFragment fourFragment;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;

    //记录ViewPage当前的选中界面
    private int currentPosition = 0;
    private ImageView navUserIocn;
    private TextView navUserName;
    private TextView navUserMail;
    private TextView navWifiName;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ButterKnife.bind(this);
        addActivity(this, MainHomeActivity.class);

        //请求权限
        initPermission();
        //注册监听网络状态的广播接收者
        initReceive();
        //订阅EventBus事件
        EventBus.getDefault().register(this);

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarLeftIcon.setImageResource(R.drawable.icon_drawer_menu);
        baseToolbarTitle.setVisibility(View.VISIBLE);

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

    private void initDrawerLayout() {
        //nav_header
        View navViewHeaderView = navView.getHeaderView(0);
        navUserIocn = navViewHeaderView.findViewById(R.id.nav_icon_user_image);
        navUserName = navViewHeaderView.findViewById(R.id.nav_tv_user_name);
        navUserMail = navViewHeaderView.findViewById(R.id.nav_tv_user_mail);
        navWifiName = navViewHeaderView.findViewById(R.id.nav_tv_wifi_ssid);
        //nav_menu
        setupDrawerContent(navView);

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
    }

    private void setupDrawerContent(NavigationView navView) {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemOrder = menuItem.getOrder();
                switch (menuItem.getItemId()) {
                    case R.id.menu_nav_scan:
                        //扫描界面
                        startActivity(new Intent(MainHomeActivity.this, ScanCodeActivity.class));
                        break;

                    case R.id.menu_nav_friends:
                        //改变主题
                        ThemeSettingActivity.actionStart(MainHomeActivity.this);
                        break;

                    case R.id.menu_nav_follow:
                        //闪屏
                        SplashTwoActivity.actionStart(MainHomeActivity.this);
                        break;

                    case R.id.menu_nav_feedback:
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
        juheNewsContainerFragment = new JuheNewsContainerFragment();
        niceGankFragment = new NiceGankFragment();
        zhiHuContainerFragment = new ZhiHuContainerFragment();
        fourFragment = new FourFragment();

        fragmentList.add(juheNewsContainerFragment);
        fragmentList.add(niceGankFragment);
        fragmentList.add(zhiHuContainerFragment);
        fragmentList.add(fourFragment);

        titleList.add("聚合新闻");
        titleList.add("颜如玉");
        titleList.add("知乎日报");
        titleList.add("特色");

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
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case 1:
                        currentPosition = 1;
                        mainRgRbTwo.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "切换模式", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case 2:
                        currentPosition = 2;
                        mainRgRbThree.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case 3:
                        currentPosition = 3;
                        mainRgRbFour.setChecked(true);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "特色产品", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case R.id.main_rg_rb_two:
                        //切换fragment
//                        switchFragment(1);
                        myViewPager.setCurrentItem(1);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "切换模式", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case R.id.main_rg_rb_three:
                        //切换fragment
//                        switchFragment(2);
                        myViewPager.setCurrentItem(2);
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case R.id.main_rg_rb_four:
                        //切换fragment
//                        switchFragment(3);
                        myViewPager.setCurrentItem(3);
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "特色产品", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
                baseToolbarTitle.setText(titleList.get(currentPosition));
            }
        });
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
//                Toast.makeText(MainHomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                ToastUtil.setCustomToast(MainHomeActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                        true, "再按一次退出应用", Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
                touchTime = currentTime;
            } else {
                finish();
//                System.exit(0);
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

    @OnClick({R.id.base_actionbar_left_icon, R.id.rl_net_bar})
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netReceiver);
        EventBus.getDefault().unregister(this);
    }

}
