package liang.com.baseproject.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.R;
import liang.com.baseproject.View.JuheNewsDetailWebView;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.PermissionActivity;
import liang.com.baseproject.home.adapter.HomeContainerAdapter;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.listener.AppBarStateChangeListener;
import liang.com.baseproject.presenter.JuheNewsDetailPresenter;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.utils.CheckPermission;
import liang.com.baseproject.utils.ImageLoaderUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.widget.CustomScrollRelativeLayout;

/**
 * 创建日期：2019/2/20 on 13:31
 * 描述: 聚合新闻详情页- WebView加载Url
 * 作者: liangyang
 */
public class WebViewDetailActivity extends MVPBaseActivity<JuheNewsDetailWebView, JuheNewsDetailPresenter> implements JuheNewsDetailWebView, AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener, AMap.OnMapTouchListener {

    private static final String TAG = WebViewDetailActivity.class.getSimpleName();
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.url_web)
    WebView urlWeb;
    @BindView(R.id.iv_detail_top)
    ImageView ivDetailTop;
    @BindView(R.id.news_image_mask)
    View newsImageMask;
    @BindView(R.id.tv_imgSource)
    TextView tvImgSource;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
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
    @BindView(R.id.iv_web_view_error)
    ImageView ivWebViewError;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.custom_scroll_container)
    CustomScrollRelativeLayout customScrollContainer;
    @BindView(R.id.iv_btn_my_location)
    ImageView ivBtnMyLocation;
    @BindView(R.id.rv_map)
    RecyclerView rvMap;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private String title;
    private String url;
    private String imageUrl;

    private String userIcon = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3706852963,2399513353&fm=26&gp=0.jpg";

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

    private Map<String, LatLng> poaitionMap = new HashMap<>();
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private HomeContainerAdapter homeContainerAdapter;

    private static final int PAGE_START = 0;
    private int currPage = PAGE_START;

    public static void actionStart(Context context, String title, String url, String imageUrl) {
        Intent intent = new Intent(context, WebViewDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("imageUrl", imageUrl);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String title, String url, String imageUrl, View view, String sharedElementName) {
        Intent intent = new Intent(context, WebViewDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("imageUrl", imageUrl);
        if (Build.VERSION.SDK_INT > 20) {
            Bundle options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, view, sharedElementName).toBundle();
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
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
    protected JuheNewsDetailPresenter createPresenter() {
        return new JuheNewsDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_juhe_news_detail;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juhe_news_detail);
        ButterKnife.bind(this);
        //随系统设置更改ToolBar状态栏颜色
        getActionBarTheme(null, toolbarLayout);
        //google官方在安卓6.0以上版本才推出的深色状态栏字体api
        changeStatusBarTextColor(true);
        customScrollContainer.setCollapsingToolbarLayout(toolbarLayout);
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
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarLeftIcon.setImageDrawable(getDrawable(R.drawable.abc_ic_ab_back_material));
        baseToolbarRightIcon.setVisibility(View.GONE);


        //得到Intent传递的数据
        parseIntent();
        //WebViewInterface
        mPresenter.setWebView(url);
        ImageLoaderUtils.loadRadiusImage(WebViewDetailActivity.this, true, ivDetailTop,
                imageUrl, R.drawable.image_top_default, R.drawable.image_top_default, 0);
//        mPresenter.setWebView("https://blog.csdn.net/u013139425/article/details/79519268?tdsourcetag=s_pcqq_aiomsg");

        toolbarLayout.setTitle(title);
        //设置CollapsingToolbarLayout扩展状态标题栏颜色
        toolbarLayout.setExpandedTitleColor(Color.YELLOW);
        //设置CollapsingToolbarLayout收缩状态标题栏颜色
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        initRecyclerView();

        //设置监听android.support.design.widget.AppBarLayout这个控件设置监听了
        //因为android.support.design.widget.CollapsingToolbarLayout外层是 android.support.design.widget.AppBarLayout所以设置的监听是它了
        initListener();
    }

    /**
     * CollapsingToolbarLayout滑动状态监听
     * 因为android.support.design.widget.CollapsingToolbarLayout外层是android.support.design.widget.AppBarLayout所以设置的监听是它了
     *
     */
    private void initListener() {
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    baseToolbarRightIcon.setVisibility(View.GONE);
                    baseToolbarLeftIcon.setImageResource(R.drawable.abc_ic_ab_back_material);
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    baseToolbarRightIcon.setVisibility(View.VISIBLE);
                    baseToolbarLeftIcon.setImageResource(R.drawable.ic_back);
                }else {
                    //中间状态
                    baseToolbarRightIcon.setVisibility(View.GONE);
                    baseToolbarLeftIcon.setImageResource(R.drawable.abc_ic_ab_back_material);
                }
            }
        });

    }

    private void initData(int page) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<HomeBean>() {
                    @Override
                    protected void onStart() {

                    }

                    @Override
                    protected void onSuccess(HomeBean data) {
                        if (currPage == PAGE_START) {
                            //第一页数据
                            homeContainerAdapter.setNewData(data.getDatas());
                            LogUtil.d(TAG, "下拉刷新：  " + "数量: " + homeContainerAdapter.getData().size());
                        } else {
                            //请求更多数据,直接添加list中
                            homeContainerAdapter.addData(data.getDatas());
                            homeContainerAdapter.loadMoreComplete();
                            LogUtil.d(TAG, "上拉加载更多：  " + "数量: " + homeContainerAdapter.getData().size());
                        }

                        if (data.isOver() || data.getDatas().size() == 0) {
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
                    protected void onFail(String errorMsg) {
                        onShowToast(errorMsg);
                        homeContainerAdapter.loadMoreFail();
                        //这两个方法是在加载失败时调用的
                        smartRefreshLayout.finishRefresh(false);
                        smartRefreshLayout.finishLoadMore(false);
                    }

                    @Override
                    protected void onFinish() {

                    }
                });
    }

    private void initRecyclerView() {
        smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(MyApplication.getAppContext()));
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
                initData(currPage);
            }
        });

        boolean setRefreshFooter = isSetRefreshFooter();
        if (setRefreshFooter) {
            smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    currPage++;
                    initData(currPage);
                }
            });
        } else {
            homeContainerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    currPage++;
                    initData(currPage);
                }
            }, rvMap);
        }

        //自动刷新(替代第一次请求数据)
        smartRefreshLayout.autoRefresh();

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

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("imageUrl");
        LogUtil.d(TAG, "title:  " + title + "  url:  " + url);
    }

    @Override
    public ProgressBar getProgressBar() {
        return pbProgress;
    }

    @Override
    public WebView getWebView() {
        return urlWeb;
    }

    @Override
    public ImageView getErrorView() {
        return ivWebViewError;
    }

    @Override
    public void setToast(String content) {
        ToastUtil.setCustomToast(WebViewDetailActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                true, content, Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
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
        urlWeb.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
    }

    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, R.id.iv_detail_top, R.id.iv_btn_my_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                finish();
                break;

            case R.id.toolbar_right_layout:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, url);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到..."));
                break;

            case R.id.iv_detail_top:
                SinglePictureActivity.actionStart(WebViewDetailActivity.this, imageUrl, title);
                break;

            case R.id.iv_btn_my_location:
                LatLng latLng = poaitionMap.get("position");
                Log.e("LocationChange", "onMyLocationChange再定位: " + new Gson().toJson(latLng));
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
                break;
        }
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
            setToast("定位失败");
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
}
