package com.liang.module_weather.logic

import androidx.lifecycle.liveData
import com.liang.module_weather.logic.dao.PlaceDao
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.model.Weather
import com.liang.module_weather.logic.network.WeatherConstant
import com.liang.module_weather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * 创建日期: 2020/8/26 on 4:23 PM
 * 描述: Repository单例类 仓库层的统一封装入口
 * 作者: 杨亮
 */
object Repository {

    //存储仓库层方法

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    /**
     * 1、一般在仓库层定义的方法，为了能将异步获取的数据以响应式编程的方式通知给上一层，通常会返回一个LiveData对象；
     * 2、下述代码中的liveData()函数是lifecycle-livedata-ktx库提供的一个非常强大的功能，它可以自动构建并返回一个LiveData对象，
     * 然后在它的代码块中提供一个挂起函数的上下文，这样就可以在liveData()函数中调用任意的挂起函数了。
     * 3、注意，这里将liveData()函数的线程参数类型指定为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了;
     * 4*、注意，因为使用了协程来简化网络回调的写法，导致WeatherNetwork中封装的每个网络请求接口都有可能会抛出异常，
     * 于是，我们在仓库层中为每个网络请求都进行try-catch处理
     */
//    fun searchPlaces(query: String) = liveData<Result<List<Place>>>(Dispatchers.IO) {
//        val result: Result<List<Place>> = try {
//            val placeResponse = WeatherNetwork.searchPlaces(query)
//            if (placeResponse.status == WeatherConstant.STATUS_OK) {
//                val places = placeResponse.places
//                //4、kotlin内置的 Result.success()方法来包装获取的城市数据列表
//                Result.success(places)
//            } else {
//                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
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

    /**
     * 1、注意，在仓库层中并没有提供两个分别用于获取实时天气和未来天气的方法，而是提供了一个refreshWeather()方法用于来刷新天气信息，
     * 对于调用方而言，需要调用两次请求才能获得其想要的所有天气数据明显是比较繁琐的行为，因此最好的做法就是在仓库层在进行一次统一的封装；
     * 2、不过，获取实时天气和未来天气这两个接口请求是没有先后顺序的，因此让它们并发执行可以提升程序的运行效率；
     * 但是要在同时得到它们的响应结果后才能进一步执行程序 ---> 协程async()函数；
     * 3、只需要分别在两个async()函数中发起网络请求，然后分别调用它们的await()方法，就可以保证只有在两个网络请求都成功响应之后，才会进一步执行程序；
     * 4、另外，由于协程async()函数必须在协程的作用域内才能调用，所以这里又使用coroutineScope函数创建了一个协程作用域；
     */
//    fun refreshWeather(lng: String, lat: String) = liveData<Result<Weather>>(Dispatchers.IO) {
//        val result = try {
//            coroutineScope {
//                val deferredRealtime = async {
//                    WeatherNetwork.getRealtimeWeather(lng, lat)
//                }
//
//                val deferredDaily = async {
//                    WeatherNetwork.getDailyWeather(lng, lat)
//                }
//
//                val realtimeResponse = deferredRealtime.await()
//                val dailyResponse = deferredDaily.await()
//
//                //在同时获取到RealtimeResponse和DailyResponse之后，判断响应状态
//                if (realtimeResponse.status == WeatherConstant.STATUS_OK && dailyResponse.status == WeatherConstant.STATUS_OK) {
//                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
//                    //使用Result.success()方法来包装Weather对象
//                    Result.success(weather)
//                } else {
//                    //使用Result.failure()方法来包装一个异常信息
//                    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}" +
//                            "   daily response status is ${dailyResponse.status}"))
//                }
//            }
//        } catch (e: Exception) {
//            Result.failure<Weather>(e)
//        }
//        //同样，最后调用emit()方法将包装的结果发射出去
//        emit(result)
//    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                WeatherNetwork.getRealtimeWeather(lng, lat)
            }

            val deferredDaily = async {
                WeatherNetwork.getDailyWeather(lng, lat)
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            //在同时获取到RealtimeResponse和DailyResponse之后，判断响应状态
            if (realtimeResponse.status == WeatherConstant.STATUS_OK && dailyResponse.status == WeatherConstant.STATUS_OK) {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                //使用Result.success()方法来包装Weather对象
                Result.success(weather)
            } else {
                //使用Result.failure()方法来包装一个异常信息
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}" +
                        "   daily response status is ${dailyResponse.status}"))
            }
        }
    }

    /**
     * 可以在某个统一的入口函数中进行封装，使得只要进行一次try-catch处理就行了
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