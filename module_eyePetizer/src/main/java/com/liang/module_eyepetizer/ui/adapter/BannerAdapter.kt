package com.liang.module_eyepetizer.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.ItemX
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * 创建日期: 2020/11/25 on 7:15 PM
 * 描述:
 * 作者: 杨亮
 */
class BannerAdapter : BaseBannerAdapter<ItemX, BannerViewHolder>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.eye_item_banner_item_view
    }

    override fun createViewHolder(parent: ViewGroup, itemView: View?, viewType: Int): BannerViewHolder {
        return BannerViewHolder(itemView!!)
    }

    override fun onBind(holder: BannerViewHolder?, data: ItemX?, position: Int, pageSize: Int) {
        holder?.bindData(data, position, pageSize)
    }

}