package com.liang.module_eyepetizer.logic.model

import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity

/**
 * 创建日期: 2020/9/15 on 5:09 PM
 * 描述: 与CommonTabLayout搭配使用的实体类
 * 作者: 杨亮
 */
class TabEntity(private var title: String, private var selectedIcon: Int = 0, private var unSelectedIcon: Int = 0) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

}