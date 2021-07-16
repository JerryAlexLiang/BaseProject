package com.liang.module_laboratory.jetpack

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.ToastUtil
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.activity_jetpack_demo.*

/**
 * 创建日期:2021/7/15 on 5:40 PM
 * 描述: JetPack - Navigation
 * 作者: 杨亮
 */
class NavigationDemoActivity : MVVMBaseActivity() {

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
        return R.layout.activity_navigation_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
//        baseActionBarWidget.setActionBarHeight(100)
//        baseActionBarWidget.initViewsVisible(true, false, false,
//                true, false, false, false)
////        baseActionBarWidget.setActionBarTitle("Navigation", ContextCompat.getColor(this, R.color.colorWhite))
//        baseActionBarWidget.setActionBarTitle(this, "Navigation", R.color.colorWhite)
//
//        baseActionBarWidget.setOnLeftButtonClickListener {
//            finish()
//        }
//
//        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)
    }
}