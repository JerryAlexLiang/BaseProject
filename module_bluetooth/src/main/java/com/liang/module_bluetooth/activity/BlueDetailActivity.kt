package com.liang.module_bluetooth.activity

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.inuker.bluetooth.library.Constants
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener
import com.inuker.bluetooth.library.connect.options.BleConnectOptions
import com.inuker.bluetooth.library.connect.response.BleConnectResponse
import com.inuker.bluetooth.library.model.BleGattProfile
import com.inuker.bluetooth.library.search.SearchResult
import com.liang.module_bluetooth.model.DetailItem
import com.liang.module_bluetooth.adapter.DeviceDetailAdapter
import com.liang.module_bluetooth.R
import com.liang.module_bluetooth.ble.ClientManager
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.utils.setOnClickListener
import kotlinx.android.synthetic.main.activity_blue_detail.*
import kotlinx.android.synthetic.main.ble_layout_base_actionbar_default.*
import java.util.*

class BlueDetailActivity : MVVMBaseActivity() {

    private var deviceBroadcastName: String? = null
    private lateinit var deviceDetailAdapter: DeviceDetailAdapter

    private var device: BluetoothDevice? = null
    private var mac: String? = null
    private var bleDevice: SearchResult? = null

    private var detailItems: MutableList<DetailItem> = mutableListOf<DetailItem>()

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
        return R.layout.activity_blue_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initListener() {
        ClientManager.client?.registerConnectStatusListener(bleDevice!!.address, mConnectStatusListener)

        setOnClickListener(base_actionbar_left_icon) {
            when (this) {
                base_actionbar_left_icon -> {
                    finish()
                }

                else -> {
                }
            }
        }
    }

    private val mConnectStatusListener: BleConnectStatusListener = object : BleConnectStatusListener() {
        override fun onConnectStatusChanged(mac: String, status: Int) {
            LogUtil.d(BluetoothMainActivity.TAG, "onConnectStatusChanged    =========>   " + String.format(String.format("DeviceDetailActivity onConnectStatusChanged %d in %s",
                    status, Thread.currentThread().name)))
            val mConnected = status == Constants.STATUS_CONNECTED
            if (!mConnected) {
                //断开重连
                base_actionbar_title!!.text = "断开重连"
                ToastUtil.onShowWarnRectangleToast(this@BlueDetailActivity, "断开重连")
                connectDevice(bleDevice!!)
            }
        }
    }

    private fun connectDevice(bleDevice: SearchResult) {
        val options = BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(20000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build()

        val mDevice = bleDevice.device

        ClientManager.client?.connect(mDevice?.address, options, BleConnectResponse { _, profile ->
            LogUtil.d(BluetoothMainActivity.TAG, "connectDevice    =========>   " + String.format("profile:\n%s", profile))
            base_actionbar_title!!.text = deviceBroadcastName
            ToastUtil.onShowSuccessRectangleToast(this, "连接成功")
            setGattProfile(profile)
        })
    }

    private fun initView() {
        base_actionbar_left_icon!!.visibility = View.VISIBLE
        base_actionbar_title!!.visibility = View.VISIBLE
        base_actionbar_title!!.text = this.resources?.getString(R.string.titleBar) ?: ""

        deviceDetailAdapter = DeviceDetailAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        rvBleDeviceDetail.layoutManager = linearLayoutManager
        rvBleDeviceDetail.adapter = deviceDetailAdapter

    }

    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(), resources?.displayMetrics).toInt()
    }

    private fun initData() {
        bleDevice = intent.getParcelableExtra<SearchResult>("KEY_DATA_DEVICE")
        if (bleDevice == null) {
            finish()
        }

        mac = bleDevice?.address
        deviceBroadcastName = bleDevice?.name
        device = bleDevice?.device

        tvMacAddress.text = "MAC :  $mac"
        base_actionbar_title!!.text = deviceBroadcastName

        connectDevice(bleDevice!!)

    }

    private fun setGattProfile(profile: BleGattProfile) {
        val items: MutableList<DetailItem> = ArrayList()
        val services = profile.services
        for (service in services) {
            items.add(DetailItem(DetailItem.TYPE_SERVICE, service.uuid, null))
            val characters = service.characters
            for (character in characters) {
                items.add(DetailItem(DetailItem.TYPE_CHARACTER, character.uuid, service.uuid))
            }
        }
        setDataList(items)
    }

    private fun setDataList(items: MutableList<DetailItem>) {
        detailItems.clear()
        detailItems.addAll(items)
        deviceDetailAdapter.setNewInstance(detailItems)
        deviceDetailAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
//        ClientManager.client?.disconnect(mac)
        ClientManager.client?.unregisterConnectStatusListener(mac, mConnectStatusListener)
        super.onDestroy()

    }
}