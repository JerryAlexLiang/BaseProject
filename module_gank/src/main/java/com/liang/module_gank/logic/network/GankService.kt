package com.liang.module_gank.logic.network

import com.liang.module_gank.logic.model.NewGirlsRes
import com.liang.module_gank.logic.model.NiceGankGirlRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 美女图片福利API
     */
    //https://www.mxnzp.com/api/image/girl/list
    //https://www.mxnzp.com/api/image/girl/list?page=1&app_id=oge6surireelxgoj&app_secret=N0RRblptUWR2S3ErMHQrbmMzNHV6dz09
    //page	字符串	当前页数，从1开始 NewGirlsRes

    @GET("image/girl/list")
    fun getNewNiceGirlData(@Query("page") page: Int): Call<NewGirlsRes>

}