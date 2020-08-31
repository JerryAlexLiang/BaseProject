package com.liang.module_weather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.liang.module_weather.logic.Repository
import com.liang.module_weather.logic.model.Place

/**
 * 创建日期: 2020/8/26 on 5:03 PM
 * 描述: 彩云天气 - 城市搜索 - ViewModel
 * ViewModel相当于逻辑层与UI层之间的一个桥梁，虽然它更偏向于逻辑层的部分，但是由于ViewModel通常和Activity或Fragment是一一对应的，
 * 因此，还是习惯将它们放在一起
 * 作者: 杨亮
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    //城市列表数据List
    //和界面相关的数据，定义在ViewModel中，可以保证它们在手机屏幕发生旋转的时候不会丢失，稍后在编写UI层代码的时候就会用到这几个变量
    var placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    /**
     * 1、每当searchPlaces()函数被调用时，观察函数switchMap()方法所对应的转换函数就会执行；
     * 2、在转换函数中，只需调用仓库层中定义的Repository.searchPlaces(query)方法就可以发起网络请求，
     * 同时，将仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象；
     */
    fun searchPlaces(query: String) {
        //这里没有直接调用仓库层中的Repository.searchPlaces()方法，而是将传入的搜索参数赋值给一个searchLiveData对象，
        //并使用Transformations.switchMap(searchLiveData)方法来观察这个对像，否则仓库层返回的LiveData对象将无法进行观察。
        searchLiveData.value = query
    }

    //存储Place相关

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}