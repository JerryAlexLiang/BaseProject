package com.liang.module_laboratory.jetpack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.activity_jetpack_demo.*

/**
 * 创建日期:2021/7/14 on 5:07 PM
 * 描述: JetPackDemo
 * 作者: 杨亮
 */
class JetpackDemoActivity : MVVMBaseActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, JetpackDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_jetpack_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        baseActionBarWidget.initViewsVisible(true, true, false,
                true, false, true, false)
        baseActionBarWidget.setActionBarTitle("JetPackDemo")

        baseActionBarWidget.setOnLeftButtonClickListener {
            ToastUtil.showShortToast("左侧按钮1")
            finish()
        }
        baseActionBarWidget.setOnLeft2ButtonClickListener {
            ToastUtil.showShortToast("左侧按钮2")
            finish()
        }
        baseActionBarWidget.setOnRightButtonClickListener {
            ToastUtil.showShortToast("右侧按钮")
        }

//        //Convert to lambda
//        baseActionBarWidget.setOnRightButtonClickListener(object :BaseActionBarWidget.OnRightButtonClickListener{
//            override fun onRightButtonClick(view: View?) {
//            }
//
//        })

//        baseActionBarWidget.setActionBarBackgroundColor(resources.getColor(R.color.red))
        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)

    }

    private fun initListener() {
        setOnClickListener(btnLifeCycleDemo) {
            when (this) {
                btnLifeCycleDemo -> {
                    LifeCycleDemoActivity.actionStart(this@JetpackDemoActivity)
                }

                else -> {

                }
            }
        }

    }


}