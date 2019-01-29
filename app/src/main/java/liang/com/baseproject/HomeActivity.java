package liang.com.baseproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.base.BaseActivity;
import liang.com.baseproject.fragment.FourFragment;
import liang.com.baseproject.fragment.OneFragment;
import liang.com.baseproject.fragment.ThreeFragment;
import liang.com.baseproject.fragment.TwoFragment;
import liang.com.baseproject.widget.MyViewPage;
import ru.alexbykov.nopermission.PermissionHelper;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.base_toolbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_toolbar)
    FrameLayout baseToolbar;
    @BindView(R.id.my_view_pager)
    ViewPager myViewPager;
    //    MyViewPage myViewPager;
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

    private PermissionHelper permissionHelper;

    //FragmentList
    private List<Fragment> fragmentList = new ArrayList<>();
//    private List<String> fragmentList = new ArrayList<>();
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;

    //记录ViewPage当前的选中界面
    private int currentPosition = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        addActivity(this, HomeActivity.class);
        getActionBarTheme(baseToolbar);

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarLeftIcon.setImageResource(R.drawable.icon_drawer_menu);
        baseToolbarTitle.setVisibility(View.VISIBLE);

        permissionHelper = new PermissionHelper(this);
        getWifiSSid();

        initViewPage();
        initEvent();

    }

    private void initEvent() {
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

    private void initViewPage() {
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();

        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);

        //ViewPager的适配器
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(fragmentViewPagerAdapter);
        myViewPager.setOffscreenPageLimit(2);
//        myViewPager.setRightDistance(getWindowManager().getDefaultDisplay().getWidth());
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
                        baseToolbarTitle.setText("礼包");
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case 1:
                        currentPosition = 1;
                        mainRgRbTwo.setChecked(true);
                        baseToolbarTitle.setText("开服");
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "开服测试", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case 2:
                        currentPosition = 2;
                        mainRgRbThree.setChecked(true);
                        baseToolbarTitle.setText("热门");
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case 3:
                        currentPosition = 3;
                        mainRgRbFour.setChecked(true);
                        baseToolbarTitle.setText("特色");
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "哈哈哈", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radioGroupContainerHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_rg_rb_one:
                        //切换fragment
//                        switchFragment(0);
                        myViewPager.setCurrentItem(0);
                        baseToolbarTitle.setText("礼包");
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case R.id.main_rg_rb_two:
                        //切换fragment
//                        switchFragment(1);
                        myViewPager.setCurrentItem(1);
                        baseToolbarTitle.setText("开服");
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "开服测试", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case R.id.main_rg_rb_three:
                        //切换fragment
//                        switchFragment(2);
                        myViewPager.setCurrentItem(2);
                        baseToolbarTitle.setText("热门");
                        baseToolbarRightIcon.setVisibility(View.GONE);
                        break;

                    case R.id.main_rg_rb_four:
                        //切换fragment
//                        switchFragment(3);
                        myViewPager.setCurrentItem(3);
                        baseToolbarTitle.setText("特色");
                        baseToolbarRightIcon.setVisibility(View.VISIBLE);
                        baseToolbarRightIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mActivity, "特色产品", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
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

    private void getWifiSSid() {
        permissionHelper.check(Manifest.permission.ACCESS_FINE_LOCATION).onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
    }

    private long waitTime = 2000;
    private long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
                System.exit(0);
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
        getActionBarTheme(baseToolbar);
    }

    public String getWIFISSID(Activity activity) {
        String ssid = "unknown id";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {

            WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {

            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo().replace("\"", "");
                }
            }
        }
        return ssid;
    }

    private void onSuccess() {
        String wifissid = getWIFISSID(this);
        System.out.println("====>   >   >  " + wifissid);
    }

    private void onDenied() {
    }

    private void onNeverAskAgain() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.base_toolbar_left_icon)
    public void onViewClicked() {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
