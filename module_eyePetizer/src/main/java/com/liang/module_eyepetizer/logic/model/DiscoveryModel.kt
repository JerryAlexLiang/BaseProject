package com.liang.module_eyepetizer.logic.model

data class DiscoveryModel(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: String,
    val total: Int


)