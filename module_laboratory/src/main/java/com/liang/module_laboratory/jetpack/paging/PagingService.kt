package com.liang.module_laboratory.jetpack.paging

import com.liang.module_core.retrofit.HttpLoggingInterceptor
import com.liang.module_core.utils.LogUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * 创建日期: 2021/7/22 on 2:20 PM
 * 描述: 用于提供网络请求接口
 * 作者: 杨亮
 */
interface PagingService {

    //https://api.github.com/search/repositories?sort=stars&q=Android&per_page=5&page=1
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchPagingResponse(@Query("page") page: Int, @Query("per_page") perPage: Int): PagingBean

    //https://www.wanandroid.com/article/list/0/json
    @GET("article/list/{page}/json")
    suspend fun searchPagingResponse2(@Path("page") page: Int) : BaseResponse<PagingBean2>

    companion object {

        private const val BASE_URL = "https://api.github.com/"
        private const val BASE_URL2 = "https://www.wanandroid.com/"

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

        fun create(): PagingService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build()
                    .create(PagingService::class.java)
        }

    }
}