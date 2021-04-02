package liang.com.baseproject.map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.base.PermissionActivity;
import liang.com.baseproject.home.adapter.HomeContainerAdapter;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.listener.AppBarStateChangeListener;
import liang.com.baseproject.login.entity.UserBean;
import com.liang.module_core.utils.CheckPermission;
import com.liang.module_core.utils.DrawCustomMarkerBitmapUtil;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.utils.NetUtil;
import com.liang.module_core.utils.ResolutionUtils;
import com.liang.module_core.utils.SPUtils;
import com.liang.module_core.utils.SettingUtils;
import com.liang.module_core.widget.CustomScrollRelativeLayout;
import com.liang.module_core.widget.popupwindow.CustomPopupWindow;

public class MapLocationActivity extends MVPBaseActivity<MapLocationView, MapLocationPresenter> implements AMap.OnMyLocationChangeListener,
        AMap.OnMarkerClickListener, AMap.OnMapTouchListener, CustomPopupWindow.ViewInterface, View.OnClickListener, MapLocationView {

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
    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_map)
    RecyclerView rvMap;
    @BindView(R.id.rl_recycler_container)
    RelativeLayout rlRecyclerContainer;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.icon_expand)
    ImageView iconExpand;
    @BindView(R.id.cv_map_head)
    CircleImageView cvMapHead;
    @BindView(R.id.tv_publisher_name)
    TextView tvPublisherName;
    @BindView(R.id.tv_activity_address)
    TextView tvActivityAddress;
    @BindView(R.id.tv_join_number)
    TextView tvJoinNumber;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_over_time)
    TextView tvOverTime;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.tv_activity_describe)
    TextView tvActivityDescribe;
    @BindView(R.id.v_down)
    View vDown;
    @BindView(R.id.rl_view_down)
    RelativeLayout rlViewDown;
    @BindView(R.id.rl_im_activity)
    RelativeLayout rlImActivity;

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

    private boolean hasQueryOfflineCity = false;

    private AlertDialog offlineCityLibDialog;
    private String selectOfflineCity;
    private LatLng initLatLng = null;

    private boolean followMove = true;

    private boolean isSetCustomMapMode = false;
    //定位成功时，存储当前定位坐标，作为判断下次定位成功时与其比较，相等的话，就不再重新执行添加Marker，
    // 不相等即定位坐标发生变化，则清除之前Marker并重新Add
    private Map<String, LatLng> currentPositionMap = new HashMap<>();
    private AMap aMap;
    private boolean isAddDefaultMarker = true;

    private MyLocationStyle myLocationStyle;
    private HomeContainerAdapter homeContainerAdapter;

    private static final int PAGE_START = 0;
    private int currPage = PAGE_START;
    private boolean mDarkTheme;
    private boolean networkAvailable;
    private List<OfflineMapCity> downloadOfflineMapCityList;
    private Marker currentLocationMarker;
    private Marker latelyMarker;

    private List<Marker> localMarkers;

    //最近一次点击的Marker
    private Marker lastClickMarker;

    private int[] img = {R.drawable.core_icon_expand_open, R.drawable.core_icon_expand_close};//定义一个int数组，用来放图片
    private boolean isExpandSmartContainer = true;//定义一个标识符，用来判断是open,还是close

    private DisplayMetrics metrics;
    private CoordinatorLayout.LayoutParams layoutParams;
    private int widthPixels;
    private int heightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //随系统设置更改ToolBar状态栏颜色
        getActionBarTheme(null, collapsingLayout);

        //获取屏幕高度
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        //定义布局参数
        layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//        if (data.isEmpty()) {
