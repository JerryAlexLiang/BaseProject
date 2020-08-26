package com.liang.module_weather.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.WeatherModuleService
import com.liang.module_weather.WeatherActivity
import org.jetbrains.anko.startActivity

/**
 * 创建日期: 2020/8/24 on 10:46 AM
 * 描述: 通过依赖注入解耦:服务管理
 * 实现接口
 * 作者: 杨亮
 */
@Route(path = AppRouter.MODULE_WEATHER_PATH)
class WeatherServiceImpl : WeatherModuleService {

    override fun startWeatherActivity(context: Context) {
        context.startActivity<WeatherActivity>()
//        WeatherActivity.actionStart(context)
    }

    override fun init(context: Context?) {
    }
}
