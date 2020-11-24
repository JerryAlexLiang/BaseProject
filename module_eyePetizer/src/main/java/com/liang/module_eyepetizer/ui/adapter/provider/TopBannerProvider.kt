//package com.liang.module_eyepetizer.ui.adapter.provider
//
//import com.chad.library.adapter.base.provider.BaseItemProvider
//import com.chad.library.adapter.base.viewholder.BaseViewHolder
//import com.liang.module_eyepetizer.logic.model.DiscoveryBean
//import com.liang.module_eyepetizer.logic.model.EyeConstant
//
///**
// * 创建日期: 2020/11/24 on 4:39 PM
// * 描述: 顶部banner
// * 作者: 杨亮
// */
//class TopBannerProvider() : BaseItemProvider<DiscoveryBean.ItemListBeanX>() {
//
//    override val itemViewType: Int
//        //顶部banner
//        get() = EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW
//
//    override val layoutId: Int
//        get() = TODO("Not yet implemented")
//
//
//    override fun convert(helper: BaseViewHolder, item: DiscoveryBean.ItemListBeanX) {
//        TODO("Not yet implemented")
//    }
//
//
//}
//
//
////class TopBannerProvider : BaseItemProvider<BaseCustomViewModel?>() {
////    fun getItemViewType(): Int {
////        return IDisCoverItemType.TOP_BANNER_VIEW
////    }
////
////    fun getLayoutId(): Int {
////        return R.layout.home_item_top_banner_view
////    }
////
////    override fun onViewHolderCreated(viewHolder: BaseViewHolder,
////                                     viewType: Int) {
////        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
////    }
////
////    override fun convert(baseViewHolder: BaseViewHolder,
////                         baseCustomViewModel: BaseCustomViewModel?) {
////        if (baseCustomViewModel == null) {
////            return
////        }
////        val binding: HomeItemTopBannerViewBinding = baseViewHolder.getBinding()
////        if (binding != null) {
////            binding.setViewModel(baseCustomViewModel as TopBannerViewModel?)
////            binding.executePendingBindings()
////        }
////    }
////}