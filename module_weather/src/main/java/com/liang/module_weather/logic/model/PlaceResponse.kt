package com.liang.module_weather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 创建日期: 2020/8/26 on 3:31 PM
 * 描述: 数据模型 - 彩云天气 - 地区搜索返回Bean
 * 作者: 杨亮
 */
data class PlaceResponse(
        val places: List<Place>,
        val query: String,
        val status: String
)

data class Place(
//        val formatted_address: String,
        //由于JSON中的一些字段的命名可能与Kotlin的命名规范不太一致，因此在这里使用了@SerializedName注解的方式，来让JSON字段和Kotlin字段之间建立映射关系
        @SerializedName("formatted_address") val address: String,
        val id: String,
        val location: Location,
        val name: String,
//        val place_id: String
        @SerializedName("place_id") val placeId: String
)

data class Location(
        val lat: Double,
        val lng: Double
)