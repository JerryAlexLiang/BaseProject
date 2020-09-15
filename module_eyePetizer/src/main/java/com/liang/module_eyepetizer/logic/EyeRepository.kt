package com.liang.module_eyepetizer.logic

import androidx.lifecycle.liveData
import com.liang.module_eyepetizer.logic.network.EyeNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * 创建日期: 2020/9/15 on 7:47 PM
 * 描述: Repository单例类 仓库层的统一封装入口
 * 协程：协程是一种轻量级的线程的概念
 * 作者: 杨亮
 */
object EyeRepository {

    fun fetchEyeHomeCommendData(url: String) = fire(Dispatchers.IO) {
        val fetchEyeHomeCommendData = EyeNetwork.fetchEyeHomeCommendData(url)
        Result.success(fetchEyeHomeCommendData)
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