//            smartRefreshLayout.setVisibility(View.GONE);
//            layoutParams.height = heightPixels;
//            appBar.setLayoutParams(layoutParams);
//        } else {
//            smartRefreshLayout.setVisibility(View.VISIBLE);
//            layoutParams.height = (int) (heightPixels * 0.8);
//            appBar.setLayoutParams(layoutParams);
//        }

        //首先判断isExpandSmartContainer是真还是假，如果是真，就显示close，如果是假，就显示open
        if (isExpandSmartContainer) {
            //当SmartContainer=true是展开状态时,显示向下图标，并且SmartContainer设置显示展开
            iconExpand.setImageResource(R.drawable.core_icon_expand_open);//向下
//            smartRefreshLayout.setVisibility(View.VISIBLE);
            layoutParams.height = (int) (heightPixels * 0.8);
            appBar.setLayoutParams(layoutParams);
        } else {
            //当SmartContainer=true是展开关闭时,显示向上图标，并且SmartContainer设置伸缩隐藏
            iconExpand.setImageResource(R.drawable.core_icon_expand_close);//向上
            smartRefreshLayout.setVisibility(View.GONE);
            layoutParams.height = heightPixels;
            appBar.setLayoutParams(layoutParams);
        }

        //绑定View
        mPresenter.attachView(this);

        //得到Intent传递的数据
        parseIntent();
        //查询离线地图资源
        networkAvailable = NetUtil.isNetworkAvailable(this);
        mAmapMap_path = this.getExternalFilesDir("").getAbsolutePath();
        //初始化地图视图
        initMap(savedInstanceState);
        //初始化视图
        initView();
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
    protected MapLocationPresenter createPresenter() {
        return new MapLocationPresenter();
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

    private String mAmapMap_path;
    private String mPath_offline = "/AmapOfflineMapData";

    /**
     * 查询离线地图资源
     */
    private void initOfflineMap() {
        OfflineMapManager offlineMapManager = new OfflineMapManager(MapLocationActivity.this, new OfflineMapManager.OfflineMapDownloadListener() {
            @Override
            public void onDownload(int i, int i1, String s) {

            }

            @Override
            public void onCheckUpdate(boolean b, String s) {

            }

            @Override
            public void onRemove(boolean b, String s, String s1) {

            }
        });

        showProgressDialog("查询离线地图资源...", false);
        mapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                downloadOfflineMapCityList = offlineMapManager.getDownloadOfflineMapCityList();
                if (null == downloadOfflineMapCityList || 0 == downloadOfflineMapCityList.size()) {
                    LogUtil.d("offlineMap", "没有下载离线地图");
                    hideProgressDialog();
                } else {
                    LogUtil.d("offlineMap", "离线地图城市: " + downloadOfflineMapCityList.size() + "个");
                    for (OfflineMapCity offlineMapCity : downloadOfflineMapCityList) {
                        LogUtil.d("offlineMap", "离线地图城市: " + offlineMapCity.getCity());
                    }
                    //切换离线地图列表
                    onShowToast("已下载离线地图资源");
                    hideProgressDialog();
                    showOfflineMapCityList(downloadOfflineMapCityList);
                    hasQueryOfflineCity = true;
                }
            }
        }, 10 * 1000);
    }

    private void showOfflineMapCityList(List<OfflineMapCity> downloadOfflineMapCityList) {
        View offlineCityListView = LayoutInflater.from(this).inflate(R.layout.dialog_recyclerview_layout, null);
        RecyclerView dialogCityRecyclerView = offlineCityListView.findViewById(R.id.rv_face_lib);
        //初始化适配器
        OfflineCityListAdapter offlineCityListAdapter = new OfflineCityListAdapter(downloadOfflineMapCityList);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialogCityRecyclerView.setLayoutManager(linearLayoutManager);
        //添加Android自带的分割线
        dialogCityRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //绑定适配器
        dialogCityRecyclerView.setAdapter(offlineCityListAdapter);

        //设置点击事件
        offlineCityListAdapter.setmOnItemClickListener(new OfflineCityListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, OfflineMapCity dataBean) {
                if (offlineCityLibDialog.isShowing()) {
                    offlineCityLibDialog.dismiss();
                }
                selectOfflineCity = dataBean.getCity();

                if (selectOfflineCity.contains("亳州")) {
                    initLatLng = Constant.BOZHOU;
                    aMap.clear();
                } else if (selectOfflineCity.contains("阜阳")) {
                    initLatLng = Constant.FUYANG;
                    aMap.clear();
                } else if (selectOfflineCity.contains("杭州")) {
                    initLatLng = Constant.HANZGHOU;
                    aMap.clear();
                } else if (selectOfflineCity.contains("宁波")) {
                    initLatLng = Constant.NINGBO;
                    aMap.clear();
                } else if (selectOfflineCity.contains("全国概要图")) {
                    aMap.clear();
                    //调用函数animateCamera或moveCamera来改变可视区域
                    changeCamera(
                            //CameraPosition(LatLng target, float zoom, float tilt, float bearing)
                            //zoom:目标可视区域的缩放级别   tilt:目标可视区域的倾斜度，以角度为单位  bearing:可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    initLatLng, 1, 0, 0)));
                    return;
                }
                //调用函数animateCamera或moveCamera来改变可视区域
                changeCamera(
                        //CameraPosition(LatLng target, float zoom, float tilt, float bearing)
                        //zoom:目标可视区域的缩放级别   tilt:目标可视区域的倾斜度，以角度为单位  bearing:可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                initLatLng, 16, 0, 0)));

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        offlineCityLibDialog = builder.create();
        offlineCityLibDialog.show();
        offlineCityLibDialog.setCanceledOnTouchOutside(false);
        //AlertDialog设置高度自适应
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = offlineCityLibDialog.getWindow().getAttributes();
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        offlineCityLibDialog.getWindow().setAttributes(lp);
        offlineCityLibDialog.getWindow().setContentView(offlineCityListView);

        ImageView ivCloseDialog = offlineCityListView.findViewById(R.id.iv_btn_close_dialog);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offlineCityLibDialog.isShowing()) {
                    offlineCityLibDialog.dismiss();
                }
            }
        });
    }

    private void initView() {
        //自定义的RelativeLayout-CollapsingToolbarLayout-滑动冲突
        customScrollContainer.setCollapsingToolbarLayout(collapsingLayout);
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        collapsingLayout.setTitle("Location");
        collapsingLayout.setCollapsedTitleGravity(Gravity.CENTER);
        collapsingLayout.setExpandedTitleGravity(Gravity.CENTER);
        //设置CollapsingToolbarLayout扩展状态标题栏颜色
        collapsingLayout.setExpandedTitleColor(Color.YELLOW);
        //设置CollapsingToolbarLayout收缩状态标题栏颜色
        collapsingLayout.setCollapsedTitleTextColor(Color.WHITE);

        initRecyclerView();

        //设置监听android.support.design.widget.AppBarLayout这个控件设置监听了
        //因为android.support.design.widget.CollapsingToolbarLayout外层是 android.support.design.widget.AppBarLayout所以设置的监听是它了
        initListener();
    }

    private void initRecyclerView() {
//        smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
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

        setRefreshHeader(smartRefreshLayout,com.liang.module_core.constant.Constant.DropBoxHeader);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMap.setLayoutManager(linearLayoutManager);
        //初始化适配器
        homeContainerAdapter = new HomeContainerAdapter();
//        homeContainerAdapter.setEnableLoadMore(false);
        //开启动画
//        homeContainerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeContainerAdapter.setAnimationEnable(true);
        homeContainerAdapter.setAdapterAnimation(new SlideInLeftAnimation());
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
//            homeContainerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//                @Override
//                public void onLoadMoreRequested() {
//                    currPage++;
//                    mPresenter.getArticleList(currPage);
//                }
//            }, rvMap);
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
            getLocalData();
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

    private void getLocalData() {
        mPresenter.getLocalMarkerData();
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
                getLocalData();
            }
        }
    }

    private void initMapLocation() {
        //使用压缩文件导入手机中的方式-只设置默认一个离线城市地图资源时-使用以下代码加载离线地图资源
        File file1 = new File(mAmapMap_path + mPath_offline);
        if (!networkAvailable && file1.exists()) {
            //调用函数animateCamera或moveCamera来改变可视区域
//            initLatLng = Constant.HANZGHOU;
            initLatLng = Constant.ZIBO;
//            changeCamera(
//                    //CameraPosition(LatLng target, float zoom, float tilt, float bearing)
//                    //zoom:目标可视区域的缩放级别   tilt:目标可视区域的倾斜度，以角度为单位  bearing:可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度
//                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                            initLatLng, 16, 0, 0)));

            //获取本地存储的定位信息
            double latelyLatitude = Double.parseDouble(SPUtils.getMapLocation_lat("latitude"));
            double latelyLongitude = Double.parseDouble(SPUtils.getMapLocation_lng("longitude"));
            if (latelyLatitude != 0 && latelyLongitude != 0) {
                LatLng latelyLatLng = new LatLng(latelyLatitude, latelyLongitude);
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latelyLatLng, 16));
            } else {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, 16));
            }
        }
        //使用压缩文件导入手机中的方式-只设置默认一个离线城市地图资源时-使用以上代码加载离线地图资源

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

        //获取本地存储的定位信息
        double latelyLatitude = Double.parseDouble(SPUtils.getMapLocation_lat("latitude"));
        double latelyLongitude = Double.parseDouble(SPUtils.getMapLocation_lng("longitude"));
        LatLng latelyLatLng = new LatLng(latelyLatitude, latelyLongitude);

        //获取地址描述数据 - 逆地理编码（坐标转地址）
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng currentLatLng = new LatLng(latitude, longitude);

        Log.d("onMyLocationChange", "定位信息: " + currentLatLng);

        LatLng currentPositionMapLng = currentPositionMap.get("currentPosition");

        if (latitude == 0 && longitude == 0) {
            //定位失败
            if (latelyLatitude != 0 && latelyLongitude != 0) {
                //最近一次有定位成功，将最近一次定位点置为marker
                if (currentLocationMarker != null) {
                    currentLocationMarker.destroy();
                }

                if (latelyMarker != null) {
                    latelyMarker.destroy();
                }

                latelyMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).position(latelyLatLng));
                //这个场景暂时无法做出和上次比较判断是否坐标发生变化，所以不添加Marker动画
                //                startGrowAnimation(latelyMarker);
                Log.d("onMyLocationChange", "定位失败-上次定位-Add Marker  " + "latelyLatLng:  " + latelyLatLng + " currentLatLng= " + currentLatLng);

            } else {
                //最近一次没有定位成功，设置固定位置marker
                if (currentLocationMarker != null) {
                    currentLocationMarker.destroy();
                }
                if (isAddDefaultMarker) {
                    if (latelyMarker != null) {
                        latelyMarker.destroy();
                    }
                    //第一次进入
                    latelyMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).position(initLatLng));
                    startGrowAnimation(latelyMarker);
                    Log.d("onMyLocationChange", "定位失败-默认坐标-Add Marker  " + "initLatLng:  " + initLatLng + " currentLatLng= " + currentLatLng);
                    isAddDefaultMarker = false;
                } else {
                    //只要是定位失败，且最近一次没有定位成功，则固定Marker，不再每次都add marker
                    Log.d("onMyLocationChange", "定位失败-默认坐标-No Add Marker  " + "initLatLng:  " + initLatLng + " currentLatLng= " + currentLatLng);
                }
            }
            onShowErrorToast("定位失败");
            //此处将followMove=true，意图为当定位成功时可以自动将地图定位到定位中心
            followMove = true;
        } else {
            if (followMove) {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));
            }
            //定位成功
            if (!currentLatLng.equals(currentPositionMapLng)) {
                //存储定位信息
                SPUtils.saveMapLocation(currentLatLng.latitude, currentLatLng.longitude);

                //最近一次定位点与当前成功获取定位信息点经纬度不同时，更新Marker
                if (latelyMarker != null) {
                    latelyMarker.destroy();
                }

                if (currentLocationMarker != null) {
                    currentLocationMarker.destroy();
                }

                currentLocationMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car)).position(currentLatLng));
                startGrowAnimation(currentLocationMarker);
                Log.d("onMyLocationChange", "定位成功-Add Marker    currentLatLng:  " + currentLatLng + "  " + "latelyLatLng： " + latelyLatLng);
            } else {
                //最近一次定位点与当前成功获取定位信息点经纬度相同时，不更新Marker
                Log.d("onMyLocationChange", "定位成功-No Add Marker    currentLatLng:  " + currentLatLng + "  " + "latelyLatLng： " + latelyLatLng);
            }
            followMove = false;
        }
        currentPositionMap.put("currentPosition", currentLatLng);
    }

    /**
     * 点击marker事件监听器
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        lastClickMarker = marker;
        //判断当前定位点是否显示信息窗口如果显示则关闭，如果没有显示则显示信息窗口
        if (lastClickMarker.isInfoWindowShown()) {
            lastClickMarker.hideInfoWindow();
        } else {
            lastClickMarker.showInfoWindow();
        }
        upTheDetail();
        //返回false，点击Marker后当前点位显示在地图中心
        return false;
    }

    /**
     * 地图触摸响应事件
     */
    @Override
    public void onTouch(MotionEvent motionEvent) {
        //用户拖动地图后，不再跟随移动，需要跟随移动时再把这个改成true
        if (followMove) {
            followMove = false;
        }

        //触摸地图，关闭Marker的InfoWindow
        if (aMap != null && lastClickMarker.isInfoWindowShown()) {
            lastClickMarker.hideInfoWindow();
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && rlImActivity.getVisibility() == View.VISIBLE) {
            downTheDetail();
        }
    }

    /**
     * 调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
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

                    rlControlContainer.setVisibility(View.VISIBLE);
                    iconExpand.setVisibility(View.VISIBLE);

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
                    collapsingLayout.setTitleEnabled(false);

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

                    rlControlContainer.setVisibility(View.GONE);
                    iconExpand.setVisibility(View.GONE);

                    //显示ToolBar
                    collapsingLayout.setTitleEnabled(true);
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

                    rlControlContainer.setVisibility(View.VISIBLE);
                    iconExpand.setVisibility(View.GONE);

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
                    collapsingLayout.setTitleEnabled(false);
                }
            }
        });

    }

    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, R.id.iv_btn_my_location, R.id.iv_control_btn, R.id.icon_expand, R.id.rl_view_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                finish();
                break;

            case R.id.toolbar_right_layout:

            case R.id.iv_control_btn:
                showMenuWindow();
                break;

            case R.id.iv_btn_my_location:
//                LatLng latLng = poaitionMap.get("position");
//                Log.e("LocationChange", "onMyLocationChange再定位: " + new Gson().toJson(latLng));
////                aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
//                if (latLng != null && latLng.latitude != 0 && latLng.longitude != 0) {
//                    aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
//                } else {
//                    //调用函数animateCamera或moveCamera来改变可视区域
////                    LatLng initLatLng = new LatLng(33.869338, 115.782939);
//                    changeCamera(
//                            //CameraPosition(LatLng target, float zoom, float tilt, float bearing)
//                            //zoom:目标可视区域的缩放级别   tilt:目标可视区域的倾斜度，以角度为单位  bearing:可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度
//                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                                    initLatLng, 16, 0, 0)));
//                    onShowToast("定位失败，重置中心");
//                }

                //获取本地存储的定位信息
                double latelyLatitude = Double.parseDouble(SPUtils.getMapLocation_lat("latitude"));
                double latelyLongitude = Double.parseDouble(SPUtils.getMapLocation_lng("longitude"));
                if (latelyLatitude != 0 && latelyLongitude != 0) {
                    LatLng latelyLatLng = new LatLng(latelyLatitude, latelyLongitude);
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latelyLatLng, 16));
                } else {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, 16));
                }
                break;

            case R.id.icon_expand:
                //首先判断isExpandSmartContainer是真还是假，如果是真，就显示close，如果是假，就显示open
                if (isExpandSmartContainer) {
                    //SmartContainer是展开状态时，点击后缩回SmartContainer，并切换向上图标
                    iconExpand.setImageResource(R.drawable.core_icon_expand_close);//向上
                    smartRefreshLayout.setVisibility(View.GONE);
                    layoutParams.height = heightPixels;
                    appBar.setLayoutParams(layoutParams);
                    isExpandSmartContainer = false;
                    onShowToast("关闭列表布局");
                } else {
                    //SmartContainer是关闭状态时，点击后展开SmartContainer，并切换向下图标
                    iconExpand.setImageResource(R.drawable.core_icon_expand_open);//向下
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    layoutParams.height = (int) (heightPixels * 0.8);
                    appBar.setLayoutParams(layoutParams);
                    isExpandSmartContainer = true;
                    onShowToast("显示列表布局");
                }
                break;

            case R.id.rl_view_down:
                downTheDetail();
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
        TextView tvMenuOfflineMap = view.findViewById(R.id.dialog_map_offline_custom);
        TextView tvMenuStandardMap = view.findViewById(R.id.dialog_map_menu_standard);
        TextView tvMenuSatelliteMap = view.findViewById(R.id.dialog_map_menu_satellite);
        TextView tvMenuNightMap = view.findViewById(R.id.dialog_map_menu_night);
        TextView tvMenuNavigationMap = view.findViewById(R.id.dialog_map_menu_navigation);
        TextView tvMenuCustomOverlay = view.findViewById(R.id.dialog_map_menu_custom_overlay);

//        if (flag == 1) {
        if (currentState == AppBarStateChangeListener.State.COLLAPSED) {
            //折叠状态时隐藏PopupMenuDialog地图模式切换选项
            tvMenuCustomMap.setVisibility(View.GONE);
            tvMenuOfflineMap.setVisibility(View.GONE);
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

            boolean networkAvailable = NetUtil.isNetworkAvailable(this);
            Log.d(TAG, "getChildView: " + networkAvailable);
            if (networkAvailable) {
                tvMenuOfflineMap.setVisibility(View.GONE);
            } else {
                tvMenuOfflineMap.setVisibility(View.VISIBLE);
            }
        }

        tvMenuShare.setOnClickListener(this);
        tvMenuCustomMap.setOnClickListener(this);
        tvMenuOfflineMap.setOnClickListener(this);
        tvMenuStandardMap.setOnClickListener(this);
        tvMenuSatelliteMap.setOnClickListener(this);
        tvMenuNightMap.setOnClickListener(this);
        tvMenuNavigationMap.setOnClickListener(this);
        tvMenuCustomOverlay.setOnClickListener(this);
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

            case R.id.dialog_map_offline_custom:
                onShowToast("查询离线地图");
                isSetCustomMapMode = false;
                if (!hasQueryOfflineCity) {
                    //离线下载-获取数据
                    initOfflineMap();
                } else {
                    showOfflineMapCityList(downloadOfflineMapCityList);
                }
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

            case R.id.dialog_map_menu_custom_overlay:
                OverlayTestActivity.actionStart(this);
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
    public void onGetArticleListSuccess(HomeBean data) {
        if (currPage == PAGE_START) {
            //第一页数据
            homeContainerAdapter.setNewData(data.getDatas());
//            homeContainerAdapter.addData(data.getDatas());
            LogUtil.d(TAG, "下拉刷新：  " + "数量: " + homeContainerAdapter.getData().size());
        } else {
            //请求更多数据,直接添加list中
            homeContainerAdapter.addData(data.getDatas());
//            homeContainerAdapter.loadMoreComplete();
            LogUtil.d(TAG, "上拉加载更多：  " + "数量: " + homeContainerAdapter.getData().size());
        }

        if (data.isOver() || data.getDatas().size() == 0) {
//        if (data.getDatas().size() == 0) {
//            homeContainerAdapter.loadMoreEnd();
            smartRefreshLayout.setEnableLoadMore(false);
            onShowToast("没有更多数据了!");
        } else {
//            if (!homeContainerAdapter.isLoadMoreEnable()) {
//                homeContainerAdapter.setEnableLoadMore(true);
//            }
            smartRefreshLayout.setEnableLoadMore(true);
        }
        //这两个方法是在加载成功,并且还有数据的情况下调用的
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onGetArticleListFail(String content) {
        onShowToast(content);
//        homeContainerAdapter.loadMoreFail();
        //这两个方法是在加载失败时调用的
        smartRefreshLayout.finishRefresh(false);
        smartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onGetLocalMarkerDataSuccess(List<UserBean> localMarkerDataList) {
        LogUtil.d(TAG, "本地数据Marker:  " + new Gson().toJson(localMarkerDataList));

        if (localMarkers != null) {
            for (Marker marker : localMarkers) {
                marker.remove();
            }
        }
        localMarkers = new ArrayList<>();

        for (int i = 0; i < localMarkerDataList.size(); i++) {
            if (!TextUtils.isEmpty(localMarkerDataList.get(i).getIcon())) {
                DrawCustomMarkerBitmapUtil.drawMark(localMarkerDataList.get(i), localMarkerDataList.get(i).getIcon(),
                        144, true, new DrawCustomMarkerBitmapUtil.OnGetMapHeadListener() {
                            @Override
                            public void success(Bitmap bitmap, Object object) {
                                UserBean userBean = (UserBean) object;
                                localMarkers.add(aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitmap))));
                                localMarkers.get(localMarkers.size() - 1).setPosition(userBean.getLatLng());
                                startGrowAnimation(localMarkers.get(localMarkers.size() - 1));
                                //可以给定位点绑定一个信息对象
                                localMarkers.get(localMarkers.size() - 1).setObject(userBean);
                                localMarkers.get(localMarkers.size() - 1).setInfoWindowEnable(true);
                                localMarkers.get(localMarkers.size() - 1).setTitle(userBean.getUsername());
                                localMarkers.get(localMarkers.size() - 1).setSnippet(userBean.getDescribe());
                                localMarkers.get(localMarkers.size() - 1).hideInfoWindow();
                            }

                            @Override
                            public void fail(Object object) {

                            }
                        });
            } else {
                localMarkers.add(aMap.addMarker(new MarkerOptions().icon(
                        BitmapDescriptorFactory.fromBitmap(DrawCustomMarkerBitmapUtil.drawMark(BitmapFactory.decodeResource(
                                MapLocationActivity.this.getResources(), R.mipmap.ic_launcher), 144, true)))));
                localMarkers.get(localMarkers.size() - 1).setPosition(localMarkerDataList.get(localMarkers.size() - 1).getLatLng());
                startGrowAnimation(localMarkers.get(localMarkers.size() - 1));
                //可以给定位点绑定一个信息对象
                localMarkers.get(localMarkers.size() - 1).setObject(localMarkerDataList.get(i));
                localMarkers.get(localMarkers.size() - 1).setInfoWindowEnable(true);
                localMarkers.get(localMarkers.size() - 1).setTitle(localMarkerDataList.get(i).getUsername());
                localMarkers.get(localMarkers.size() - 1).setSnippet(localMarkerDataList.get(i).getDescribe());
                localMarkers.get(localMarkers.size() - 1).hideInfoWindow();
            }
        }
    }

    @Override
    public void onGetLocalMarkerDataFail(String content) {

    }

    @Override
    public void onShowProgress() {

    }

    @Override
    public void onHideProgress() {

    }

    @Override
    public void onRequestError(String content) {

    }

    /**
     * 地上生长的Marker
     */
    private void startGrowAnimation(Marker growMarker) {
        if (growMarker != null) {
            Animation animation = new ScaleAnimation(0, 1, 0, 1);
            animation.setInterpolator(new LinearInterpolator());
            //整个移动所需要的时间
            animation.setDuration(1000);
            //设置动画
            growMarker.setAnimation(animation);
            //开始动画
            growMarker.startAnimation();
        }
    }

    public void downTheDetail() {
        if (rlImActivity.getVisibility() == View.VISIBLE) {
            rlImActivity.setVisibility(View.GONE);
            android.view.animation.Animation operatingAnim = AnimationUtils
                    .loadAnimation(this, R.anim.fade_down);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            rlImActivity.startAnimation(operatingAnim);
        }
    }

    public void upTheDetail() {
        android.view.animation.Animation operatingAnim = AnimationUtils
                .loadAnimation(this, R.anim.fade_up);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        rlImActivity.startAnimation(operatingAnim);
        rlImActivity.setVisibility(View.VISIBLE);
    }
}
