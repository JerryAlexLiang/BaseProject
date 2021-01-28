package com.liang.module_core.adapter

/**
 * 创建日期: 2021/1/28 on 2:19 PM
 * 描述: 选择功能
 * 作者: 杨亮
 */
interface ISelectable<T> {

    /**
     * 当前布局是否是选择模式
     * @return true: 是选择模式；false：普通模式
     */
//    val isSelectModel: Boolean

    val isMultipleSelectModel: Boolean

//    /**
//     * 切换选择模式
//     */
//    fun toggleSelected()

    /**
     * 选中全部
     * @param allDataList 全部数据，因为分页加载，不一定是当前显示数据
     */
    fun selectAll(allDataList: MutableList<T>)

    /**
     * 取消全选
     */
    fun deselectAll()

    /**
     * 获取当前选中的列表数据
     * @return 选中列表
     */
    fun getSelectedList(): MutableList<T>

}
