package com.liang.module_weather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * 创建日期: 2020/8/26 on 4:23 PM
 * 描述: Repository单例类 仓库层的统一封装入口
 * 作者: 杨亮
 */
object Repository {

    /**
     * 1、一般在仓库层定义的方法，为了能将异步获取的数据以响应式编程的方式通知给上一层，通常会返回一个LiveData对象；
     * 2、下述代码中的liveData()函数是lifecycle-livedata-ktx库提供的一个非常强大的功能，它可以自动构建并返回一个LiveData对象，
     * 然后在它的代码块中提供一个挂起函数的上下文，这样就可以在liveData()函数中调用任意的挂起函数了。
     * 3、注意，这里将liveData()函数的线程参数类型指定为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了
     */
//    fun searchPlaces(query: String): LiveData<Result<List<Place>>> = liveData(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = WeatherNetwork.searchPlaces(query)
//
//            val status = placeResponse.status
//            val places1 = placeResponse.places
//
//            if (status == "OK") {
//                val places = placeResponse.places
//                //4、kotlin内置的 Result.success()方法来包装获取的城市数据列表
//                Result.success<List<Place>>(places)
//            } else {
//                //Result.failure来包装一个异常信息
//                Result.failure<List<Place>>(RuntimeException("response status is $status"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        //5、最后，使用一个emit()方法将包装的结果发射出去，这个emit()方法类似于调用LiveData的setValue()方法来通知数据变化
//        //只不过这里我们无法直接取得返回的LiveData对象，所以lifecycle-livedata-ktx库提供了这样一个替代方法。
//        emit(result)
//    }

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

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