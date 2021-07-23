package com.liang.model_middleware.service

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 创建日期: 2021/7/23 on 4:42 PM
 * 描述:
 * 作者: 杨亮
 */
interface GankModuleService : IProvider {

    //获取新版颜如玉Fragment
    fun initNewGankGirlFragment(): Fragment
}