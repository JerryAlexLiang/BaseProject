//package com.liang.module_eyepetizer.ui.adapter
//
//import com.chad.library.adapter.base.BaseProviderMultiAdapter
//import com.liang.module_eyepetizer.logic.model.DiscoveryBean
//import com.liang.module_eyepetizer.logic.model.EyeConstant
//
///**
// * 创建日期: 2020/11/24 on 4:13 PM
// * 描述:
// * 作者: 杨亮
// */
//class DiscoveryAdapter : BaseProviderMultiAdapter<DiscoveryBean.ItemListBeanX>() {
//
//    init {
//
//    }
//
//    override fun getItemType(data: List<DiscoveryBean.ItemListBeanX>, position: Int): Int {
//
//        when (data[position].type) {
//            EyeConstant.IDisCoverItemReturnType.horizontalScrollCard -> {
//                //顶部banner
//                return EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW
//            }
//
//            else -> return EyeConstant.IDisCoverItemType.UNKNOWN
//        }
//        return -1;
//    }
//}
