package com.liang.module_card3d.router

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.Card3DModuleService
import com.liang.module_card3d.ui.Card3DRotateActivity
import com.liang.module_card3d.ui.Card3DRotateActivityJava

/**
 * 创建日期: 2/18/22 on 2:13 PM
 * 描述: 3D汽车模型旋转
 * 作者: 杨亮
 */

@Route(path = AppRouter.MODULE_CARD_3D)
class Card3DModuleServiceImpl : Card3DModuleService {

    override fun startCard3DActivity(context: Context) {
        Card3DRotateActivity.actionStart(context)
//        Card3DRotateActivityJava.actionStart(context)
    }

    override fun init(context: Context?) {
    }

}
