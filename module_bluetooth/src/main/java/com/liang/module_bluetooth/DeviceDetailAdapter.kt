package com.liang.module_bluetooth

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.liang.module_bluetooth.provider.DeviceProfileAdapter
import com.liang.module_bluetooth.provider.DeviceServiceAdapter

/**
 * 创建日期: 2021/2/3 on 2:58 PM
 * 描述:
 * 作者: 杨亮
 */
class DeviceDetailAdapter : BaseProviderMultiAdapter<DetailItem>() {

    init {
        addItemProvider(DeviceServiceAdapter())
        addItemProvider(DeviceProfileAdapter())
    }

    companion object {
        const val TYPE_SERVICE = 0
        const val TYPE_CHARACTER = 1
        const val TYPE_SERVICE_ITEM = 2
        const val TYPE_CHARACTER_ITEM = 3
    }

    override fun getItemType(data: List<DetailItem>, position: Int): Int {
        return when (data[position].type) {
            TYPE_SERVICE -> {
                TYPE_SERVICE_ITEM
            }
            TYPE_CHARACTER -> {
                TYPE_CHARACTER_ITEM
            }
            else -> {
                0
            }
        }
    }
}