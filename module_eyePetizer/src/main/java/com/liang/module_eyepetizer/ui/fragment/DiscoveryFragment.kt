package com.liang.module_eyepetizer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.Data
import com.liang.module_eyepetizer.logic.model.EyeConstant
import com.liang.module_eyepetizer.logic.model.Item
import com.liang.module_eyepetizer.logic.model.ItemX
import com.liang.module_eyepetizer.logic.network.InjectorUtil
import com.liang.module_eyepetizer.ui.adapter.DiscoveryAdapter
import com.liang.module_eyepetizer.ui.viewModel.DiscoveryViewModel
import kotlinx.android.synthetic.main.fragment_discovery.*


/**
 * 创建日期:2020/11/24 on 1:52 PM
 * 描述: 发现页面
 * 作者: 杨亮
 */
class DiscoveryFragment : Fragment() {

    private lateinit var adapter: DiscoveryAdapter
//    private lateinit var adapter: DiscoveryAdapterJava

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getDiscoveryViewModelFactory()).get(DiscoveryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //确定Item的改变不会影响RecyclerView的宽高
        recyclerView.setHasFixedSize(true)
        val manager = LinearLayoutManager(context)
        recyclerView.layoutManager = manager

        smart_refresh_layout.autoRefresh()
        smart_refresh_layout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        adapter = DiscoveryAdapter(viewModel.dataList)
//        adapter = DiscoveryAdapterJava(viewModel.dataList)
        recyclerView.adapter = adapter

        observe()
    }


    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()

            if (response != null) {

                val nextPageUrl = response.nextPageUrl
                if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                    smart_refresh_layout.closeHeaderOrFooter()
                }

                if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                    smart_refresh_layout.finishLoadMoreWithNoMoreData()
                }

                if (response.nextPageUrl.isNullOrEmpty()) {
                    smart_refresh_layout.finishLoadMoreWithNoMoreData()
                } else {
                    smart_refresh_layout.closeHeaderOrFooter()
                }

                viewModel.dataList.clear()
//                viewModel.dataList.addAll(response.itemList)


                for (element in response.itemList) {
                    when (element.type) {
                        EyeConstant.IDisCoverItemReturnType.horizontalScrollCard -> {
                            viewModel.dataList.add(element)
                        }
                        EyeConstant.IDisCoverItemReturnType.specialSquareCardCollection -> {
                            viewModel.dataList.add(element)
                        }
                        EyeConstant.IDisCoverItemReturnType.textCard -> {
                            viewModel.dataList.add(element)
                        }
                    }
                }

//                viewModel.dataList.add(response.itemList[0])
//                viewModel.dataList.add(response.itemList[1])

                adapter.notifyDataSetChanged()

                LogUtil.d("eye", "eye data0 :  " + GsonUtils.toJson(viewModel.dataList[0].type))
                LogUtil.d("eye", "eye data1 :  " + GsonUtils.toJson(viewModel.dataList[1].type))

                for (index in 0 until adapter.itemCount) {
                    LogUtil.d("eye", "eye data index source:  " + index + "  " + GsonUtils.toJson(adapter.data[index].type))
                }
                LogUtil.d("eye", "eye data adapter :  " + adapter.data.size)
            } else {
                smart_refresh_layout.closeHeaderOrFooter()
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                DiscoveryFragment()
    }
}