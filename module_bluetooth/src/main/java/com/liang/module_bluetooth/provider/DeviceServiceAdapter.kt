package com.liang.module_bluetooth.provider

import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_bluetooth.DetailItem
import com.liang.module_bluetooth.R
import kotlinx.android.synthetic.main.ble_device_detail_item.view.*
import java.lang.String

/**
 * 创建日期: 2021/2/3 on 3:52 PM
 * 描述:
 * 作者: 杨亮
 */
class DeviceServiceAdapter : BaseItemProvider<DetailItem>() {

    companion object {
        const val TYPE_SERVICE = 0
        const val TYPE_CHARACTER = 1
        const val TYPE_SERVICE_ITEM = 2
        const val TYPE_CHARACTER_ITEM = 3
    }

    override val itemViewType: Int
        get() = TYPE_SERVICE_ITEM

    override val layoutId: Int
        get() = R.layout.ble_device_detail_item

    override fun convert(helper: BaseViewHolder, item: DetailItem) {
        helper.itemView.root.setBackgroundColor(ContextCompat.getColor(context, R.color.device_detail_service))
        helper.itemView.uuid.paint.isFakeBoldText = true
        helper.itemView.uuid.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
        helper.itemView.uuid.text = String.format("服务Service: %s", item.uuid.toString().toUpperCase())
    }
}