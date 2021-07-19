package com.liang.module_laboratory

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.liang.module_core.jetpack.MVVMBaseFragment
import kotlinx.android.synthetic.main.fragment_navigation_one.*


class BottomOneFragment : MVVMBaseFragment() {

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
        return R.layout.fragment_bottom_one
    }

    override fun initView(rootView: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(false, false, false,
                true, false, false, false)
        baseActionBarWidget.setActionBarTitle(context, "首页", R.color.colorWhite)
        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)
    }

    companion object {

        @JvmStatic
        fun newInstance() = BottomOneFragment()
    }
}