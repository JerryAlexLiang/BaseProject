package com.liang.module_laboratory.select_list

import android.util.Log
import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.adapter.BaseSelectListAdapter
import com.liang.module_core.utils.ToastUtil
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.lab_rv_select_list_item.view.*

/**
 * 创建日期: 2021/1/28 on 5:48 PM
 * 描述: 基于封装的单多选列表适配器的多选切换模式列表Adapter
 * 作者: 杨亮
 */
class CommonMultipleSelectListAdapter : BaseSelectListAdapter<BookBean>(R.layout.lab_rv_select_list_item) {

    override val isMultipleSelectModel: Boolean
        get() = true

    fun setCanSelect(isCanSelect: Boolean) {
        toggleSelected(isCanSelect)
    }

    override fun onItemViewSelected(holder: BaseViewHolder) {
        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_checked_multi)
    }

    override fun onItemViewUnSelected(holder: BaseViewHolder) {
        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_uncheck)
    }

    override fun doBindViewHolder(holder: BaseViewHolder, item: BookBean) {
        holder.setText(R.id.tvName, item.name)
        val ivIcon = holder.itemView.ivIcon
        val tvName = holder.itemView.tvName

        Log.d("Jerry", "doSelectModelBindViewHolder:   $isSelectModel")
        if (isSelectModel) {

            //设置最大选中个数
            setMaxSelectCount(3)
//            setMaxSelectCount(data.size)

            Log.d("Jerry", "dataListSize:   " + data.size)

            //可选列表模式
            Log.d("Jerry", "doSelectModelBindViewHolder1:   $isSelectModel")
            holder.itemView.ivSelectContainer.visibility = View.VISIBLE

            ivIcon.isClickable = true
            tvName.isClickable = true
            ivIcon.setOnClickListener {
                ToastUtil.onShowDefaultToast(context, "当前点击: " + item.name)

                Log.d("Jerry", "doSelectModelBindViewHolder2:   $isSelectModel")
            }
            tvName.setOnClickListener {
                ToastUtil.onShowDefaultToast(context, "当前点击: " + item.name)

                Log.d("Jerry", "doSelectModelBindViewHolder3:   $isSelectModel")
            }

        } else {
            //普通类表模式
            Log.d("Jerry", "doSelectModelBindViewHolder4:   $isSelectModel")
            holder.itemView.ivSelectContainer.visibility = View.INVISIBLE
            ivIcon.isClickable = false
            tvName.isClickable = false
            holder.itemView.setOnClickListener {
                Log.d("Jerry", "doSelectModelBindViewHolder5:   $isSelectModel")
                ToastUtil.onShowSuccessRectangleToast(context, "当前点击: " + item.name)
            }
        }
    }
}
