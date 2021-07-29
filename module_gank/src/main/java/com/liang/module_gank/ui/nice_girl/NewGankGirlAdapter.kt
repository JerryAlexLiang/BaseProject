package com.liang.module_gank.ui.nice_girl

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.widget.ViewPagerPictureActivity
import com.liang.module_gank.R
import com.liang.module_gank.logic.model.GankGirlRes
import kotlinx.android.synthetic.main.item_rv_new_nice_gank.view.*


/**
 * 创建日期: 2021/7/27 on 2:17 PM
 * 描述:
 * 作者: 杨亮
 */
class NewGankGirlAdapter(layoutResId: Int) : BaseQuickAdapter<GankGirlRes, BaseViewHolder>(layoutResId) {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: GankGirlRes) {
        holder.itemView.tvNiceGank.text = item.desc ?: "desc"
        holder.itemView.tvNiceSource.text = "nice: ${item.author}"

//        Glide.with(context).load(item.url).into(holder.itemView.ivNiceGank)

//        val iv: VerticalScrollParallaxImageView = holder.getView(R.id.ivNiceGank)
//        Glide.with(context).load(item.url).into(iv)

        Glide.with(context)
                .load(item.url)
                .apply(RequestOptions().apply {
                    override(Target.SIZE_ORIGINAL)
                    placeholder(R.drawable.core_icon_bg_header)
                    error(R.drawable.core_icon_error)
                })
                .into(holder.itemView.ivNiceGank)

//        Glide.with(context)
//                .load(item.url)
//                .apply(RequestOptions().apply {
//                    override(Target.SIZE_ORIGINAL)
//                    placeholder(R.drawable.core_icon_bg_header)
//                    error(R.drawable.core_icon_error)
//                })
//                .into(object : CustomTarget<Drawable?>() {
//                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
//                        holder.itemView.llContainer.background = resource
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {
//                    }
//
//                })

        //点击图片
        holder.itemView.ivNiceGank.setOnClickListener {
            //跳转到大图显示界面
//            SinglePictureActivity.actionStart(context, item.url, item.desc)
            ViewPagerPictureActivity.actionStart(context, mutableListOf(item.url))
            (context as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        //点击Card
        holder.itemView.cardNiceGank.setOnClickListener {
            ToastUtil.showLongToast(item.desc)
        }

    }


}