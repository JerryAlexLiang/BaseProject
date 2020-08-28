package com.liang.module_weather.logic.model

import java.util.*

/**
 * 创建日期: 2020/8/27 on 5:57 PM
 * 描述: 未来几天天气
 * 这里将所有的数据模型类都定义在了DailyResponse的内部，
 * 这样可以防止出现和其它接口的数据模型类有同名冲突的情况
 * 作者: 杨亮
 */
data class DailyResponse(
        val api_status: String,
        val api_version: String,
        val lang: String,
        val location: List<Double>,
        val result: Result,
        val server_time: Int,
        val status: String,
        val timezone: String,
        val tzshift: Int,
        val unit: String
) {
    data class Result(
            val daily: Daily,
            val primary: Int
    ) {
        data class Daily(
                val air_quality: AirQuality,
                val astro: List<Astro>,
                val cloudrate: List<Cloudrate>,
                val dswrf: List<Dswrf>,
                val humidity: List<Humidity>,
                val life_index: LifeIndex,
                val precipitation: List<Precipitation>,
                val pressure: List<Pressure>,
                val skycon: List<Skycon>,
                val skycon_08h_20h: List<Skycon08h20h>,
                val skycon_20h_32h: List<Skycon20h32h>,
                val status: String,
                val temperature: List<Temperature>,
                val visibility: List<Visibility>,
                val wind: List<Wind>
        ) {
            data class AirQuality(
                    val aqi: List<Aqi>,
                    val pm25: List<Pm25>
            ) {
                data class Aqi(
                        val avg: Avg,
                        val date: Date,
                        val max: Max,
                        val min: Min
                ) {
                    data class Avg(
                            val chn: Double,
                            val usa: Double
                    )

                    data class Max(
                            val chn: Int,
                            val usa: Int
                    )

                    data class Min(
                            val chn: Int,
                            val usa: Int
                    )
                }

                data class Pm25(
                        val avg: Double,
                        val date: Date,
                        val max: Int,
                        val min: Int
                )
            }

            data class Astro(
                    val date: Date,
                    val sunrise: Sunrise,
                    val sunset: Sunset
            ) {
                data class Sunrise(
                        val time: String
                )

                data class Sunset(
                        val time: String
                )
            }

            data class Cloudrate(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class Dswrf(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class Humidity(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class LifeIndex(
                    val carWashing: List<CarWashing>,
                    val coldRisk: List<ColdRisk>,
                    val comfort: List<Comfort>,
                    val dressing: List<Dressing>,
                    val ultraviolet: List<Ultraviolet>
            ) {
                data class CarWashing(
                        val date: Date,
                        val desc: String,
                        val index: String
                )

                data class ColdRisk(
                        val date: Date,
                        val desc: String,
                        val index: String
                )

                data class Comfort(
                        val date: Date,
                        val desc: String,
                        val index: String
                )

                data class Dressing(
                        val date: Date,
                        val desc: String,
                        val index: String
                )

                data class Ultraviolet(
                        val date: Date,
                        val desc: String,
                        val index: String
                )
            }

            data class Precipitation(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class Pressure(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class Skycon(
                    val date: Date,
                    val value: String
            )

            data class Skycon08h20h(
                    val date: Date,
                    val value: String
            )

            data class Skycon20h32h(
                    val date: Date,
                    val value: String
            )

            data class Temperature(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class Visibility(
                    val avg: Double,
                    val date: Date,
                    val max: Double,
                    val min: Double
            )

            data class Wind(
                    val avg: Avg,
                    val date: Date,
                    val max: Max,
                    val min: Min
            ) {
                data class Avg(
                        val direction: Double,
                        val speed: Double
                )

                data class Max(
                        val direction: Double,
                        val speed: Double
                )

                data class Min(
                        val direction: Double,
                        val speed: Double
                )
            }
        }
    }
}