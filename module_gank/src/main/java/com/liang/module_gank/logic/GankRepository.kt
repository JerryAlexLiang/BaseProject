package com.liang.module_gank.logic

import androidx.lifecycle.liveData
import com.liang.module_gank.logic.network.GankNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * 创建日期: 2021/7/23 on 5:52 PM
 * 描述: Repository单例类 仓库层的统一封装入口
 * 协程：协程是一种轻量级的线程的概念
 * 作者: 杨亮
 */
object GankRepository {

    /**
     * 1、一般在仓库层定义的方法，为了能将异步获取的数据以响应式编程的方式通知给上一层，通常会返回一个LiveData对象；
     * 2、下述代码中的liveData()函数是lifecycle-livedata-ktx库提供的一个非常强大的功能，它可以自动构建并返回一个LiveData对象，
     * 然后在它的代码块中提供一个挂起函数的上下文，这样就可以在liveData()函数中调用任意的挂起函数了。
     * 3、注意，这里将liveData()函数的线程参数类型指定为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了;
     * 4*、注意，因为使用了协程来简化网络回调的写法，导致WeatherNetwork中封装的每个网络请求接口都有可能会抛出异常，
     * 于是，我们在仓库层中为每个网络请求都进行try-catch处理。
     */
    fun getNiceGankGirlData(page: Int) = fire(Dispatchers.IO) {
        val niceGankGirlResponse = GankNetwork.getNiceGankGirlData(page)
        val data = niceGankGirlResponse.data
        Result.success(data)
    }

    /**
     * 可以在某个统一的入口函数中进行封装，使得只要进行一次try-catch处理就行了。
     * 新增fire()函数，这是一个按照liveData()函数的参数接收标准定义的一个高阶函数；
     * 在fire()函数内部，会先调用一下liveData()函数，然后在liveData()函数的代码块中统一进行了try-catch处理，
     * 并在try语句中调用传入的Lambda表达式中的代码，最终获取Lambda表达式的执行结果并调用emit()方法发射出去。
     *
     * 注意：
     * 1、在liveData()函数的代码块中，我们是拥有挂起函数suspend上下文的，可是当回调到Lambda表达式中，代码就没有挂起函数suspend上下文了，
     * 但实际上Lambda表达式中的代码一定也是在挂起函数suspend中运行的；
     * 2、为了解决这个问题，需要在函数类型前声明一个suspend关键字，以表示所有传入的Lambda表达式中的代码也是拥有挂起函数suspend上下文的。
     * 3、定义好了fire()函数之后，剩下的工作就是分别将searchPlaces()和refreshWeather()方法中调用的liveData()函数替换成fire()函数，然后把try-catch语句、emit()方法
     * 之类的逻辑移除即可。这样，仓库层Repository中的代码就变得更加简洁清晰了。
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
            liveData<Result<T>>(context) {
                val result = try {
                    block()
                } catch (e: Exception) {
                    Result.failure<T>(e)
                }
                emit(result)
            }
}