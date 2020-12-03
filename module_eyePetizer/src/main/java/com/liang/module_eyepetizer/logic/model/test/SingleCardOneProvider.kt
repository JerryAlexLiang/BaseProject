package com.liang.module_eyepetizer.logic.model.test

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.utils.ToastUtil
import com.liang.module_eyepetizer.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * 创建日期: 2020/11/30 on 1:59 PM
 * 描述:
 * 作者: 杨亮
 */
class SingleCardOneProvider : BaseItemProvider<BaseCustomViewModel>() {

    companion object {
        const val MultiCardBean = 1
        const val SingleCardOneBean = 2
        const val SingleCardTwoBean = 3
    }

    override val itemViewType: Int
        get() = SingleCardOneBean

    override val layoutId: Int
        get() = R.layout.single_card_one_view_item

    override fun convert(helper: BaseViewHolder, item: BaseCustomViewModel) {
        val singleCardOneBean:Test.SingleCardOneBean = item as Test.SingleCardOneBean
//        val imageView = helper.getView<ImageView>(R.id.ivOne)
        val imageView = helper.getView<CircleImageView>(R.id.ivOne)
        Glide.with(context).asBitmap().load(singleCardOneBean.iconUrl).into(imageView)

        helper.itemView.setOnClickListener {
            ToastUtil.showShortToast("原始顺序: " + item.sortIndex + "整理顺序: " + item.sort)
        }
    }

}