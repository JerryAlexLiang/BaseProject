package com.liang.module_bluetooth

import android.app.Application
import android.content.Context
import com.inuker.bluetooth.library.BluetoothContext
import com.liang.model_middleware.app.BaseApplicationImpl
import com.liang.module_core.app.BaseApplication

/**
 * 创建日期: 2021/2/3 on 10:18 AM
 * 描述:
 * 作者: 杨亮
 */
class BleApplication : BaseApplication(), BaseApplicationImpl {

    companion object {

        lateinit var context: Context

    }

    override fun onCreate(application: Application?, isDebug: Boolean) {
        context = application!!
        BluetoothContext.set(context)
    }
}
