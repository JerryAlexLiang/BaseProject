package com.liang.module_laboratory.jetpack

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.fragment_navigation_one.*


class NavigationOneFragment : MVVMBaseFragment() {


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
        return R.layout.fragment_navigation_one
    }

    override fun initView(rootView: View?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(true, false, false,
                true, false, false, false)
        baseActionBarWidget.setActionBarTitle(context, "Navigation1", R.color.colorWhite)

        baseActionBarWidget.setOnLeftButtonClickListener {
            activity.finish()
        }

        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)

        btnJump2.setOnClickListener {
//            //未采用safe args的参数传递方式
//            val bundle = Bundle()
//            bundle.putString("key_one", "Jerry")
//            bundle.putInt("key_two", 100)

            //通过safe args完成参数传递
            val bundle = NavigationOneFragmentArgs.Builder().setKeyOne("肖战").setKeyTwo(100).build().toBundle()

//            //Navigation跳转方式1: 有动画效果
            findNavController().navigate(R.id.action_navigationOneFragment_to_navigationTwoFragment, bundle)
//            Navigation.findNavController(it).navigate(R.id.action_navigationOneFragment_to_navigationTwoFragment, bundle)
            //Navigation跳转方式2:   无动画效果
//            findNavController().navigate(R.id.navigationTwoFragment, bundle)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NavigationOneFragment()
    }
}