package com.liang.module_laboratory.router

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.TestLaboratoryService
import com.liang.module_laboratory.TestLaboratoryActivity
import org.jetbrains.anko.startActivity

/**
 * 创建日期: 2021/1/15 on 2:46 PM
 * 描述: 通过依赖注入解耦:服务管理 实现接口
 * 作者: 杨亮
 */
@Route(path = AppRouter.MODULE_TEST_LABORATORY)
internal class TestLaboratoryServiceImpl : TestLaboratoryService {

    override fun startTestLaboratoryActivity(context: Context) {
        context.startActivity<TestLaboratoryActivity>()
//        context.startActivity(Intent(context, TestLaboratoryActivity::class.java))
    }

    override fun init(context: Context?) {
    }

}
