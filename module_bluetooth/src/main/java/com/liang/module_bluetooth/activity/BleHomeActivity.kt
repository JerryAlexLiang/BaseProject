package com.liang.module_bluetooth.activity

import android.os.Bundle
import android.view.View
import com.liang.module_bluetooth.R
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.setOnClickListener
import kotlinx.android.synthetic.main.activity_ble_home.*
import kotlinx.android.synthetic.main.ble_layout_base_actionbar_default.*
import org.jetbrains.anko.startActivity

class BleHomeActivity : MVVMBaseActivity() {

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
        return R.layout.activity_ble_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        base_actionbar_left_icon!!.visibility = View.VISIBLE
        base_actionbar_title!!.visibility = View.VISIBLE
        base_actionbar_title!!.text = this.resources.getString(R.string.titleBar)
    }

    private fun initListener() {
        setOnClickListener(base_actionbar_left_icon, btnBleServiceNormal, btnBleInukerLibNormal) {
            when (this) {
                base_actionbar_left_icon -> {
                    finish()
                }

                btnBleServiceNormal -> {
                    startActivity<BleServiceNormalActivity>()
                }

                btnBleInukerLibNormal -> {
                    startActivity<BluetoothMainActivity>()
                }

            }
        }
    }
}