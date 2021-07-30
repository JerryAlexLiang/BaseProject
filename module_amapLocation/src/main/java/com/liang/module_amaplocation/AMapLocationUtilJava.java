package com.liang.module_amaplocation;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.liang.module_core.utils.GsonUtils;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.utils.NetUtil;

/**
 * 创建日期: 2021/7/30 on 1:51 PM
 * 描述: 高德地图定位功能封装
 * 作者: 杨亮
 */
public class AMapLocationUtilJava implements LifecycleObserver, AMapLocationListener {

    public static final String TAG = AMapLocationUtilJava.class.getSimpleName();

    private Context mContext;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationClientOption = null;

    public static double longitude = 0;
    public static double latitude = 0;

    private MutableLiveData<AMapLocation> locationMutableLiveData = null;

//    public interface onLocationCallbackListener {
//        void onCallback(double longitude, double latitude, AMapLocation location, boolean isLocationSuccess, String address);
//    }

    public interface OnLocationCallbackListener {
        void onLocationFail(AMapLocation location);

        void onLocationSuccess(AMapLocation location);
    }

    private OnLocationCallbackListener mOnCallBackListener = null;

    public void setonLocationCallbackListener(OnLocationCallbackListener listener) {
        this.mOnCallBackListener = listener;
    }

    public void setLocationSuccess(AMapLocation location) {
        if (mOnCallBackListener != null) {
            mOnCallBackListener.onLocationSuccess(location);
        }
    }

    public void setLocationFail(AMapLocation location) {
        if (mOnCallBackListener != null) {
            mOnCallBackListener.onLocationFail(location);
        }
    }

    public AMapLocationUtilJava(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 初始化定位
     */
    public void initLocation() {
        //初始化client
        if (null == locationClient) {
            locationClient = new AMapLocationClient(mContext);
        }
        locationClientOption = getLocationClientOption();
        //设置定位参数
        locationClient.setLocationOption(locationClientOption);
        //设置定位监听
        locationClient.setLocationListener(this);
    }

    private AMapLocationClientOption getLocationClientOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //如果网络可用就选择高精度
        if (NetUtil.isNetworkAvailable(mContext)) {
            //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //可选，设置是否GPS优先，只在高精度模式下有效。默认关闭
            mOption.setGpsFirst(true);
        } else {
            //如果网络不可用，就选择仅设备模式
            mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            mOption.setGpsFirst(true);
        }
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30 * 1000);
        //可选，设置定位间隔。默认为5秒
        mOption.setInterval(5 * 1000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(false);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(true);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        return mOption;
    }

    /**
     * 设置定位监听
     * AMapLocationListener locationListener = new AMapLocationListener()
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        StringBuilder sb = new StringBuilder();
        if (null != aMapLocation) {
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (aMapLocation.getErrorCode() == 0) {
                longitude = aMapLocation.getLongitude();
                latitude = aMapLocation.getLatitude();
                String district = aMapLocation.getDistrict();
                //定位成功，停止定位：如果实时定位，就把stopLocation()关闭
                stopLocation();
                LogUtil.e(TAG, "======> 定位成功: " + GsonUtils.toJson(aMapLocation));

                setLocationSuccess(aMapLocation);

                locationMutableLiveData.setValue(aMapLocation);

            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + aMapLocation.getErrorCode() + "\n");
                sb.append("错误信息:" + aMapLocation.getErrorInfo() + "\n");
                sb.append("错误描述:" + aMapLocation.getLocationDetail() + "\n");
                LogUtil.e(TAG, "======> 定位失败: " + sb.toString());

                setLocationFail(aMapLocation);

                locationMutableLiveData.setValue(aMapLocation);
            }
        }
    }

    /**
     * 获取locationLiveData
     */
    public LiveData<AMapLocation> getLocationLiveData() {
        if (locationMutableLiveData == null) {
            locationMutableLiveData = new MutableLiveData<>();
        }
        return locationMutableLiveData;
    }

    /**
     * 开启定位
     */
    public void startLocation() {
        locationClient.startLocation();
        LogUtil.e(TAG, "======> 开始定位: ");
    }

    /**
     * 关闭定位
     */
    public void stopLocation() {
        locationClient.stopLocation();
        LogUtil.e(TAG, "======> 关闭定位: ");
    }

    /**
     * 销毁定位
     * 当Activity执行onDestroy()方法时，该方法会被自动调用
     * <p>
     * 如果AMapLocationClient是在当前Activity实例化的，在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroyLocation() {
        LogUtil.e(TAG, "======> 执行销毁定位: ");
        locationClient.onDestroy();
        locationClient = null;
        locationClientOption = null;
    }
}
