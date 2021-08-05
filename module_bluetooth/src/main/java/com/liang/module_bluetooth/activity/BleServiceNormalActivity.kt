package com.liang.module_bluetooth.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigkoo.alertview.AlertView
import com.inuker.bluetooth.library.search.SearchResult
import com.liang.module_bluetooth.R
import com.liang.module_bluetooth.adapter.DeviceAdapter
import com.liang.module_bluetooth.ble.ClientManager
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.utils.setOnClickListener
import kotlinx.android.synthetic.main.ble_activity_bluetooth_main.*
import kotlinx.android.synthetic.main.ble_layout_base_actionbar_default.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * 创建日期:2021/2/4 on 2:54 PM
 * 描述: 蓝牙BLE开发-官方API
 * 作者: 杨亮
 */
class BleServiceNormalActivity : MVVMBaseActivity(), BluetoothAdapter.LeScanCallback {

    //    private lateinit var mScanCallback: SampleScanCallback
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var mBLEScanner: BluetoothLeScanner? = null

    private var blueDevices = mutableListOf<SearchResult>()
    private lateinit var deviceAdapter: DeviceAdapter

    companion object {
        val TAG = BleServiceNormalActivity::class.java.simpleName

        const val REQUEST_CODE_OPEN_GPS = 1
        const val REQUEST_CODE_PERMISSION_LOCATION = 2

        const val REQUEST_POSITION_LOCATION = 5 //请求位置信息
        const val REQUEST_ENABLE_BT = 6//启动蓝牙
    }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun provideContentViewId(): Int {
        return R.layout.ble_activity_bluetooth_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mScanCallback = SampleScanCallback()
        initView()
        initListener()
    }

