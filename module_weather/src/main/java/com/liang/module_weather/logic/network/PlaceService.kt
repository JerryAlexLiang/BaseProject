package com.liang.module_weather.logic.network

import com.liang.module_weather.WeatherApplication
import com.liang.module_weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 创建日期: 2020/8/26 on 3:36 PM
 * 描述: 彩云天气城市搜索Retrofit接口
 * 作者: 杨亮
 */
interface PlaceService {

    /**
     * 1、搜索城市只有query一个参数是需要动态指定的，使用@Query注解的方式来实现；
     * 2、另外两个参数是不会变的，因此固定写在@GET注解中即可；
     * 3、返回值声明为Call<PlaceResponse>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象
     */
    @GET("v2/place?token=${WeatherApplication.WEATHER_API_TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}