package com.liang.module_gank.logic.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 创建日期: 2021/10/20 on 11:23 AM
 * 描述: 公共参数拦截器
 * 添加公共请求参数
 * 这种方式是 通过在URL上 加的公共请求参数，一般是通过请求头方式加
 * 若有些请求不用或者不用全部公共请求参数，则那些不用的传空值即可
 * 作者: 杨亮
 */
class AddQueryParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedUrl: HttpUrl = originalRequest.url().newBuilder()
                .addQueryParameter("app_id", "dlgpsborkcrsfpkl")
                .addQueryParameter("app_secret", "MGlJSnVRaW5oU0M1U3JvSjkyUjU1UT09")
                .build()

        val request: Request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }
}