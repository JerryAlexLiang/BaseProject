package com.liang.module_laboratory.select_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.fragment_select_list.*

/**
 * 创建日期:2021/1/19 on 11:13 AM
 * 描述: 单选列表
 * 作者: 杨亮
 */
class SingleSelectListFragment : MVVMBaseFragment() {

    private lateinit var dataList: MutableList<BookBean>
    private lateinit var defaultSelectDataList: MutableList<BookBean>
    private lateinit var adapter: SingleSelectListAdapter

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_select_list
    }

    override fun initView(rootView: View?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()

        val linearLayoutManager = LinearLayoutManager(context)
        rvSelectList.layoutManager = linearLayoutManager

        adapter = SingleSelectListAdapter(R.layout.lab_rv_select_list_item)
        rvSelectList.adapter = adapter
        adapter.addData(dataList)

        initListener()
    }

    private fun initListener() {
        setOnClickListener(btnCheck, btnCheck2) {
            when (this) {
                btnCheck -> {
                    val selectedList = adapter.getSelectedList()
                    Log.d(TAG, "initListener: " + GsonUtils.toJson(selectedList))
                }

                btnCheck2 -> {
                    defaultSelectDataList.clear()
                    defaultSelectDataList.add(dataList[6])
                    adapter.setDefaultSelectList(defaultSelectDataList)
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
                SingleSelectListFragment()
    }
}