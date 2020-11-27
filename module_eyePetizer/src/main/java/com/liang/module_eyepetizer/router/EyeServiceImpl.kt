package com.liang.module_eyepetizer.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.EyeModuleService
import com.liang.module_eyepetizer.ui.activity.EyePetizerActivity
import com.liang.module_eyepetizer.ui.activity.EyeSplashActivity
import org.jetbrains.anko.startActivity

/**
 * 创建日期: 2020/9/14 on 8:07 PM
 * 描述:通过依赖注入解耦:服务管理
 * 实现接口
 * 作者: 杨亮
 */
@Route(path = AppRouter.MODULE_EYE_PETIZER)
class EyeServiceImpl : EyeModuleService {

    override fun startEyePetizerActivity(context: Context) {
        context.startActivity<EyePetizerActivity>()
//        EyeSplashActivity.actionStart(context)

    }

    override fun init(context: Context?) {

    }

}
