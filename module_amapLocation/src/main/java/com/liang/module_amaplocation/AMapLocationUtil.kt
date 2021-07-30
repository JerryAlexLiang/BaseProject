package com.liang.module_amaplocation

import android.content.Context
import androidx.lifecycle.*
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.utils.NetUtil

/**
 * 创建日期: 2021/7/30 on 1:51 PM
 * 描述: 高德地图定位功能封装
 * 作者: 杨亮
 */
class AMapLocationUtil(private val mContext: Context) : LifecycleObserver, AMapLocationListener {

    private var locationClient: AMapLocationClient? = null

    private var locationClientOption: AMapLocationClientOption? = null

    private var locationMutableLiveData: MutableLiveData<AMapLocation>? = null

    interface OnLocationCallbackListener {
        fun onLocationFail(location: AMapLocation?)
        fun onLocationSuccess(location: AMapLocation?)
    }

    private var mOnCallBackListener: OnLocationCallbackListener? = null

    fun setonLocationCallbackListener(listener: OnLocationCallbackListener?) {
        mOnCallBackListener = listener
    }

    private fun setLocationSuccess(location: AMapLocation?) {
        if (mOnCallBackListener != null) {
            mOnCallBackListener!!.onLocationSuccess(location)
        }
    }

    private fun setLocationFail(location: AMapLocation?) {
        if (mOnCallBackListener != null) {
            mOnCallBackListener!!.onLocationFail(location)
        }
    }

    /**
     * 初始化定位
     */
    fun initLocation() {
        //初始化client
        if (null == locationClient) {
            locationClient = AMapLocationClient(mContext)
        }
        locationClientOption = getLocationClientOption()
        //设置定位参数
        locationClient!!.setLocationOption(locationClientOption)
        //设置定位监听
        locationClient!!.setLocationListener(this)
    }

    private fun getLocationClientOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        //如果网络可用就选择高精度
        if (NetUtil.isNetworkAvailable(mContext)) {
            //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //可选，设置是否GPS优先，只在高精度模式下有效。默认关闭
            mOption.isGpsFirst = true
        } else {
            //如果网络不可用，就选择仅设备模式
            mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors
            mOption.isGpsFirst = true
        }
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.httpTimeOut = (30 * 1000).toLong()
        //可选，设置定位间隔。默认为5秒
        mOption.interval = (5 * 1000).toLong()
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.isNeedAddress = true
        //可选，设置是否单次定位。默认是false
        mOption.isOnceLocation = false
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.isOnceLocationLatest = false
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)
        //可选，设置是否使用传感器。默认是false
        mOption.isSensorEnable = true
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isWifiScan = true
        //可选，设置是否使用缓存定位，默认为true
        mOption.isLocationCacheEnable = true
        return mOption
    }

    /**
     * 设置定位监听
     * AMapLocationListener locationListener = new AMapLocationListener()
     */
    override fun onLocationChanged(aMapLocation: AMapLocation) {
        val sb = StringBuilder()
        if (aMapLocation.errorCode == 0) {
            longitude = aMapLocation.longitude
            latitude = aMapLocation.latitude
            val district = aMapLocation.district
            //定位成功，停止定位：如果实时定位，就把stopLocation()关闭
            stopLocation()
            LogUtil.e(TAG, "======> 定位成功: " + GsonUtils.toJson(aMapLocation))
            setLocationSuccess(aMapLocation)
            locationMutableLiveData!!.setValue(aMapLocation)
        } else {
            //定位失败
            sb.append("""定位失败""".trimIndent())
            sb.append("""错误码:${aMapLocation.errorCode}""".trimIndent())
            sb.append("""错误信息:${aMapLocation.errorInfo}""".trimIndent())
            sb.append("""错误描述:${aMapLocation.locationDetail}""".trimIndent())
            LogUtil.e(TAG, "======> 定位失败: $sb")
            setLocationFail(aMapLocation)
            locationMutableLiveData!!.setValue(aMapLocation)
        }
    }

    /**
     * 获取locationLiveData
     */
//    val locationLiveData: LiveData<AMapLocation>
//        get() {
//            if (locationMutableLiveData == null) {
//                locationMutableLiveData = MutableLiveData()
//            }
//            return locationMutableLiveData as MutableLiveData<AMapLocation>
//        }

    fun getLocationLiveData(): LiveData<AMapLocation> {
        if (locationMutableLiveData == null) {
            locationMutableLiveData = MutableLiveData<AMapLocation>()
        }
        return locationMutableLiveData as MutableLiveData<AMapLocation>
    }

    /**
     * 开启定位
     */
    fun startLocation() {
        locationClient!!.startLocation()
        LogUtil.e(TAG, "======> 开始定位: ")
    }

    /**
     * 关闭定位
     */
    fun stopLocation() {
        locationClient!!.stopLocation()
        LogUtil.e(TAG, "======> 关闭定位: ")
    }

    /**
     * 销毁定位
     * 当Activity执行onDestroy()方法时，该方法会被自动调用
     *
     *
     * 如果AMapLocationClient是在当前Activity实例化的，在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroyLocation() {
        LogUtil.e(TAG, "======> 执行销毁定位: ")
        locationClient!!.onDestroy()
        locationClient = null
        locationClientOption = null
    }

    companion object {
        val TAG = AMapLocationUtil::class.java.simpleName
        var longitude = 0.0
        var latitude = 0.0
    }
}