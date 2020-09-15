package com.liang.module_eyepetizer.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.liang.module_core.jetpack.BaseViewPagerFragment
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.TabEntity
import kotlinx.android.synthetic.main.eye_layout_base_actionbar_default.*
import java.util.*


class EyeHomeFragment : BaseViewPagerFragment() {

    override val createTitles: ArrayList<CustomTabEntity>
        get() = ArrayList<CustomTabEntity>().apply {
            add(TabEntity("推荐"))
            add(TabEntity("发现"))
        }


    override val createFragments: Array<Fragment>
        get() = arrayOf(CommendFragment.newInstance(), DiscoveryFragment.newInstance())


    override fun createViewLayoutId(): Int {
        return R.layout.fragment_eye_home
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

    override fun initView(rootView: View?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //随系统设置更改ToolBar状态栏颜色
//        getActionBarTheme(null, toolbarLayout)
        commonTabLayout.visibility = View.VISIBLE
        viewPager2?.currentItem = 1
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment = EyeHomeFragment()
    }
}