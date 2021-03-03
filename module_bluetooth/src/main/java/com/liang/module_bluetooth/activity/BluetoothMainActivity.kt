package com.liang.module_bluetooth.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigkoo.alertview.AlertView
import com.inuker.bluetooth.library.Constants
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener
import com.inuker.bluetooth.library.connect.options.BleConnectOptions
import com.inuker.bluetooth.library.connect.response.BleConnectResponse
import com.inuker.bluetooth.library.search.SearchRequest
import com.inuker.bluetooth.library.search.SearchResult
import com.inuker.bluetooth.library.search.response.SearchResponse
import com.inuker.bluetooth.library.utils.BluetoothLog
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
 * 创建日期:2021/2/2 on 4:43 PM
 * 描述: 扫描蓝牙连接硬件设备
 * 作者: 杨亮
 */
class BluetoothMainActivity : MVVMBaseActivity() {

    private var blueDevices = mutableListOf<SearchResult>()

    private lateinit var deviceAdapter: DeviceAdapter

    companion object {

        val TAG = BluetoothMainActivity::class.java.simpleName

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
        initView()
        initListener()

    }

    private fun initListener() {
        setOnClickListener(btnScan,base_actionbar_left_icon) {
            when (this) {
                btnScan -> {
                    if (btnScan.text == getString(R.string.start_scan)) {
                        //开始扫描
                        checkPermissions()
                    } else if (btnScan.text == getString(R.string.stop_scan)) {
                        //停止扫描

                    }
                }

                base_actionbar_left_icon->{
                    finish()
                }
            }
        }
    }

    private fun checkPermissions() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!bluetoothAdapter.isEnabled) {
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

    private fun initView() {
        base_actionbar_left_icon!!.visibility = View.VISIBLE
        base_actionbar_title!!.visibility = View.VISIBLE
        base_actionbar_title!!.text = this.resources.getString(R.string.titleBar2)

        deviceAdapter = DeviceAdapter(R.layout.ble_rv_adapter_device)
        val linearLayoutManager = LinearLayoutManager(this)
        rvBleDevice.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dp2px(12);
            }
        })
        rvBleDevice.layoutManager = linearLayoutManager
        rvBleDevice.adapter = deviceAdapter
        initRvItemClickListener()

    }

    private var mConnected = false

    private fun initRvItemClickListener() {
        deviceAdapter.setOnDeviceClickListener(object : DeviceAdapter.OnDeviceClickListener {

            override fun onConnect(bleDevice: SearchResult?) {
                connectDevice(bleDevice)

            }

            override fun onDisConnect(bleDevice: SearchResult?) {
                val mac = bleDevice?.address
                ClientManager.client?.disconnect(mac)
            }

            override fun onDetail(bleDevice: SearchResult?) {
                val intent = Intent(this@BluetoothMainActivity, BlueDetailActivity::class.java)
                intent.putExtra("KEY_DATA_DEVICE", bleDevice)
                startActivity(intent)
            }
        })
    }

    private fun connectDevice(bleDevice: SearchResult?) {
        progressBar.visibility = View.VISIBLE

        val options = BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(20000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build()

        val mDevice = bleDevice?.device

        ClientManager.client?.connect(mDevice?.address, options, BleConnectResponse { _, profile ->
            LogUtil.d(TAG, "connectDevice    =========>   " + String.format("profile:\n%s", profile))
            progressBar.visibility = View.INVISIBLE
            deviceAdapter.setDeviceConnect(true)
            ToastUtil.onShowSuccessRectangleToast(this, "连接成功")
        })

        ClientManager.client?.registerConnectStatusListener(mDevice!!.address, object : BleConnectStatusListener() {
            override fun onConnectStatusChanged(mac: String?, status: Int) {
                LogUtil.d(TAG, "onConnectStatusChanged    =========>   " + String.format(String.format("DeviceDetailActivity onConnectStatusChanged %d in %s",
                        status, Thread.currentThread().name)))
                mConnected = status == Constants.STATUS_CONNECTED
                if (!mConnected) {
                    progressBar.visibility = View.INVISIBLE
                    ToastUtil.onShowFailRectangleToast(this@BluetoothMainActivity, "断开连接")
                    deviceAdapter.setDeviceConnect(false)
                }
            }
        })
    }

    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(), resources.displayMetrics).toInt()
    }

    private fun onPermissionGranted(permission: String) {
        when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
//                AlertDialog.Builder(this)
//                        .setTitle(R.string.notifyTitle)
//                        .setMessage(R.string.gpsNotifyMsg)
//                        .setNegativeButton(R.string.cancel) { _, _ -> finish() }
//                        .setPositiveButton(R.string.setting) { _, _ ->
//                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS)
//                        }
//                        .setCancelable(false)
//                        .show()

                //手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
                AlertView.Builder()
                        .setContext(this)
                        .setTitle(resources.getString(R.string.notifyTitle))
                        .setMessage(resources.getString(R.string.gpsNotifyMsg))
                        .setDestructive(resources.getString(R.string.cancel), resources.getString(R.string.setting))
                        .setOthers(null)
                        .setStyle(AlertView.Style.Alert)
                        .setOnItemClickListener { o, position ->
                            when (position) {
                                0 -> toast("取消")
                                1 -> {
                                    intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivityForResult(intent, REQUEST_POSITION_LOCATION);
                                }
                            }
                        }
                        .build()
                        .show()
            } else {
                startLeScan()
            }
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

    private fun checkGPSIsOpen(): Boolean {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                ?: return false
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun startLeScan() {
        blueDevices.clear()

        val request = SearchRequest.Builder()
                .searchBluetoothLeDevice(5000, 2).build()

        ClientManager.client?.search(request, mSearchResponse)

        //开始扫描
        ClientManager.client?.registerBluetoothStateListener(object : BluetoothStateListener() {
            override fun onBluetoothStateChanged(openOrClosed: Boolean) {
                BluetoothLog.v(String.format("onBluetoothStateChanged %b", openOrClosed))
            }
        })

    }

    private val mSearchResponse: SearchResponse = object : SearchResponse {

        override fun onSearchStarted() {
            LogUtil.d(TAG, "onSearchStarted")
            btnScan.text = resources.getString(R.string.string_refreshing)
            blueDevices.clear()
            deviceAdapter.notifyDataSetChanged()
        }

        override fun onDeviceFounded(device: SearchResult?) {

            LogUtil.d(TAG, "onDeviceFounded    =========>   " + device?.device!!.address)

            if (!blueDevices.contains(device)) {
                if (device.address == "DE:7E:F0:7F:8F:EC") {
                    blueDevices.add(device)
                    LogUtil.d("onDeviceFounded2", "onDeviceFounded1    =========>   " + blueDevices.size)
//                    deviceAdapter.setList(blueDevices)
                    deviceAdapter.setNewData(blueDevices)
                    deviceAdapter.notifyDataSetChanged()
                    blueDevices.reverse()
                    LogUtil.d("onDeviceFounded2", "onDeviceFounded2    =========>   " + deviceAdapter.data.size)
                }
            }
        }

        override fun onSearchStopped() {
            LogUtil.d(TAG, "onSearchStopped")
            btnScan.text = resources.getString(R.string.start_scan)
        }

        override fun onSearchCanceled() {
            LogUtil.d(TAG, "onSearchCanceled")
            btnScan.text = resources.getString(R.string.start_scan)

        }

    }

    override fun onPause() {
        super.onPause()
        ClientManager.client?.stopSearch()
    }

}