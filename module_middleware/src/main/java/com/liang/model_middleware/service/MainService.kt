package com.liang.model_middleware.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 创建日期: 2021/1/18 on 1:49 PM
 * 描述:
 * 作者: 杨亮
 */
interface MainService : IProvider {

    fun getJuheNewsTabFragment(): Fragment

    fun openFiltrateActivity(context: Context)

    fun openMapTestActivity(context: Context)

    fun openCustomSimpleCameraActivity(context: Context)

    fun openServiceAIDLActivity(context: Context)

    fun openAgentWebActivityX5(context: Context, title: String, url: String)
}