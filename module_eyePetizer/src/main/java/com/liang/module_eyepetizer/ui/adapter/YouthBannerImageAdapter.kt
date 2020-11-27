package com.liang.module_eyepetizer.ui.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.Data
import com.liang.module_eyepetizer.logic.model.ItemX
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * 创建日期: 2020/11/27 on 5:23 PM
 * 描述:
 * 作者: 杨亮
 */
class YouthBannerImageAdapter(data: Data) : BannerAdapter<ItemX, YouthBannerImageViewHolder>(data.itemList) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): YouthBannerImageViewHolder {
        return YouthBannerImageViewHolder(BannerUtils.getView(parent!!, R.layout.eye_item_banner_item_view))
    }

    override fun onBindView(holder: YouthBannerImageViewHolder?, data: ItemX?, position: Int, size: Int) {
        val ivBannerBg = holder!!.ivBannerBg
        Glide.with(ivBannerBg).asBitmap().load(data!!.data.image).into(ivBannerBg)
    }


}
