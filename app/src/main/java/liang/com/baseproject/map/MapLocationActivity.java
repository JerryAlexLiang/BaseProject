package liang.com.baseproject.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.PermissionActivity;
import liang.com.baseproject.home.adapter.HomeContainerAdapter;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.presenter.HomeContainerPresenter;
import liang.com.baseproject.home.view.HomeContainerView;
import liang.com.baseproject.listener.AppBarStateChangeListener;
import liang.com.baseproject.utils.CheckPermission;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ResolutionUtils;
import liang.com.baseproject.utils.SettingUtils;
import liang.com.baseproject.widget.CustomScrollRelativeLayout;
import liang.com.baseproject.widget.popupwindow.CustomPopupWindow;

public class MapLocationActivity extends MVPBaseActivity<HomeContainerView, HomeContainerPresenter> implements HomeContainerView, AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener, AMap.OnMapTouchListener, CustomPopupWindow.ViewInterface, View.OnClickListener {

    private static final String TAG = MapLocationActivity.class.getSimpleName();
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_control_title)
    TextView tvControlTitle;
    @BindView(R.id.iv_control_btn)
    ImageView ivControlBtn;
    @BindView(R.id.rl_control_container)
    RelativeLayout rlControlContainer;
    @BindView(R.id.iv_btn_my_location)
    ImageView ivBtnMyLocation;
    @BindView(R.id.custom_scroll_container)
    CustomScrollRelativeLayout customScrollContainer;
    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.toolbar_back_layout)
    RelativeLayout toolbarBackLayout;
    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_toolbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.toolbar_right_layout)
    RelativeLayout toolbarRightLayout;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_map)
    RecyclerView rvMap;
    @BindView(R.id.rl_recycler_container)
    RelativeLayout rlRecyclerContainer;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_web_view_error)
    ImageView ivWebViewError;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private CustomPopupWindow customPopupWindow;

    /**
     * android 6.0 或以上权限申请
     */
    private static final int REQUEST_CODE = 0;                        //权限请求码

    //配置需要取的权限
    static final String[] PERMISSION = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private boolean mIsPermissionGanted = false;                    //是否授权成功
    private UiSettings uiSettings;
    private CheckPermission mCheckPermission;

    private boolean followMove = true;

    private boolean isSetCustomMapMode = false;
    private Map<String, LatLng> poaitionMap = new HashMap<>();
    private AMap aMap;

    private MyLocationStyle myLocationStyle;
    private HomeContainerAdapter homeContainerAdapter;

    private static final int PAGE_START = 0;
    private int currPage = PAGE_START;
    private boolean mDarkTheme;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MapLocationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return true;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected HomeContainerPresenter createPresenter() {
        return new HomeContainerPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_map_location;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //随系统设置更改ToolBar状态栏颜色
        getActionBarTheme(null, toolbarLayout);
        //得到Intent传递的数据
        parseIntent();
        //初始化地图视图
        initMap(savedInstanceState);
        //初始化视图
        initView();
    }

    private void initView() {
        //自定义的RelativeLayout-CollapsingToolbarLayout-滑动冲突
        customScrollContainer.setCollapsingToolbarLayout(toolbarLayout);
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        toolbarLayout.setTitle("Location");
        toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        toolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
        //设置CollapsingToolbarLayout扩展状态标题栏颜色
        toolbarLayout.setExpandedTitleColor(Color.YELLOW);
        //设置CollapsingToolbarLayout收缩状态标题栏颜色
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        initRecyclerView();

        //设置监听android.support.design.widget.AppBarLayout这个控件设置监听了
        //因为android.support.design.widget.CollapsingToolbarLayout外层是 android.support.design.widget.AppBarLayout所以设置的监听是它了
        initListener();
    }

    private void initRecyclerView() {
        smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
        smartRefreshLayout.setRefreshHeader(new MaterialHeader(MyApplication.getAppContext())); //经典Swip
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMap.setLayoutManager(linearLayoutManager);
        //初始化适配器
        homeContainerAdapter = new HomeContainerAdapter();
        homeContainerAdapter.setEnableLoadMore(false);
        //开启动画
        homeContainerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvMap.setAdapter(homeContainerAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currPage = PAGE_START;
                mPresenter.getArticleList(currPage);
            }
        });

        boolean setRefreshFooter = isSetRefreshFooter();
        if (setRefreshFooter) {
            smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    currPage++;
                    mPresenter.getArticleList(currPage);
                }
            });
        } else {
            homeContainerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    currPage++;
                    mPresenter.getArticleList(currPage);
                }
            }, rvMap);
        }

        //自动刷新(替代第一次请求数据)
        smartRefreshLayout.autoRefresh();

    }

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
    }

    private void initMap(Bundle savedInstanceState) {
        //获取地图控件引用
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //AMap类是地图的控制器类，用来操作地图。
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //初始化权限
        initPermission();
        if (mIsPermissionGanted) {
            initMapLocation();
        }

        //自定义地图 - 设定离线样式文件
        setMapCustomStyleFile(this);

        if (mDarkTheme) {
            //黑夜模式 - 夜景地图模式
            aMap.setMapType(AMap.MAP_TYPE_NIGHT);
        } else {
            //标准模式 - 矢量地图模式
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
    }

    private void initPermission() {
        //SDK版本小于23时候不做检测
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        mCheckPermission = new CheckPermission(this);
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(PERMISSION)) {
            startPermissionActivity();
        } else {
            //定位权限已授权
            mIsPermissionGanted = true;
        }
    }

    private void startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //权限请求码
            if (resultCode == PermissionActivity.PERMISSION_DENIEG) {
                //拒绝时，没有获取到权限，无法运行，关闭页面
                finish();
            } else {
                //定位权限授权成功
                mIsPermissionGanted = true;
                initMapLocation();
            }
        }
    }

    private void initMapLocation() {
        //实例化UiSettings类对象
        uiSettings = aMap.getUiSettings();
        //缩放按钮(默认显示)
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        //指南针(默认不显示)
        uiSettings.setCompassEnabled(false);
        //定位按钮
        //设置默认定位按钮是否显示，非必需设置。
        uiSettings.setMyLocationButtonEnabled(false);
        //设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
        myLocationStyle = new MyLocationStyle();
        //自定义蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        //精度圆圈的自定义：
        myLocationStyle.strokeColor(Color.argb(180, 3, 145, 255));//设置定位蓝点精度圆圈的边框颜色的方法
        myLocationStyle.radiusFillColor(Color.argb(10, 0, 0, 180));//设置定位蓝点精度圆圈的填充颜色的方法
        //精度圈边框宽度自定义方法
        myLocationStyle.strokeWidth(5);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);

        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);

        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        //禁止地图旋转手势
        uiSettings.setRotateGesturesEnabled(false);
        //禁止倾斜手势
        uiSettings.setTiltGesturesEnabled(false);

        //比例尺控件
        uiSettings.setScaleControlsEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        //地图Logo
        uiSettings.setLogoLeftMargin(10000); // 隐藏logo

        //获取当前经纬度信息：
        aMap.setOnMyLocationChangeListener(this);

        //设置点击marker事件监听器
        aMap.setOnMarkerClickListener(this);

        //地图触摸响应事件
        aMap.setOnMapTouchListener(this);
    }


    @Override
    public void onMyLocationChange(Location location) {
        //获取地址描述数据 - 逆地理编码（坐标转地址）
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng currentLatLng = new LatLng(latitude, longitude);

        if (latitude == 0 && longitude == 0) {
            //定位失败
            Log.e("LocationChange", "onMyLocationChange: 定位失败  " + currentLatLng + "   ");
            onShowErrorToast("定位失败");
        } else {
            if (followMove) {
                aMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));
            }
            Log.e("LocationChange", "onMyLocationChange: " + new Gson().toJson(currentLatLng));
        }
        poaitionMap.put("position", currentLatLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        //用户拖动地图后，不再跟随移动，需要跟随移动时再把这个改成true
        if (followMove) {
            followMove = false;
        }
    }

    /**
     * 自定义地图 - 设定离线样式文件
     */
    private void setMapCustomStyleFile(Context context) {
        String styleName = "style.data";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        aMap.setCustomMapStylePath(filePath + "/" + styleName);
        aMap.showBuildings(true);
        aMap.showIndoorMap(true);
        aMap.showMapText(true);
    }

    /**
     * CollapsingToolbarLayout滑动状态监听
     * 因为android.support.design.widget.CollapsingToolbarLayout外层是android.support.design.widget.AppBarLayout所以设置的监听是它了
     */
    private AppBarStateChangeListener.State currentState;

    private void initListener() {
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                int mapType = aMap.getMapType();
                if (state == State.EXPANDED) {
                    currentState = state;
//                    //展开状态
//                    toolbarRightLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showMenuWindow(0);
//                        }
//                    });
//                    baseToolbarRightIcon.setVisibility(View.GONE);
                    baseToolbarLeftIcon.setImageResource(R.drawable.abc_ic_ab_back_material);
//                    rlRecyclerContainer.setBackground(getResources().getDrawable(R.drawable.shape_white_card_bg));
                    rlRecyclerContainer.setBackgroundColor(getResources().getColor(R.color.surface));

                    if (mDarkTheme) {
                        //黑夜模式
                        if (mapType == AMap.MAP_TYPE_NORMAL || mapType == AMap.MAP_TYPE_NAVI) {
                            //标准模式地图or导航模式
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(true);
                        } else if (mapType == AMap.MAP_TYPE_NIGHT || mapType == AMap.MAP_TYPE_SATELLITE) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                        if (isSetCustomMapMode) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                    } else {
                        //白天模式
                        if (mapType == AMap.MAP_TYPE_NORMAL || mapType == AMap.MAP_TYPE_NAVI) {
                            //标准模式地图or导航模式
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(true);
                        } else if (mapType == AMap.MAP_TYPE_NIGHT || mapType == AMap.MAP_TYPE_SATELLITE) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                        if (isSetCustomMapMode) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                    }

                    //不显示ToolBar
                    toolbarLayout.setTitleEnabled(false);

                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    currentState = state;
//                    toolbarRightLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showMenuWindow(1);
//                        }
//                    });
//                    baseToolbarRightIcon.setVisibility(View.VISIBLE);
                    baseToolbarLeftIcon.setImageResource(R.drawable.ic_back);
                    baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    rlRecyclerContainer.setBackground(getResources().getDrawable(R.drawable.shape_white_rectangle_bg));

                    //显示ToolBar
                    toolbarLayout.setTitleEnabled(true);
                    //google官方在安卓6.0以上版本才推出的深色状态栏字体api-白色
                    changeStatusBarTextColor(false);
                } else {
                    //中间滑动任意位置状态
                    currentState = state;
//                    toolbarRightLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showMenuWindow(0);
//                        }
//                    });
//                    baseToolbarRightIcon.setVisibility(View.GONE);
                    baseToolbarLeftIcon.setImageResource(R.drawable.abc_ic_ab_back_material);
//                    rlRecyclerContainer.setBackground(getResources().getDrawable(R.drawable.shape_white_card_bg));
                    rlRecyclerContainer.setBackgroundColor(getResources().getColor(R.color.surface));

                    if (mDarkTheme) {
                        //黑夜模式
                        if (mapType == AMap.MAP_TYPE_NORMAL || mapType == AMap.MAP_TYPE_NAVI) {
                            //标准模式地图or导航模式
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(true);
                        } else if (mapType == AMap.MAP_TYPE_NIGHT || mapType == AMap.MAP_TYPE_SATELLITE) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                        if (isSetCustomMapMode) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                    } else {
                        //白天模式
                        if (mapType == AMap.MAP_TYPE_NORMAL || mapType == AMap.MAP_TYPE_NAVI) {
                            //标准模式地图or导航模式
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(true);
                        } else if (mapType == AMap.MAP_TYPE_NIGHT || mapType == AMap.MAP_TYPE_SATELLITE) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                        if (isSetCustomMapMode) {
                            baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                            changeStatusBarTextColor(false);
                        }
                    }

                    //不显示ToolBar
                    toolbarLayout.setTitleEnabled(false);
                }
            }
        });

    }

    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, R.id.iv_btn_my_location, R.id.iv_control_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                finish();
                break;

            case R.id.toolbar_right_layout:
                showMenuWindow();
                break;

            case R.id.iv_btn_my_location:
                LatLng latLng = poaitionMap.get("position");
                Log.e("LocationChange", "onMyLocationChange再定位: " + new Gson().toJson(latLng));
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
                break;

            case R.id.iv_control_btn:
                showMenuWindow();
                break;

            default:
                break;
        }
    }

    //    private void showMenuWindow(int flag) {
    private void showMenuWindow() {
        if (customPopupWindow != null && customPopupWindow.isShowing()) {
            return;
        }

        View menuView = LayoutInflater.from(this).inflate(R.layout.dialog_map_menu, null);
        //测量View的高和宽
        int menuWidth = ResolutionUtils.dip2px(this, 120);
        CustomPopupWindow.measureWidthAndHeight(menuView);
        customPopupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.dialog_map_menu)
