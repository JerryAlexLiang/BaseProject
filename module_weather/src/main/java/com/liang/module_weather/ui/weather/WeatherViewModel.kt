package com.liang.module_weather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.liang.module_weather.logic.Repository
import com.liang.module_weather.logic.model.Location

/**
 * 创建日期: 2020/8/28 on 10:59 AM
 * 描述: ViewModel相当于逻辑层与UI层之间的一个桥梁，虽然它更偏向于逻辑层的部分，但是由于ViewModel通常和Activity或Fragment是一一对应的，
 * 因此，还是习惯将它们放在一起
 * 作者: 杨亮
 */
class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    //定义3个变量locationLng、locationLat、placeName
    //和界面相关的数据，定义在ViewModel中，可以保证它们在手机屏幕发生旋转的时候不会丢失，稍后在编写UI层代码的时候就会用到这几个变量
    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    //通过switchMap()转换函数转换后，仓库层返回的LiveData对象就可以转换成一个可供Activity和Fragment观察的LiveData对象了
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}