package com.liang.module_core.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 创建日期: 2021/1/28 on 5:58 PM
 * 描述: 封装单多选列表适配器，具体UI列表适配器只关注UI渲染
 * 作者: 杨亮
 */
abstract class BaseSelectListAdapter<T>(layoutResId: Int) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId), ISelectable<T> {

    private val selectedList: MutableList<T> = mutableListOf()

    //默认选择模式列表
    var isSelectModel: Boolean = true

    override fun convert(holder: BaseViewHolder, item: T) {
        if (isSelectModel) {
            doSelectModelBindViewHolder(holder, item)
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
        } else {
            //切换编辑模式，清空之前选中的Item列表
            selectedList.clear()
            doNormalModelBindViewHolder(holder, item)
        }
    }

    /**
     * 取消选中Item UI事件
     */
    open fun onItemViewUnSelected(holder: BaseViewHolder) {

    }

    /**
     * 选中Item UI事件
     */
    open fun onItemViewSelected(holder: BaseViewHolder) {

    }

    /**
     * 选择列表模式渲染UI赋值
     */
    open fun doSelectModelBindViewHolder(holder: BaseViewHolder, item: T) {

    }

    /**
     * 普通列表模式渲染UI赋值
     */
    open fun doNormalModelBindViewHolder(holder: BaseViewHolder, item: T) {

    }

    /**
     * 切换选择模式
     * 当前布局是否是选择模式
     * true: 是选择模式；false：普通模式
     */
    override fun toggleSelected(isCanSelect: Boolean) {
        this.isSelectModel = isCanSelect
        notifyDataSetChanged()
    }

    /**
     * 全选
     */
    override fun selectAll(allDataList: MutableList<T>) {
        if (isSelectModel) {
            selectedList.clear()
            selectedList.addAll(allDataList)
            notifyDataSetChanged()
        }
    }

    /**
     * 全不选
     */
    override fun deselectAll() {
        if (isSelectModel) {
            selectedList.clear()
            notifyDataSetChanged()
        }
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