//                .setWidthAndHeight(menuWidth, menuView.getMeasuredHeight())
                .setWidthAndHeight(menuWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(1.0f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.MenuDialog)
                .setViewOnclickListener(this, 0)
                .create();
        int leftPx = ResolutionUtils.dip2px(this, -90);
        customPopupWindow.showAsDropDown(baseToolbarRightIcon, leftPx, 40);
//        customPopupWindow.showAtLocation(menuView, Gravity.TOP,0,0);

    }

    @Override
    public void getChildView(View view, int layoutResId, int flag) {
        TextView tvMenuShare = view.findViewById(R.id.dialog_map_menu_share);
        TextView tvMenuCustomMap = view.findViewById(R.id.dialog_map_menu_custom);
        TextView tvMenuStandardMap = view.findViewById(R.id.dialog_map_menu_standard);
        TextView tvMenuSatelliteMap = view.findViewById(R.id.dialog_map_menu_satellite);
        TextView tvMenuNightMap = view.findViewById(R.id.dialog_map_menu_night);
        TextView tvMenuNavigationMap = view.findViewById(R.id.dialog_map_menu_navigation);

//        if (flag == 1) {
        if (currentState == AppBarStateChangeListener.State.COLLAPSED) {
            //折叠状态时隐藏PopupMenuDialog地图模式切换选项
            tvMenuCustomMap.setVisibility(View.GONE);
            tvMenuStandardMap.setVisibility(View.GONE);
            tvMenuSatelliteMap.setVisibility(View.GONE);
            tvMenuNightMap.setVisibility(View.GONE);
            tvMenuNavigationMap.setVisibility(View.GONE);
        } else {
            tvMenuCustomMap.setVisibility(View.VISIBLE);
            tvMenuStandardMap.setVisibility(View.VISIBLE);
            tvMenuSatelliteMap.setVisibility(View.VISIBLE);
            tvMenuNightMap.setVisibility(View.VISIBLE);
            tvMenuNavigationMap.setVisibility(View.VISIBLE);
        }

        tvMenuShare.setOnClickListener(this);
        tvMenuCustomMap.setOnClickListener(this);
        tvMenuStandardMap.setOnClickListener(this);
        tvMenuSatelliteMap.setOnClickListener(this);
        tvMenuNightMap.setOnClickListener(this);
        tvMenuNavigationMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_map_menu_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到..."));
                break;

            case R.id.dialog_map_menu_custom:
                //自定义地图
                onShowToast("自定义地图");
                //设置自定义地图后，自定义地图默认为关闭状态，可通过如下方法开启：
                aMap.setMapCustomEnable(true);
                isSetCustomMapMode = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
                //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                changeStatusBarTextColor(false);
                LogUtil.d(TAG, "地图MapType:    " + aMap.getMapType());
                break;

            case R.id.dialog_map_menu_standard:
                //标准地图
                onShowToast("标准地图");
                if (aMap != null) {
                    // 矢量地图模式
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    isSetCustomMapMode = false;
                    LogUtil.d(TAG, "地图MapType:    " + aMap.getMapType());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                        baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                        //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                        changeStatusBarTextColor(true);
                    }
                }
                break;

            case R.id.dialog_map_menu_satellite:
                //卫星地图
                onShowToast("卫星地图");
                if (aMap != null) {
                    // 卫星地图模式
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                    isSetCustomMapMode = false;
                    LogUtil.d(TAG, "地图MapType:    " + aMap.getMapType());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                        changeStatusBarTextColor(false);
                    }
                }
                break;

            case R.id.dialog_map_menu_night:
                //夜间模式(使用这种方式实现地图层的暗黑夜间模式or自定义MapView的前套餐层实现dispatchDraw()方法canvas.drawColor实现)
                onShowToast("夜间模式");
                if (aMap != null) {
                    //夜景地图模式
                    aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                    isSetCustomMapMode = false;
                    LogUtil.d(TAG, "地图MapType:    " + aMap.getMapType());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                        changeStatusBarTextColor(false);
                    }
                }
                break;

            case R.id.dialog_map_menu_navigation:
                //导航模式
                onShowToast("导航模式");
                if (aMap != null) {
                    //导航地图模式
                    aMap.setMapType(AMap.MAP_TYPE_NAVI);
                    isSetCustomMapMode = false;
                    LogUtil.d(TAG, "地图MapType:    " + aMap.getMapType());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        baseToolbarLeftIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                        baseToolbarRightIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                        //google官方在安卓6.0以上版本才推出的深色状态栏字体api
                        changeStatusBarTextColor(true);
                    }
                }
                break;

            default:
                break;
        }
        //关闭DialogMenu
        dismissMenuDialog();
    }

    public void dismissMenuDialog() {
        if (customPopupWindow != null) {
            customPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
        LogUtil.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
        LogUtil.d(TAG, "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
    }


    @Override
    public void onGetArticleListSuccess(HomeBean data) {
        if (currPage == PAGE_START) {
            //第一页数据
            homeContainerAdapter.setNewData(data.getDatas());
//            homeContainerAdapter.addData(data.getDatas());
            LogUtil.d(TAG, "下拉刷新：  " + "数量: " + homeContainerAdapter.getData().size());
        } else {
            //请求更多数据,直接添加list中
            homeContainerAdapter.addData(data.getDatas());
            homeContainerAdapter.loadMoreComplete();
            LogUtil.d(TAG, "上拉加载更多：  " + "数量: " + homeContainerAdapter.getData().size());
        }

        if (data.isOver() || data.getDatas().size() == 0) {
//        if (data.getDatas().size() == 0) {
            homeContainerAdapter.loadMoreEnd();
            smartRefreshLayout.setEnableLoadMore(false);
            onShowToast("没有更多数据了!");
        } else {
            if (!homeContainerAdapter.isLoadMoreEnable()) {
                homeContainerAdapter.setEnableLoadMore(true);
            }
            smartRefreshLayout.setEnableLoadMore(true);
        }
        //这两个方法是在加载成功,并且还有数据的情况下调用的
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onGetArticleListFail(String content) {
        onShowToast(content);
        homeContainerAdapter.loadMoreFail();
        //这两个方法是在加载失败时调用的
        smartRefreshLayout.finishRefresh(false);
        smartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onShowProgress() {

    }

    @Override
    public void onHideProgress() {

    }
}
