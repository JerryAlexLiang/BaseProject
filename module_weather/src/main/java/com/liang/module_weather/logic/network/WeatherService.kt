package com.liang.module_weather.logic.network

import com.liang.module_weather.WeatherApplication
import com.liang.module_weather.logic.model.DailyResponse
import com.liang.module_weather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 创建日期: 2020/8/28 on 9:46 AM
 * 描述: 天气API
 * 作者: 杨亮
 */
interface WeatherService {

    //实时天气API
    @GET("v2.5/${WeatherApplication.WEATHER_API_TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    //未来几天天气API
    @GET("v2.5/${WeatherApplication.WEATHER_API_TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}