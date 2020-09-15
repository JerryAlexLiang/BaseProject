package com.liang.module_core.jetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.liang.module_core.R
import java.util.ArrayList

/**
 * 创建日期: 2020/9/15 on 2:57 PM
 * 描述: Fragment基类，适用场景：页面含有ViewPager+TabLayout的界面。
 * 作者: 杨亮
 */
abstract class BaseViewPagerFragment : MVVMBaseFragment() {

    abstract val createFragments: Array<Fragment>
    abstract val createTitles: ArrayList<CustomTabEntity>
    private lateinit var pageChangeCallback: PageChangeCallback

    protected var tabLayout: CommonTabLayout? = null
    protected var viewPager2: ViewPager2? = null

    private var offscreenPageLimit = 1

    private val adapter by lazy { Vp2Adapter(getActivity()!!).apply { addFragments(createFragments) } }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    open fun setupViews() {
        initViewPager()
    }

    private fun initViewPager() {
        viewPager2 = rootView?.findViewById(R.id.viewPager2)!!
        tabLayout = rootView?.findViewById(R.id.commonTabLayout)!!

        viewPager2?.offscreenPageLimit = offscreenPageLimit
        viewPager2?.adapter = adapter

        tabLayout?.setTabData(createTitles)
        tabLayout?.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                viewPager2?.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })
        pageChangeCallback = PageChangeCallback()
        viewPager2?.registerOnPageChangeCallback(pageChangeCallback)
    }

    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tabLayout?.currentTab = position
        }
    }

    inner class Vp2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        pageChangeCallback.run { viewPager2?.unregisterOnPageChangeCallback(this) }
    }

}