package com.liang.module_weather.logic.network

import com.liang.module_weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 创建日期: 2020/8/26 on 4:02 PM
 * 描述: 定义的统一的网络数据源访问入口，对所有的网络请求的API进行封装
 * WeatherNetwork单例类
 * 作者: 杨亮
 */
object WeatherNetwork {

    //1、使用ServiceCreator创建了一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    /**
     * 当外部调用WeatherNetwork的searchPlaces()函数时，
     * 1、Retrofit就会立即发起网络请求，同时当前的协程也会被阻塞住；
     * 2、直到服务器响应我们的请求之后，await()函数会将解析出来的数据模型对象取出并返回，同时恢复当前协程的执行；
     * 3、searchPlaces()函数在的得到await()函数的返回值后会将该数据再返回到上一层。
     */
    //2、定义一个searchPlaces()函数
//    suspend fun searchPlaces(query: String): PlaceResponse {
//        //3、调用PlaceService接口中的searchPlaces()方法，发起搜索城市数据请求
//        //4、为了让代码更加简洁
//        //5、借助协程技术来实现简化代码，这里定义一个await()函数，并将searchPlaces()函数也声明成挂起函数suspend
//        return placeService.searchPlaces(query).await()
//    }

    //3、调用PlaceService接口中的searchPlaces()方法，发起搜索城市数据请求
    //4、为了让代码更加简洁
    //5、借助协程技术来实现简化代码，这里定义一个await()函数，并将searchPlaces()函数也声明成挂起函数suspend
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()


    //WeatherService接口封装
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    //实时天气接口请求
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    //未来几天天气接口请求
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}