package com.liang.module_gank.logic.network

import com.liang.module_gank.logic.model.NiceGankGirlRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 创建日期: 2021/7/23 on 5:38 PM
 * 描述: Gank新版API
 * 作者: 杨亮
 */
interface GankService {

    /**
     * 干货API - 颜如玉(福利)
     */
    //https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
    @GET("data/category/Girl/type/Girl/page/{page}/count/10")
    fun getNiceGankGirlData(@Path("page") page: Int): Call<NiceGankGirlRes>


}