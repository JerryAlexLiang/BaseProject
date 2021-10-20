package com.liang.module_gank.ui.nice_girl

import android.app.Activity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.widget.ViewPagerPictureActivity
import com.liang.module_gank.R
import com.liang.module_gank.logic.model.NewGirlRes
import kotlinx.android.synthetic.main.item_rv_new_girl.view.*

/**
 * 创建日期: 2021/10/20 on 2:07 PM
 * 描述: 新美女列表
 * 作者: 杨亮
 */
class NewGirlRvAdapter(layoutResId: Int) : BaseQuickAdapter<NewGirlRes, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: NewGirlRes) {
        Glide.with(context)
                .load(item.imageUrl)
                .apply(RequestOptions().apply {
                    override(Target.SIZE_ORIGINAL)
                    placeholder(R.drawable.core_icon_bg_header)
                    error(R.drawable.core_icon_error)
                })
                .into(holder.itemView.ivGirlNew)

        //点击图片
        holder.itemView.ivGirlNew.setOnClickListener {
            //跳转到大图显示界面
//            SinglePictureActivity.actionStart(context, item.url, item.desc)
            ViewPagerPictureActivity.actionStart(context, mutableListOf(item.imageUrl), mutableListOf(""))
            (context as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

//        //点击Card
//        holder.itemView.cardNiceGirlNew.setOnClickListener {
//            ToastUtil.showLongToast("")
//        }
    }
}