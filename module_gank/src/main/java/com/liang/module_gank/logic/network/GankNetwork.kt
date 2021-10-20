package com.liang.module_gank.logic.network

import retrofit2.await

/**
 * 创建日期: 2021/7/23 on 5:48 PM
 * 描述: 定义的统一的网络数据源访问入口，对所有的网络请求的API进行封装
 * GankNetwork单例类
 * 作者: 杨亮
 */
object GankNetwork {

    //1、使用ServiceCreator创建了一个PlaceService接口的动态代理对象
//    private val placeService = ServiceCreator.create(GankService::class.java)
    //泛型实化
    private val gankService = ServiceCreator.create<GankService>()

    /**
     * 当外部调用WeatherNetwork的searchPlaces()函数时，
     * 1、Retrofit就会立即发起网络请求，同时当前的协程也会被阻塞住；
     * 2、直到服务器响应我们的请求之后，await()函数会将解析出来的数据模型对象取出并返回，同时恢复当前协程的执行；
     * 3、getNiceGankGirlData()函数在的得到await()函数的返回值后会将该数据再返回到上一层。
     */

    //3、调用PlaceService接口中的getNiceGankGirlData()方法，发起搜索数据请求
    //4、为了让代码更加简洁
    //5、借助协程技术来实现简化代码，这里定义一个await()函数，并将searchPlaces()函数也声明成挂起函数suspend
    suspend fun getNiceGankGirlData(page: Int) = gankService.getNiceGankGirlData(page).await()

    suspend fun getNewNiceGirlData(page: Int) = gankService.getNewNiceGirlData(page).await()
}