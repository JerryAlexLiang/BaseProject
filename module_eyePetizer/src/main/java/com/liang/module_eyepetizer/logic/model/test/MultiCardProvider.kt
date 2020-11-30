package com.liang.module_eyepetizer.logic.model.test

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liang.module_eyepetizer.R
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils

/**
 * 创建日期: 2020/11/30 on 1:52 PM
 * 描述:
 * 作者: 杨亮
 */
class MultiCardProvider : BaseItemProvider<BaseCustomViewModel>() {

    companion object {
        const val MultiCardBean = 1
        const val SingleCardOneBean = 2
        const val SingleCardTwoBean = 3
    }

    override val itemViewType: Int
        get() = MultiCardBean

    override val layoutId: Int
        get() = R.layout.multi_card_view_item

    override fun convert(helper: BaseViewHolder, item: BaseCustomViewModel) {
        val multiCardBean: Test.MultiCardBean = item as Test.MultiCardBean
        val banner: Banner<MutableList<Test.MultiCardBean.BannerBean>, YouthBannerAdapter> = helper.getView(R.id.bannerViewPage)

        banner.run {
            indicator = CircleIndicator(context)
            setIndicatorSelectedWidth(BannerUtils.dp2px(15F).toInt())
            setBannerRound(20f)
            //adapter = YouthBannerImageAdapter(item.data)
            adapter = YouthBannerAdapter(multiCardBean.banner)
        }
    }
}