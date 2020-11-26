package com.liang.module_eyepetizer.ui.adapter.provider

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_core.utils.DensityUtil
import com.liang.module_core.utils.ToastUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.EyeConstant
import com.liang.module_eyepetizer.logic.model.Item
import com.liang.module_eyepetizer.logic.model.ItemX
import com.liang.module_eyepetizer.ui.adapter.BannerAdapter
import com.liang.module_eyepetizer.ui.adapter.BannerViewHolder
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

/**
 * 创建日期: 2020/11/24 on 4:39 PM
 * 描述: 顶部banner
 * 作者: 杨亮
 */
class TopBannerProvider : BaseItemProvider<Item>() {

    override val itemViewType: Int
        //顶部banner
        get() = EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW

    override val layoutId: Int
        get() = R.layout.eye_discovery_banner_view

    override fun convert(helper: BaseViewHolder, item: Item) {
        val bannerViewPager: BannerViewPager<ItemX, BannerViewHolder> = helper.getView(R.id.bannerViewPager)
        bannerViewPager.run {
            setAutoPlay(true)
            setCanLoop(true)
//            setScrollDuration(3000)
            setRoundCorner(DensityUtil.dpToPx(context, 4))
            setRevealWidth(14)
            setIndicatorVisibility(View.VISIBLE)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
            setInterval(3000)
            if (item.data.itemList.size == 1) {
                setPageMargin(0)
            } else {
                setPageMargin(DensityUtil.dpToPx(context, 4))
            }
            adapter = BannerAdapter()
//            removeDefaultPageTransformer()
            setOnPageClickListener { position ->
                ToastUtil.showShortToast(item.data.itemList[position].data.title)
            }
            create(item.data.itemList)
        }

        val ivOne: ImageView = helper.getView(R.id.ivOne)
        val ivTwo: ImageView = helper.getView(R.id.ivTwo)

        Glide.with(ivOne).asBitmap().load(item.data.itemList[0].data.image).into(ivOne)
        Glide.with(ivTwo).asBitmap().load(item.data.itemList[1].data.image).into(ivTwo)

    }

}





