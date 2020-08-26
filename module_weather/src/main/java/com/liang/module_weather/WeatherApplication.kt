package com.liang.module_weather

import android.app.Application
import android.content.Context
import com.liang.module_core_java.app.BaseApplication
import com.liang.model_middleware.app.BaseApplicationImpl

/**
 * 创建日期: 2020/8/26 on 2:05 PM
 * 描述:
 * 作者: 杨亮
 */
class WeatherApplication : BaseApplication(), BaseApplicationImpl {

    companion object {

        //彩云天气API - 申请的令牌值
        const val WEATHER_API_TOKEN = "V7I0xO493GuCeYpm"

        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onCreate(application: Application?, isDebug: Boolean) {
    }
}