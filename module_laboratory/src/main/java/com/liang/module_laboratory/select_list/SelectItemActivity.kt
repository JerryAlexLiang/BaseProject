package com.liang.module_laboratory.select_list

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.jetpack.utils.dp2px
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_laboratory.R
import com.liang.module_ui.adapter.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.activity_select_item.*
import kotlinx.android.synthetic.main.lab_layout_base_actionbar_default.*

/**
 * 创建日期:2021/1/18 on 4:57 PM
 * 描述: 单选多选列表
 * 作者: 杨亮
 */
class SelectItemActivity : MVVMBaseActivity() {

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
        return R.layout.activity_select_item
    }

    private val stringList: MutableList<String> = mutableListOf()
    private val fragmentList: MutableList<Fragment> = mutableListOf()

    private var mFragmentContent: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        initView()

        initListener()
    }

    private fun initListener() {
        setOnClickListener(base_actionbar_left_icon) {
            when (this) {
                base_actionbar_left_icon -> {
                    finish()
                }

                else -> {
                }
            }
        }
    }

    private fun initView() {
        base_actionbar_left_icon!!.visibility = View.VISIBLE
        base_actionbar_title!!.visibility = View.VISIBLE
        base_actionbar_title!!.text = "单选多选列表"

        //初始化适配器
        val fragmentViewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager, fragmentList, stringList)
        //设置至少9个fragment，防止重复创建和销毁，造成内存溢出
        viewPager.offscreenPageLimit = 3
        //绑定适配器
        //绑定适配器
        viewPager.adapter = fragmentViewPagerAdapter
        //TabLayout属性设置
        //设置下划线指示器圆角
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = dp2px(this, 2f).toFloat()
        tabLayout.setSelectedTabIndicator(gradientDrawable)
        //TabLayout同步fragment
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initData() {
        stringList.add("单选列表")
        stringList.add("多选列表")

        fragmentList.add(SingleSelectListFragment.newInstance())
        fragmentList.add(MultipleSelectListFragment.newInstance())
    }

    companion object {

        fun actionStart(context: Context) {
            val intent = Intent(context, SelectItemActivity::class.java)
            context.startActivity(intent)
        }
    }
}