package com.liang.module_laboratory.jetpack

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.fragment_navigation_one.baseActionBarWidget
import kotlinx.android.synthetic.main.fragment_navigation_two.*

class NavigationTwoFragment : MVVMBaseFragment() {


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
        return R.layout.fragment_navigation_two
    }

    override fun initView(rootView: View?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(true, false, false,
                true, false, false, false)
        baseActionBarWidget.setActionBarTitle(context, "Navigation2", R.color.colorWhite)

        baseActionBarWidget.setOnLeftButtonClickListener {
            findNavController().popBackStack()
        }

        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)

//        //未采用safe args的数据接收方式
//        val arguments = arguments
//        if (arguments != null) {
//            val name = arguments.getString("key_one")
//            val score = arguments.getInt("key_two")
//
//            tvTwoContent.append("\n");
//            tvTwoContent.append("name: $name   分数: $score")
//        }

        //采用safe args的数据接收方式
        val arguments = arguments
        if (arguments != null) {
            val name = NavigationOneFragmentArgs.fromBundle(arguments).keyOne
            val score = NavigationOneFragmentArgs.fromBundle(arguments).keyTwo

            tvTwoContent.append("\n");
            tvTwoContent.append("name: $name   分数: $score")

        }

        btnJumpBottomNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_navigationTwoFragment_to_bottomNavigationActivity)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = NavigationTwoFragment()
    }
}