package com.liang.model_middleware.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 创建日期: 2020/8/24 on 10:42 AM
 * 描述:
 * 作者: 杨亮
 */
interface WeatherModuleService : IProvider {

    fun startWeatherActivity(context: Context)
}