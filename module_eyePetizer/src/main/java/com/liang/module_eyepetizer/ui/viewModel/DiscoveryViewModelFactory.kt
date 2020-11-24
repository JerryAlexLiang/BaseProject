package com.liang.module_eyepetizer.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.liang.module_eyepetizer.logic.EyeRepository

/**
 * 创建日期: 2020/11/24 on 2:45 PM
 * 描述:
 * 作者: 杨亮
 */
class DiscoveryViewModelFactory(private val repository: EyeRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoveryViewModel(repository) as T
    }
}