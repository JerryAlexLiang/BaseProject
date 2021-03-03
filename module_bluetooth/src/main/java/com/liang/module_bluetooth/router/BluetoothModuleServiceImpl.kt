package com.liang.module_bluetooth.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.BluetoothModuleService
import com.liang.module_bluetooth.activity.BleHomeActivity
import org.jetbrains.anko.startActivity

/**
 * 创建日期: 2021/2/2 on 4:31 PM
 * 描述:
 * 作者: 杨亮
 */
@Route(path = AppRouter.MODULE_BLUETOOTH)
class BluetoothModuleServiceImpl :BluetoothModuleService{

    override fun startBluetoothMainActivity(context: Context) {
//        context.startActivity<BluetoothMainActivity>()
        context.startActivity<BleHomeActivity>()
    }

    override fun init(context: Context?) {

    }
}