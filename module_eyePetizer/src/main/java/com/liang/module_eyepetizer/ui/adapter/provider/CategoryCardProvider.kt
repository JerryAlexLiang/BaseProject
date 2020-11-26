package com.liang.module_eyepetizer.ui.adapter.provider

import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.utils.ImageLoaderUtils
import com.liang.module_core.jetpack.utils.SpecialSquareCardCollectionItemDecoration
import com.liang.module_core.jetpack.utils.load
import com.liang.module_core.utils.ToastUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.Item
import com.liang.module_eyepetizer.logic.model.ItemX
import kotlinx.android.synthetic.main.eye_category_item_card_view.view.*
import kotlinx.android.synthetic.main.eye_category_item_child_view.view.*

/**
 * 创建日期: 2020/11/26 on 5:02 PM
 * 描述: 热门分类
 * 作者: 杨亮
 */
class CategoryCardProvider(override val itemViewType: Int, override val layoutId: Int) : BaseItemProvider<Item>() {

    override fun convert(helper: BaseViewHolder, item: Item) {
        helper.setText(R.id.tv_title, item.data.header.title)
        helper.setText(R.id.tv_action_title, item.data.header.rightText)
        helper.itemView.tv_action_title.setOnClickListener {
            ToastUtil.showShortToast(item.data.header.title + " " + item.data.header.rightText)
        }
        val rvCategory = helper.itemView.rvCategory
        rvCategory.layoutManager = GridLayoutManager(context, 2).apply {
            orientation = GridLayoutManager.HORIZONTAL
        }
        if (rvCategory.itemDecorationCount == 0) {
            rvCategory.addItemDecoration(SpecialSquareCardCollectionItemDecoration())
        }
        val dataList = item.data.itemList.filter {
            it.type == "squareCardOfCategory" && it.data.dataType == "SquareCard"
        }
        val categoryItemAdapter = CategoryItemAdapter(R.layout.eye_category_item_child_view)
        rvCategory.adapter = categoryItemAdapter
        categoryItemAdapter.setNewInstance(dataList as MutableList<ItemX>)
    }

    inner class CategoryItemAdapter(layoutResId: Int) : BaseQuickAdapter<ItemX, BaseViewHolder>(layoutResId) {
        override fun convert(holder: BaseViewHolder, item: ItemX) {
            holder.setText(R.id.tvTitle, item.data.title)
//            Glide.with(holder.itemView.ivPicture).asBitmap().load(item.data.image).into(holder.itemView.ivPicture)
//            ImageLoaderUtils.loadRadiusImage(context,true,holder.itemView.ivPicture,item.data.image,R.drawable.ic_logo_slogan,R.drawable.ic_error,20)
            holder.itemView.ivPicture.load(item.data.image, 4f)

            holder.itemView.setOnClickListener {
                ToastUtil.showShortToast(item.data.title)
            }
        }
    }
}