package com.liang.module_laboratory.select_list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.lab_rv_select_list_item.view.*
import kotlin.collections.contains as contains1

/**
 * 创建日期: 2021/1/19 on 11:17 AM
 * 描述: 多选列表适配器
 * 作者: 杨亮
 */
class MultipleSelectListAdapter(layoutResId: Int) : BaseQuickAdapter<BookBean, BaseViewHolder>(layoutResId) {

    private var selectDataList: MutableList<BookBean> = mutableListOf()

    override fun convert(holder: BaseViewHolder, item: BookBean) {
        holder.setText(R.id.tvName, item.name)

        holder.itemView.setOnClickListener {
            //多选
            if (selectDataList.contains(item)) {
                selectDataList.remove(item)
            } else {
                selectDataList.add(item)
            }
            notifyDataSetChanged()
        }

        if (selectDataList.contains(item)) {
            holder.itemView.ivSelectContainer.setImageResource(R.drawable.ui_common_checked_multi)
        } else {
            holder.itemView.ivSelectContainer.setImageResource(R.drawable.ui_common_uncheck)
        }
    }

    fun setDefaultSelectList(defaultSelectList: MutableList<BookBean>) {
        //多选
        for (bookBean in defaultSelectList) {
            if (!selectDataList.contains(bookBean)) {
                selectDataList.add(bookBean)
            }
        }
        notifyDataSetChanged()
    }

    fun getSelectedList(): List<BookBean?>? {
        return selectDataList
    }
}