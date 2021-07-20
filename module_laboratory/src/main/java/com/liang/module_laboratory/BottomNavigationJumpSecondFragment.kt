package com.liang.module_laboratory

import android.os.Bundle
import android.view.View
import com.liang.module_core.jetpack.MVVMBaseFragment

class BottomNavigationJumpSecondFragment : MVVMBaseFragment() {

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
        return R.layout.fragment_bottom_navigation_jump_second
    }

    override fun initView(rootView: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {

        @JvmStatic
        fun newInstance() = BottomNavigationJumpSecondFragment()
    }
}