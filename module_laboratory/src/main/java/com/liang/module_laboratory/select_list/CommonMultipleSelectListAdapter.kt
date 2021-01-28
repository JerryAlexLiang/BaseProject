package com.liang.module_laboratory.select_list

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.adapter.BaseSelectListAdapter
import com.liang.module_laboratory.R

/**
 * 创建日期: 2021/1/28 on 5:48 PM
 * 描述:
 * 作者: 杨亮
 */
class CommonMultipleSelectListAdapter : BaseSelectListAdapter<BookBean>(R.layout.lab_rv_select_list_item) {

    override fun doBindViewHolder(holder: BaseViewHolder, item: BookBean) {
        holder.setText(R.id.tvName, item.name);
    }

    override fun onItemViewUnSelected(holder: BaseViewHolder) {
        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_uncheck)
    }

    override fun onItemViewSelected(holder: BaseViewHolder) {
        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_checked_multi);
    }

    override val isMultipleSelectModel: Boolean
        get() = true
}
