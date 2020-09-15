package com.liang.module_eyepetizer.ui.activity

import android.os.Bundle
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.ui.fragment.EyeHomeFragment

/**
 * 创建日期:2020/9/15 on 2:24 PM
 * 描述: 开眼视模块
 * 作者: 杨亮
 */
class EyePetizerActivity : MVVMBaseActivity() {

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
        return R.layout.activity_eye_petizer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        val eyeHomeFragment = EyeHomeFragment.newInstance()
        replaceFragment(eyeHomeFragment)
    }
}