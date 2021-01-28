package com.liang.module_laboratory.select_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.fragment_select_list.*
import kotlinx.android.synthetic.main.lab_fab_menu_layout.*

/**
 * 创建日期:2021/1/19 on 11:13 AM
 * 描述: 单选列表
 * 作者: 杨亮
 */
class CommonSingleSelectListFragment : MVVMBaseFragment() {

    private lateinit var dataList: MutableList<BookBean>
    private lateinit var defaultSelectDataList: MutableList<BookBean>
    private lateinit var adapter: CommonSingleSelectListAdapter

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_select_list
    }

    override fun initView(rootView: View?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()

        fabMenuOne.visibility = View.VISIBLE
        fabMenuTwo.visibility = View.VISIBLE
        fabMenuOne.labelText = "打印选中"
        fabMenuTwo.labelText = "设置默认选中"

        val linearLayoutManager = LinearLayoutManager(context)
        rvSelectList.layoutManager = linearLayoutManager

        adapter = CommonSingleSelectListAdapter()
        rvSelectList.adapter = adapter
        adapter.addData(dataList)

        initListener()
    }

    private fun initListener() {
        setOnClickListener(btnCheck, btnCheck2, fabMenuOne, fabMenuTwo) {
            when (this) {
                btnCheck -> {
                    val selectedList = adapter.selectedList
                    Log.d(TAG, "initListener: " + GsonUtils.toJson(selectedList))
                }

                btnCheck2 -> {
                    defaultSelectDataList.clear()
                    defaultSelectDataList.add(dataList[6])
                    adapter.setDefaultSelectList(defaultSelectDataList)
                }

                fabMenuOne -> {
                    val selectedList = adapter.selectedList
                    Log.d(TAG, "initListener: " + GsonUtils.toJson(selectedList))
                    ToastUtil.showShortToast(GsonUtils.toJson(selectedList))

                    fabMenu.toggle(false)
                }

                fabMenuTwo -> {
                    defaultSelectDataList.clear()
                    defaultSelectDataList.add(dataList[6])
                    adapter.setDefaultSelectList(defaultSelectDataList)

                    fabMenu.toggle(false)
                }

            }
        }
    }

    private fun initData() {
        dataList = mutableListOf()
        defaultSelectDataList = mutableListOf()

        dataList.add(BookBean("微微一笑很倾城", 1))
        dataList.add(BookBean("三国演义", 2))
        dataList.add(BookBean("水浒传", 3))
        dataList.add(BookBean("西游记", 4))
        dataList.add(BookBean("红楼梦", 5))
        dataList.add(BookBean("天官赐福", 6))
        dataList.add(BookBean("我本浪人，游吟世间", 7))
        dataList.add(BookBean("海底两万里", 8))
        dataList.add(BookBean("Android高级进阶", 9))
        dataList.add(BookBean("Flutter开发指南", 10))
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


    companion object {

        @JvmStatic
        fun newInstance() =
                CommonSingleSelectListFragment()
    }
}