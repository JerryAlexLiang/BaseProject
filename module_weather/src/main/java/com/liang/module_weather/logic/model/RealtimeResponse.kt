package com.liang.module_weather.logic.model

/**
 * 创建日期: 2020/8/27 on 6:01 PM
 * 描述: 实时天气
 * 这里将所有的数据模型类都定义在了RealtimeResponse的内部，
 * 这样可以防止出现和其它接口的数据模型类有同名冲突的情况
 * 作者: 杨亮
 */
data class RealtimeResponse(
        val api_status: String, // active
        val api_version: String, // v2.5
        val lang: String, // zh_CN
        val location: List<Double>,
        val result: Result,
        val server_time: Int, // 1598581713
        val status: String, // ok
        val timezone: String, // Asia/Shanghai
        val tzshift: Int, // 28800
        val unit: String // metric
) {
    data class Result(
            val primary: Int, // 0
            val realtime: Realtime
    ) {
        data class Realtime(
                val air_quality: AirQuality,
                val apparent_temperature: Double, // 29.6
                val cloudrate: Double, // 0.0
                val dswrf: Double, // 540.0
                val humidity: Double, // 0.5
                val life_index: LifeIndex,
                val precipitation: Precipitation,
                val pressure: Double, // 99960.7
                val skycon: String, // CLEAR_DAY
                val status: String, // ok
                val temperature: Double, // 28.0
                val visibility: Double, // 29.8
                val wind: Wind
        ) {
            data class AirQuality(
                    val aqi: Aqi,
                    val co: Double, // 0.6
                    val description: Description,
                    val no2: Double, // 39.0
                    val o3: Double, // 52.0
                    val pm10: Double, // 33.0
                    val pm25: Double, // 12.0
                    val so2: Double // 1.0
            ) {
                data class Aqi(
                        val chn: Double, // 33.0
                        val usa: Double // 50.0
                )

                data class Description(
                        val chn: String, // 优
                        val usa: String // 优
                )
            }

            data class LifeIndex(
                    val comfort: Comfort,
                    val ultraviolet: Ultraviolet
            ) {
                data class Comfort(
                        val desc: String, // 热
                        val index: Int // 3
                )

                data class Ultraviolet(
                        val desc: String, // 很强
                        val index: Double // 9.0
                )
            }

            data class Precipitation(
                    val local: Local,
                    val nearest: Nearest
            ) {
                data class Local(
                        val datasource: String, // radar
                        val intensity: Double, // 0.0
                        val status: String // ok
                )

                data class Nearest(
                        val distance: Double, // 157.34
                        val intensity: Double, // 0.1875
                        val status: String // ok
                )
            }

            data class Wind(
                    val direction: Double, // 345.0
                    val speed: Double // 3.24
            )
        }
    }
}