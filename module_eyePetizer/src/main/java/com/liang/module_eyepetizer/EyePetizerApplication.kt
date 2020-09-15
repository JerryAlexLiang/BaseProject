package com.liang.module_eyepetizer

import android.app.Application
import android.content.Context
import com.liang.model_middleware.app.BaseApplicationImpl
import com.liang.module_core.app.BaseApplication

/**
 * 创建日期: 2020/9/14 on 7:30 PM
 * 描述:
 * 作者: 杨亮
 */
class EyePetizerApplication : BaseApplication(), BaseApplicationImpl {

    companion object {

        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onCreate(application: Application?, isDebug: Boolean) {
    }
}