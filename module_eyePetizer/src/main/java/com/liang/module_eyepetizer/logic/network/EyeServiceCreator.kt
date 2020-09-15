package com.liang.module_eyepetizer.logic.network

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
object EyeServiceCreator {

    //开眼视频BASE_URL
    private const val EYE_BASE_URL = "http://baobab.kaiyanapp.com/"

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

    //1、使用private修饰符来声明，相当于对外部而言它们都是不可见的
    private val retrofit = Retrofit.Builder()
            .baseUrl(EYE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()

//    fun <T> create(serviceClass: Class<T>): T {
//        return retrofit.create(serviceClass)
//    }

    //2、最后，提供一个外部可见的create()方法，并接收一个Class类型的参数
    //当外部调用这个方法时，实际上就是调用了Retrofit对象的create()方法，从而创建出相应Service接口的动态代理对象
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

//    inline fun <reified T> create(): T {
//        return create(T::class.java)
//    }

    //3、优化：泛型实化功能：
    //a、定义一个不带参数的create()方法，并使用inline关键字来修饰方法；
    //b、使用reified具体化关键字来修饰泛型，这是泛型实化的两大前提条件；
    //c、接下来就可以使用T::class.java这种语法了，这里调用刚才2中定义的带有Class参数的create()方法即可
    inline fun <reified T> create(): T = create(T::class.java)
}