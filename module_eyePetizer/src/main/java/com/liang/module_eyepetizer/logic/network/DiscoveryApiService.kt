package com.liang.module_eyepetizer.logic.network

import com.liang.module_eyepetizer.logic.model.DiscoveryBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * 创建日期: 2020/9/15 on 7:10 PM
 * 描述: 主页界面，主要包含：（首页，社区，通知，我的）对应的 API 接口
 * 作者: 杨亮
 */
interface DiscoveryApiService {

    /**
     * 首页-发现列表
     */
    @GET
    fun fetchDiscovery(@Url url: String): Call<DiscoveryBean>


    companion object {

        /**
         * 首页-发现列表
         */
        const val DISCOVERY_URL = "${EyeServiceCreator.EYE_BASE_URL}api/v7/index/tab/discovery"
    }
}