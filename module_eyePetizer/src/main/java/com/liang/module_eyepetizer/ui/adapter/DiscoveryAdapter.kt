package com.liang.module_eyepetizer.ui.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.EyeConstant
import com.liang.module_eyepetizer.logic.model.Item
import com.liang.module_eyepetizer.ui.adapter.provider.CategoryCardProvider
import com.liang.module_eyepetizer.ui.adapter.provider.TopBannerProvider

/**
 * 创建日期: 2020/11/24 on 4:13 PM
 * 描述:
 * 作者: 杨亮
 */
class DiscoveryAdapter(dataList: MutableList<Item>) : BaseProviderMultiAdapter<Item>(dataList) {

    init {
        addItemProvider(TopBannerProvider())
        addItemProvider(CategoryCardProvider(EyeConstant.IDisCoverItemType.CATEGORY_CARD_VIEW, R.layout.eye_category_item_card_view))
    }

    override fun getItemType(data: List<Item>, position: Int): Int {

        when (data[position].type) {
            EyeConstant.IDisCoverItemReturnType.horizontalScrollCard -> {
                //顶部banner
                return EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW
            }

            EyeConstant.IDisCoverItemReturnType.specialSquareCardCollection->{
                //热门分类
                return EyeConstant.IDisCoverItemType.CATEGORY_CARD_VIEW
            }
            else -> {
                return EyeConstant.IDisCoverItemType.UNKNOWN
            }
        }
//        return EyeConstant.IDisCoverItemType.UNKNOWN
    }

}


