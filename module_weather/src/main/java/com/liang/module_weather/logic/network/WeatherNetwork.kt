package com.liang.module_weather.logic.network

import com.liang.module_weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 创建日期: 2020/8/26 on 4:02 PM
 * 描述: 定义的统一的网络数据源访问入口，对所有的网络请求的API进行封装
 * WeatherNetwork单例类
 * 作者: 杨亮
 */
object WeatherNetwork {

    //1、使用ServiceCreator创建了一个PlaceService接口的动态代理对象
//    private val placeService = ServiceCreator.create(PlaceService::class.java)
    //泛型实化
    private val placeService = ServiceCreator.create<PlaceService>()

    /**
     * 当外部调用WeatherNetwork的searchPlaces()函数时，
     * 1、Retrofit就会立即发起网络请求，同时当前的协程也会被阻塞住；
     * 2、直到服务器响应我们的请求之后，await()函数会将解析出来的数据模型对象取出并返回，同时恢复当前协程的执行；
     * 3、searchPlaces()函数在的得到await()函数的返回值后会将该数据再返回到上一层。
     */
    //2、定义一个searchPlaces()函数
//    suspend fun searchPlaces(query: String): PlaceResponse {
//        //3、调用PlaceService接口中的searchPlaces()方法，发起搜索城市数据请求
//        //4、为了让代码更加简洁
//        //5、借助协程技术来实现简化代码，这里定义一个await()函数，并将searchPlaces()函数也声明成挂起函数suspend
//        return placeService.searchPlaces(query).await()
//    }

    //3、调用PlaceService接口中的searchPlaces()方法，发起搜索城市数据请求
    //4、为了让代码更加简洁
    //5、借助协程技术来实现简化代码，这里定义一个await()函数，并将searchPlaces()函数也声明成挂起函数suspend
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //WeatherService接口封装
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    //实时天气接口请求
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    //未来几天天气接口请求
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    /**
     * 1、传统的回调机制基本上是依靠匿名类来实现的，使用这个机制可以实现获取异步网络请求数据响应功能，但是传统的回调机制的写法遍及欧繁琐；
     * 在多少个地方发起网络请求，就需要编写多少次这样的匿名类实现。
     *
     * 2、Kotlin：可以借助suspendCoroutine函数就能将传统回调机制的写法大幅简化
     * suspendCoroutine函数必须在协程作用域内才能调用，它接收一个Lambda表达式参数，主要作用是将当前协程立即挂起，然后在一个普通的线程中执行Lambda表达式中的代码。
     * Lambda表达式的参数列表上会传入一个Continuation参数，并调用它的resume()方法或者resumeWithException()方法，可以将协程恢复执行。
     * 如果请求成功，就调用Continuation的resume()方法会恢复被挂起的协程，并传入服务器响应的数据body，该值会成为suspendCoroutine函数的返回值。
     * 如果请求失败，就调用Continuation的resumeWithException()方法恢复被挂起的协程，并传入具体的异常原因。
     *
     * 3、解释：
     * (1)、首先，await()函数仍然是一个挂起函数，然后我们给它声明了一个泛型T，并将await()函数定义成了Call<T>的扩展函数，
     * 这样所有返回值是Call类型的Retrofit网络请求接口就都可以直接调用await()函数了。
     * (2)、接着，await()函数中使用了suspendCoroutine函数来挂起当前协程，并且由于扩展函数的原因，我么现在拥有了Call对象的上下文，
     * 那么这里就可以直接调用enqueue()方法让Retrofit发起网络请求。
     * (3)、接下来，使用同样的方式对Retrofit响应的数据或者网络请求失败的情况进行处理就可以了。
     * (4)、另外，还有一点需要注意，在onResponse()回调当中，我们调用body()方法解析出来的对象可能是为空的。如果为空的话，这里的做法是手动抛出一个异常，也可以根据自己的逻辑进行
     * 更加合适的处理。
     * (5)、有了自定义的await()函数之后，我们调用Retrofit的Service接口都会变得极其简单。
     *
     */
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