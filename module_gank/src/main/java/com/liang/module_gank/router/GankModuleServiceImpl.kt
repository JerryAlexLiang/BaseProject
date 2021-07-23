package com.liang.module_gank.router

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.GankModuleService
import com.liang.module_gank.ui.nice_girl.NewGankGirlFragment

/**
 * 创建日期: 2021/7/23 on 4:45 PM
 * 描述: Gank新版api
 * 作者: 杨亮
 */
@Route(path = AppRouter.MODULE_GANK_NICE_GIRLS)
class GankModuleServiceImpl : GankModuleService {

    override fun initNewGankGirlFragment(): Fragment {
        return NewGankGirlFragment.newInstance()
    }

    override fun init(context: Context?) {

    }

}