package com.liang.module_eyepetizer.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 创建日期: 2020/9/15 on 7:41 PM
 * 描述: 定义的统一的网络数据源访问入口，对所有的网络请求的API进行封装
 * EyeNetwork单例类
 * 作者: 杨亮
 */
object EyeNetwork {

    //1、泛型实化
    private val mainPageService = EyeServiceCreator.create<MainPageService>()

    //2、借助协程来简化代码，获取开眼首页推荐数据
    suspend fun fetchEyeHomeCommendData(query:String) = mainPageService.fetchEyeHomeCommendData(query).await()

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