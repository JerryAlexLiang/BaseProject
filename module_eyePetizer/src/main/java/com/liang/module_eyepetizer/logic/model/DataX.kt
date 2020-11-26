package com.liang.module_eyepetizer.logic.model

data class DataX(
    val actionUrl: String,
    val adTrack: List<Any>,
    val autoPlay: Boolean,
    val dataType: String,
    val description: String,
    val header: HeaderX,
    val id: Int,
    val image: String,
    val label: Label,
    val labelList: List<Any>,
    val shade: Boolean,
    val title: String
)