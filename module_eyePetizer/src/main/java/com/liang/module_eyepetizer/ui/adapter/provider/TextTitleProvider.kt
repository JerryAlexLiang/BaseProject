package com.liang.module_eyepetizer.ui.adapter.provider

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.EyeConstant
import com.liang.module_eyepetizer.logic.model.Item

/**
 * 创建日期: 2020/12/3 on 11:04 AM
 * 描述: 文字标题栏布局 class TopBannerProvider() : BaseItemProvider<Item>() {
 * 作者: 杨亮
 */
class TextTitleProvider : BaseItemProvider<Item>() {

    override val itemViewType: Int
        get() = EyeConstant.IDisCoverItemType.TITLE_VIEW

    override val layoutId: Int
        get() = R.layout.eye_item_title_item_view

    override fun convert(helper: BaseViewHolder, item: Item) {
        helper.setText(R.id.tvTitle,item.data.text)
    }
}