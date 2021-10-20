package com.liang.module_gank.logic.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 创建日期: 2021/10/20 on 11:35 AM
 * 描述: 添加请求头拦截器
 * 作者: 杨亮
 */
class AddHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val url = originalRequest.url().url().toString()

        val builder: Request.Builder = originalRequest.newBuilder()
        if (url.startsWith("https://www.mxnzp.com/api")) {
            builder.header("app_id", "dlgpsborkcrsfpkl")
            builder.header("app_secret", "MGlJSnVRaW5oU0M1U3JvSjkyUjU1UT09")
        }
        val requestBuilder: Request.Builder = builder.method(originalRequest.method(), originalRequest.body())
        val request: Request = requestBuilder.build()
        return chain.proceed(request)


    }
}