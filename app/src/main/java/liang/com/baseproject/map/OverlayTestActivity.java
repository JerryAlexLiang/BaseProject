package liang.com.baseproject.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.utils.MyPointOverlay;

/**
 * 创建日期：2020/1/8 on 15:19
 * 描述: 自定义地图Overlay-解决经纬度点地图显示适配问题
 * 作者: liangyang
 */
public class OverlayTestActivity extends MVPBaseActivity implements AMap.OnMapLoadedListener {

    @BindView(R.id.map_view)
    MapView mapView;
    private AMap aMap;

    public static final LatLng POSITION_HANGZHOU = new LatLng(30.291115, 120.114108);
    private List<LatLng> list;

    @Override
    protected boolean isRegisterEventBus() {
        return false;
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
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_overlay_test;
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,OverlayTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap(savedInstanceState);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            mapView.setSelected(true);
            setMap();
        }
        //禁止地图旋转手势
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        aMap.setOnMapLoadedListener(this);
    }

    private void setMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
        CameraUpdate moveCity = CameraUpdateFactory.newLatLngZoom(POSITION_HANGZHOU, 17);
        aMap.moveCamera(moveCity);
        aMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.icon_location))).
                position(POSITION_HANGZHOU));
    }

    /**
     * 当地图加载完后
     */
    @Override
    public void onMapLoaded() {
        initData();
        List<PoiItem> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            LatLonPoint point = new LatLonPoint(list.get(i).latitude, list.get(i).longitude);
            PoiItem p = new PoiItem("1", point, null, null);
            list1.add(p);
        }
        MyPointOverlay myPointOverlay = new MyPointOverlay(aMap, list1);
        myPointOverlay.removeFromMap();
        myPointOverlay.addToMap();
        myPointOverlay.zoomToSpan();
//        myPointOverlay.AddLineToAmap();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new LatLng(30.317839,119.970362));
        list.add(new LatLng(30.29383,120.099795));
        list.add(new LatLng(30.268037,119.970362));
        list.add(new LatLng(30.317839,120.119364));
        list.add(new LatLng(30.315468,120.080397));
        list.add(new LatLng(30.290851,120.2126));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
