package com.liang.module_eyepetizer.ui.adapter.provider

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.jetpack.utils.SpecialSquareCardCollectionItemDecoration
import com.liang.module_core.jetpack.utils.load
import com.liang.module_core.utils.DensityUtil.getScreenWidth
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.widget.pageMenuLayout.PageSlideIndicatorView
import com.liang.module_core.widget.pageMenuLayout.PageSlideMenuBanner
import com.liang.module_core.widget.pageMenuLayout.adapter.PageSlideMenuViewHolderCreator
import com.liang.module_core.widget.pageMenuLayout.adapter.PageSlideRvHolder
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

        val pageSlideMenuBanner: PageSlideMenuBanner<ItemX> = helper.getView(R.id.pageSlideMenuBanner)
        pageSlideMenuBanner.setPageData(dataList, object : PageSlideMenuViewHolderCreator {

            override fun createHolder(itemView: View): PageSlideRvHolder<*>? {
                return object : PageSlideRvHolder<ItemX?>(itemView) {

                    private var entranceNameTextView: TextView? = null
                    private var entranceIconImageView: ImageView? = null

                    override fun initView(itemView: View?) {
                        entranceIconImageView = itemView?.findViewById(R.id.entrance_image)
                        entranceNameTextView = itemView?.findViewById(R.id.entrance_name)
                        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (getScreenWidth(context).toFloat() / 4.0f).toInt())
                        itemView?.layoutParams = layoutParams
                    }

                    override fun bindView(holder: RecyclerView.ViewHolder?, data: ItemX?, pos: Int) {
                        entranceNameTextView?.text = data?.data?.title
//                        Glide.with(context).asBitmap().load(data?.data?.image).into(entranceIconImageView!!)
                        entranceIconImageView?.load(data?.data?.image!!, 4f)

                        holder?.itemView?.setOnClickListener {
                            ToastUtil.showShortToast(data?.data?.title)
                        }

                    }

                }
            }

            override fun getLayoutId(): Int {
                return R.layout.eye_item_home_entrance
            }
        })

        val pageSlideIndicatorView: PageSlideIndicatorView = helper.getView(R.id.pageSlideIndicatorView)
        if (dataList.size < 11) {
            pageSlideIndicatorView.visibility = View.GONE
        } else {
            pageSlideIndicatorView.visibility = View.VISIBLE
        }

        pageSlideIndicatorView.setIndicatorCount(pageSlideMenuBanner.pageCount)

        pageSlideMenuBanner.setOnPageListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                pageSlideIndicatorView.setCurrentIndicator(position)
            }
        })

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