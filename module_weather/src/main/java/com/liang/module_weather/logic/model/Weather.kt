package com.liang.module_weather.logic.model

/**
 * 创建日期: 2020/8/28 on 10:13 AM
 * 描述: 创建Weather类，将Realtime和Daily对象封装起来
 * 作者: 杨亮
 */
data class Weather(val realtime: RealtimeResponse.Result.Realtime, val daily: DailyResponse.Result.Daily)