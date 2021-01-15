package com.liang.module_core.utils

import android.view.View

/**
 * 创建日期: 2020/9/16 on 10:54 AM
 * 描述:
 * 作者: 杨亮
 */

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param block 处理点击事件回调代码块
 */
fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
    val listener = View.OnClickListener { it.block() }
    v.forEach { it?.setOnClickListener(listener) }
}

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param listener 处理点击事件监听器
 */
fun setOnClickListener(vararg v: View?, listener: View.OnClickListener) {
    v.forEach { it?.setOnClickListener(listener) }
}

/**
 * 扩展视图可见性
 */
fun View.setVisible(visible: Int){
    this.visibility = if (visible == 0) View.VISIBLE else View.GONE
}
