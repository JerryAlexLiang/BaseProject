package com.liang.module_laboratory.jetpack

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_core.utils.ToastUtil
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.fragment_bottom_two.*
import kotlinx.android.synthetic.main.fragment_navigation_one.*
import kotlinx.android.synthetic.main.fragment_navigation_one.baseActionBarWidget


class BottomTwoFragment : MVVMBaseFragment() {

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
        return R.layout.fragment_bottom_two
    }

    override fun initView(rootView: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ToastUtil.showShortToast("Two")

        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(false, false, false,
                true, false, false, false)
        baseActionBarWidget.setActionBarTitle(context, "资讯", R.color.colorWhite)
        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)

        tvBtnJump.setOnClickListener {
            findNavController().navigate(R.id.action_bottom_navigation_two_to_bottomNavigationJumpSecondFragment)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = BottomTwoFragment()
    }
}