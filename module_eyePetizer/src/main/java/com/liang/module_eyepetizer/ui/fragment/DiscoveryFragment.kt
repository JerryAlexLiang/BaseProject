package com.liang.module_eyepetizer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.network.InjectorUtil
import com.liang.module_eyepetizer.ui.viewModel.DiscoveryViewModel
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * 创建日期:2020/11/24 on 1:52 PM
 * 描述: 发现页面
 * 作者: 杨亮
 */
class DiscoveryFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getDiscoveryViewModelFactory()).get(DiscoveryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        smart_refresh_layout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        observe()
    }


    private fun observe() {

        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response != null) {
                viewModel.nextPageUrl = response.nextPageUrl
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
                viewModel.dataList.addAll(response.itemList)

                LogUtil.d("eye", "eye data ---> " + GsonUtils.toJson(viewModel.dataList))
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