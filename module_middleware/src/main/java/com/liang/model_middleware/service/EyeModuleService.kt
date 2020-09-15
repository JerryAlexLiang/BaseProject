package com.liang.model_middleware.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 创建日期: 2020/9/15 on 10:10 AM
 * 描述:
 * 作者: 杨亮
 */
interface EyeModuleService : IProvider {

    fun startEyePetizerActivity(context: Context)
}
