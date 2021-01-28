package com.liang.module_core.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

/**
 * 创建日期: 2021/1/28 on 5:58 PM
 * 描述: 封装单多选列表适配器，具体UI列表适配器只关注UI渲染
 * 作者: 杨亮
 */
abstract class BaseSelectListAdapter<T>(layoutResId: Int) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId), ISelectable<T> {

    private val selectedList: MutableList<T> = mutableListOf()

    override fun convert(holder: BaseViewHolder, item: T) {
        doBindViewHolder(holder, item)

        holder.itemView.setOnClickListener {
            if (isMultipleSelectModel) {
                //多选
                if (selectedList.contains(item)) {
                    selectedList.remove(item)
                } else {
                    selectedList.add(item)
                }
            } else {
                if (!selectedList.contains(item)) {
                    selectedList.clear()
                    selectedList.add(item)
                }
            }
            notifyDataSetChanged()
        }
        if (selectedList.contains(item)) {
            onItemViewSelected(holder)
        } else {
            onItemViewUnSelected(holder)
        }
    }

    protected abstract fun doBindViewHolder(holder: BaseViewHolder, item: T)

    protected abstract fun onItemViewUnSelected(holder: BaseViewHolder)

    protected abstract fun onItemViewSelected(holder: BaseViewHolder)

    /**
     * 全选
     */
    override fun selectAll(allDataList: MutableList<T>) {
        selectedList.clear()
        selectedList.addAll(allDataList)
        notifyDataSetChanged()
    }

    /**
     * 全不选
     */
    override fun deselectAll() {
        selectedList.clear()
        notifyDataSetChanged()
    }

    /**
     * 获取选中列表
     */
    override fun getSelectedList(): MutableList<T> {
        return selectedList
    }

    /**
     * 设置默认选中Item
     */
    fun setDefaultSelectList(defaultSelectList: List<T>) {
        if (isMultipleSelectModel) {
            //多选
            for (t in defaultSelectList) {
                if (!selectedList.contains(t)) {
                    selectedList.add(t)
                }
            }
        } else {
            //单选
            selectedList.clear()
            selectedList.addAll(defaultSelectList)
        }
        notifyDataSetChanged()
    }

}