package com.liang.module_laboratory.jetpack.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

/**
 * 创建日期: 2021/7/22 on 3:02 PM
 * 描述:
 * 作者: 杨亮
 */
class PagingViewModel : ViewModel() {

    /**
     * 这里又额外调用了一个cachedIn()函数，这是用于将服务器返回的数据在viewModelScope这个作用域内进行缓存，
     * 假如手机横竖屏发生了旋转导致Activity重新创建，Paging3就可以直接读取缓存中的数据，而不用重新发起网络请求了
     */
    fun getPagingData(): Flow<PagingData<PagingBean.ItemsBean>> {
        return PagingRepository.getPagingData().cachedIn(viewModelScope)
    }

    fun getPagingData2(): Flow<PagingData<ArticleBean>> {
        return PagingRepository.getPagingData2().cachedIn(viewModelScope)
    }
}