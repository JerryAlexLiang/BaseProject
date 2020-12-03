package com.liang.module_eyepetizer.logic.model.test

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.utils.ToastUtil
import com.liang.module_eyepetizer.R

/**
 * 创建日期: 2020/11/30 on 3:03 PM
 * 描述:
 * 作者: 杨亮
 */
class SingleCardTwoProvider : BaseItemProvider<BaseCustomViewModel>() {

    companion object {
        const val MultiCardBean = 1
        const val SingleCardOneBean = 2
        const val SingleCardTwoBean = 3
    }

    override val itemViewType: Int
        get() = SingleCardTwoBean

    override val layoutId: Int
        get() = R.layout.single_card_two_view_item

    override fun convert(helper: BaseViewHolder, item: BaseCustomViewModel) {
        val singleCardTwoBean: Test.SingleCardTwoBean = item as Test.SingleCardTwoBean
        val imageView = helper.getView<ImageView>(R.id.ivTwo)
        Glide.with(context).asBitmap().load(singleCardTwoBean.iconUrl).into(imageView)

        helper.itemView.setOnClickListener {
            ToastUtil.showShortToast("原始顺序: " + item.sortIndex + "整理顺序: " + item.sort)
        }
    }
}
