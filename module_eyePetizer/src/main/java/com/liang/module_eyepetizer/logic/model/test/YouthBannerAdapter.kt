package com.liang.module_eyepetizer.logic.model.test

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.ui.adapter.YouthBannerImageViewHolder
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * 创建日期: 2020/11/27 on 5:23 PM
 * 描述:
 * 作者: 杨亮
 */

class YouthBannerAdapter(data: MutableList<Test.MultiCardBean.BannerBean>):BannerAdapter<Test.MultiCardBean.BannerBean,YouthBannerImageViewHolder>(data){

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): YouthBannerImageViewHolder {
        return YouthBannerImageViewHolder(BannerUtils.getView(parent!!, R.layout.eye_item_banner_item_view))
    }

    override fun onBindView(holder: YouthBannerImageViewHolder?, data: Test.MultiCardBean.BannerBean?, position: Int, size: Int) {
        val ivBannerBg = holder!!.ivBannerBg
        Glide.with(ivBannerBg).asBitmap().load(data!!.iconUrl).into(ivBannerBg)
    }

}
