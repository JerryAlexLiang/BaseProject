package com.liang.module_weather.logic.network

import com.liang.module_core.retrofit.HttpLoggingInterceptor
import com.liang.module_core.utils.LogUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 创建日期: 2020/8/26 on 3:42 PM
 * 描述: Retrofit构建器 - ServiceCreator单例类
 * 作者: 杨亮
 */
object ServiceCreator {

    //彩云天气API
    private const val WEATHER_BASE_URL = "https://api.caiyunapp.com/"

    private val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)

    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        LogUtil.e("RxHttpUtils", "message= $message")
    })


    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;
        client.addInterceptor(loggingInterceptor);
        client.build();
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()

//    fun <T> create(serviceClass: Class<T>): T {
//        return retrofit.create(serviceClass)
//    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

//    inline fun <reified T> create(): T {
//        return create(T::class.java)
//    }

    inline fun <reified T> create(): T = create(T::class.java)
}