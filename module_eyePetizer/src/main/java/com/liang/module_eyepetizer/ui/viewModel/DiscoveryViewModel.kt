package com.liang.module_eyepetizer.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.liang.module_eyepetizer.logic.EyeRepository
import com.liang.module_eyepetizer.logic.model.Item
import com.liang.module_eyepetizer.logic.network.DiscoveryApiService

/**
 * 创建日期: 2020/11/24 on 2:22 PM
 * 描述:
 * 作者: 杨亮
 */
class DiscoveryViewModel(repository: EyeRepository) : ViewModel() {

    //和界面相关的数据，定义在ViewModel中，可以保证它们在手机屏幕发生旋转的时候不会丢失，稍后在编写UI层代码的时候就会用到这几个变量
    var dataList = mutableListOf<Item>()

    private val requestParamLiveData = MutableLiveData<String>()

    var nextPageUrl: String? = null

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        repository.fetchDiscoveryData(url)
    }


    fun onRefresh() {
        requestParamLiveData.value = DiscoveryApiService.DISCOVERY_URL
    }

    fun onLoadMore() {
        requestParamLiveData.value = nextPageUrl ?: ""
    }

}
