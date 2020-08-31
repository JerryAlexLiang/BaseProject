package com.liang.module_weather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.liang.module_core.app.BaseApplication
import com.liang.module_weather.WeatherApplication
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.network.WeatherConstant

/**
 * 创建日期: 2020/8/31 on 10:58 AM
 * 描述: 持久化存储place
 * 作者: 杨亮
 */
object PlaceDao {

//    private fun sharedPreferences(): SharedPreferences {
//        return WeatherApplication.context.getSharedPreferences(WeatherConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
//    }

//    private fun sharedPreferences(): SharedPreferences =
//            WeatherApplication.context.getSharedPreferences(WeatherConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    private fun sharedPreferences() =
            BaseApplication.mContext.getSharedPreferences(WeatherConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun isPlaceSaved() = sharedPreferences().contains(WeatherConstant.SHARED_PLACE_NAME)

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString(WeatherConstant.SHARED_PLACE_NAME, Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString(WeatherConstant.SHARED_PLACE_NAME, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }
}