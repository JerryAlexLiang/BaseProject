package com.liang.module_eyepetizer.ui.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.liang.module_core.utils.ToastUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.ItemX
import com.zhpan.bannerview.BaseViewHolder

/**
 * 创建日期: 2020/11/25 on 7:11 PM
 * 描述:
 * 作者: 杨亮
 */
class BannerImageViewHolder(itemView: View) : BaseViewHolder<ItemX>(itemView) {

    override fun bindData(data: ItemX?, position: Int, pageSize: Int) {
        val ivBanner: ImageView = findView(R.id.bannerBg)
        Glide.with(ivBanner).asBitmap().load(data!!.data.image).into(ivBanner)

        ivBanner.setOnClickListener {
            ToastUtil.showShortToast("" + data.data.title)
        }

    }

}