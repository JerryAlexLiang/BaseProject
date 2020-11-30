package com.liang.module_eyepetizer.logic.model.test

import com.chad.library.adapter.base.BaseProviderMultiAdapter

/**
 * 创建日期: 2020/11/30 on 1:45 PM
 * 描述:
 * 作者: 杨亮
 */
class LocalJsonAdapter : BaseProviderMultiAdapter<BaseCustomViewModel>() {

    init {
        addItemProvider(MultiCardProvider())
        addItemProvider(SingleCardOneProvider())
        addItemProvider(SingleCardTwoProvider())
    }

    companion object {
        const val MultiCardBean = 1
        const val SingleCardOneBean = 2
        const val SingleCardTwoBean = 3
    }

    override fun getItemType(data: List<BaseCustomViewModel>, position: Int): Int {
        when(data[position]){
            is Test.MultiCardBean->{
                return MultiCardBean
            }

            is Test.SingleCardOneBean->{
                return SingleCardOneBean
            }

            is Test.SingleCardTwoBean->{
                return SingleCardTwoBean
            }

            else -> {
                return 0
            }
        }
    }


}


//class DiscoveryAdapter(dataList: MutableList<Item>) : BaseProviderMultiAdapter<Item>(dataList) {
//
//    init {
//        addItemProvider(TopBannerProvider())
//        addItemProvider(CategoryCardProvider(EyeConstant.IDisCoverItemType.CATEGORY_CARD_VIEW, R.layout.eye_category_item_card_view))
//    }
//
//    override fun getItemType(data: List<Item>, position: Int): Int {
//
//        when (data[position].type) {
//            EyeConstant.IDisCoverItemReturnType.horizontalScrollCard -> {
//                //顶部banner
//                return EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW
//            }
//
//            EyeConstant.IDisCoverItemReturnType.specialSquareCardCollection->{
//                //热门分类
//                return EyeConstant.IDisCoverItemType.CATEGORY_CARD_VIEW
//            }
//
//            else -> {
//                return EyeConstant.IDisCoverItemType.UNKNOWN
//            }
//        }
////        return EyeConstant.IDisCoverItemType.UNKNOWN
//    }
//
//}