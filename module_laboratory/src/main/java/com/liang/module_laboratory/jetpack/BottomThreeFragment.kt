package com.liang.module_laboratory.jetpack

import android.os.Bundle
import android.view.View
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.fragment_navigation_one.*

class BottomThreeFragment : MVVMBaseFragment() {

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_bottom_three
    }

    override fun initView(rootView: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(false, false, false,
                true, false, false, false)
        baseActionBarWidget.setActionBarTitle(context, "消息中心", R.color.colorWhite)
        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)
    }

    companion object {

        @JvmStatic
        fun newInstance() = BottomThreeFragment()
    }
}