    private fun initView() {
        base_actionbar_left_icon!!.visibility = View.VISIBLE
        base_actionbar_title!!.visibility = View.VISIBLE
        base_actionbar_title!!.text = this.resources?.getString(R.string.titleBar1) ?: ""

        deviceAdapter = DeviceAdapter(R.layout.ble_rv_adapter_device)
        val linearLayoutManager = LinearLayoutManager(this)
        rvBleDevice.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dp2px(12)
            }
        })
        rvBleDevice.layoutManager = linearLayoutManager
        rvBleDevice.adapter = deviceAdapter
        initRvItemClickListener()
    }

    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(), resources?.displayMetrics).toInt()
    }

    private fun initListener() {
        setOnClickListener(btnScan, base_actionbar_left_icon) {
            when (this) {
                btnScan -> {
                    if (btnScan.text == getString(R.string.start_scan)) {
                        //开始扫描
                        checkPermissions()
                    } else if (btnScan.text == getString(R.string.stop_scan)) {
                        //停止扫描

                    }
                }

                base_actionbar_left_icon -> {
                    finish()
                }
            }
        }
    }

    private fun initRvItemClickListener() {
        deviceAdapter.setOnDeviceClickListener(object : DeviceAdapter.OnDeviceClickListener {

            override fun onConnect(bleDevice: SearchResult?) {
//                connectDevice(bleDevice)
                ToastUtil.onShowWarnRectangleToast(this@BleServiceNormalActivity, bleDevice?.name)

            }

            override fun onDisConnect(bleDevice: SearchResult?) {
                val mac = bleDevice?.address
                ClientManager.client?.disconnect(mac)
            }

            override fun onDetail(bleDevice: SearchResult?) {
                val intent = Intent(this@BleServiceNormalActivity, BlueDetailActivity::class.java)
                intent.putExtra("KEY_DATA_DEVICE", bleDevice)
                startActivity(intent)
            }
        })
    }

    private fun checkPermissions() {
//        val manager: BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        val adapter = manager.adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            ToastUtil.onShowFailRectangleToast(this, resources?.getString(R.string.not_have_blue))
            return
        }
        if (!bluetoothAdapter!!.isEnabled) {
            Toast.makeText(this, getString(R.string.please_open_blue), Toast.LENGTH_LONG).show()
            return
        }
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionDeniedList: MutableList<String> = ArrayList()
        for (permission in permissions) {
            val permissionCheck: Int = ContextCompat.checkSelfPermission(this, permission)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission)
            } else {
                permissionDeniedList.add(permission)
            }
        }
        if (permissionDeniedList.isNotEmpty()) {
            val deniedPermissions = permissionDeniedList.toTypedArray()
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION_LOCATION -> if (grantResults.isNotEmpty()) {
                var i = 0
                while (i < grantResults.size) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        onPermissionGranted(permissions[i])
                    }
                    i++
                }
            }
        }
    }

    private fun onPermissionGranted(permission: String) {
        when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                //手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
                AlertView.Builder()
                        .setContext(this)
                        .setTitle(resources?.getString(R.string.notifyTitle))
                        .setMessage(resources?.getString(R.string.gpsNotifyMsg))
                        .setDestructive(resources?.getString(R.string.cancel), resources?.getString(R.string.setting))
                        .setOthers(null)
                        .setStyle(AlertView.Style.Alert)
                        .setOnItemClickListener { o, position ->
                            when (position) {
                                0 -> toast("取消")
                                1 -> {
                                    intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivityForResult(intent, REQUEST_POSITION_LOCATION)
                                }
                            }
                        }
                        .build()
                        .show()
            } else {
                //开始扫描蓝牙设备
                startLeScan()
            }
        }
    }

    private fun checkGPSIsOpen(): Boolean {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * Android5.0+ 扫描蓝牙API
     */
    private fun startLeScan() {
//        btnScan.text = "蓝牙扫描中..."
//        //Android5.0+ 扫描蓝牙类实例
//        mBLEScanner = bluetoothAdapter?.bluetoothLeScanner
//        //开始扫描,可设置过滤条件，在第一个参数传入，但一般不设置过滤
//        mBLEScanner?.stopScan(mScanCallback)
//        mBLEScanner?.startScan(mScanCallback)
//        //搜索10秒
//        Handler().postDelayed({
//            mBLEScanner?.stopScan(mScanCallback)
//            btnScan.text = "开始扫描"
//        }, 10000)

        btnScan.text = resources?.getString(R.string.string_refreshing) ?: ""
        blueDevices.clear()
        deviceAdapter.notifyDataSetChanged()

        bluetoothAdapter?.stopLeScan(this)
        bluetoothAdapter?.startLeScan(this)
        //搜索10秒
        Handler().postDelayed({
            bluetoothAdapter?.stopLeScan(this)
            btnScan.text = resources?.getString(R.string.start_scan) ?: ""
        }, 10000)
    }

    /**
     * 搜索蓝牙设备回调
     */
    override fun onLeScan(device: BluetoothDevice?, rssi: Int, scanRecord: ByteArray?) {
        val name = device?.name
        val address = device?.address

        val searchResult = SearchResult(device)

        if (!blueDevices.contains(searchResult)) {
            if (searchResult.address == "DE:7E:F0:7F:8F:EC") {
                blueDevices.add(searchResult)
                deviceAdapter.setNewInstance(blueDevices)
                deviceAdapter.notifyDataSetChanged()
                blueDevices.reverse()

            }
        }
    }

//    /**
//     * 搜索蓝牙设备回调
//     */
//    inner class SampleScanCallback : ScanCallback() {
//
//        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
//            super.onBatchScanResults(results)
//            if (results != null) {
//                for (result in results) {
//                    val address = result.device.address
//                    val name = result.device.name
//                    LogUtil.d(TAG, "扫描onBatchScanResults ====>  $name MAC: $address")
//                }
//            }
//        }
//
//        override fun onScanResult(callbackType: Int, result: ScanResult?) {
//            super.onScanResult(callbackType, result)
//            if (result != null) {
//                val address = result.device.address
//                val name = result.device.name
//                if (address == "DE:7E:F0:7F:8F:EC") {
//                    ToastUtil.onShowSuccessRectangleToast(this@BleServiceNormalActivity, name)
//                }
//            }
//
//        }
//
//        override fun onScanFailed(errorCode: Int) {
//            super.onScanFailed(errorCode)
//            ToastUtil.onShowFailRectangleToast(this@BleServiceNormalActivity, "Scan failed with error: $errorCode")
//
//        }
//
//    }


}