package com.liang.model_middleware.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 创建日期: 2/18/22 on 2:09 PM
 * 描述: 3D旋转汽车模型
 * 作者: 杨亮
 */
interface Card3DModuleService : IProvider {

    fun startCard3DActivity(context: Context)
}