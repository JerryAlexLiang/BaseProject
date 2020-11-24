package com.liang.module_eyepetizer.logic.network

import com.liang.module_eyepetizer.logic.EyeRepository
import com.liang.module_eyepetizer.ui.viewModel.DiscoveryViewModelFactory

/**
 * 创建日期: 2020/11/24 on 2:35 PM
 * 描述:
 * 作者: 杨亮
 */
object InjectorUtil {

    private fun getEyeRepository(): EyeRepository {
        return EyeRepository.getInstance(EyeNetwork)
    }

//    private fun getEyeRepository() = EyeRepository.getInstance(EyeNetwork)

    fun getDiscoveryViewModelFactory() = DiscoveryViewModelFactory(getEyeRepository())
}