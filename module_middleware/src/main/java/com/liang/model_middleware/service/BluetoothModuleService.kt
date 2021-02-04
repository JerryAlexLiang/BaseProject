package com.liang.model_middleware.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 创建日期: 2021/2/2 on 3:15 PM
 * 描述:
 * 作者: 杨亮
 */
interface BluetoothModuleService : IProvider {

    fun startBluetoothMainActivity(context: Context)
}
