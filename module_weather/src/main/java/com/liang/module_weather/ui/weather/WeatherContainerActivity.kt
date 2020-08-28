package com.liang.module_weather.ui.weather

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.liang.module_core.jetpack.JetPackActivity
import com.liang.module_core.mvp.MVPBasePresenter
import com.liang.module_core.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.logic.model.Weather
import com.liang.module_weather.logic.model.getSky
import com.liang.module_weather.logic.network.WeatherConstant
import com.scwang.smartrefresh.header.*
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.header.FalsifyHeader
import com.scwang.smartrefresh.layout.header.TwoLevelHeader
import kotlinx.android.synthetic.main.activity_weather_container.*
import kotlinx.android.synthetic.main.layout_weather_life_index.*
import kotlinx.android.synthetic.main.layout_weather_forecast.*
import kotlinx.android.synthetic.main.weather_item_now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherContainerActivity : JetPackActivity() {

    companion object {
        private const val TAG = "WeatherContainerActivity"
    }

    //1、使用lazy函数这种懒加载技术来获取ViewModel的实例
    //这是一种非常棒的写法，允许我们在整个类中随时使用viewModel这个变量，而完全不用关心它何时初始化、是否为空等前提条件
//    private val viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    private val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_weather_container)
        initView()

        getIntentData()

        refreshWeather()

//        smart_refresh_layout.autoRefresh()

        initRefreshListener()

        observeData()
    }

    private fun initRefreshListener() {
        smart_refresh_layout.setOnRefreshListener {
            refreshWeather()
        }
    }

    private fun getIntentData() {
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra(WeatherConstant.LOCATION_LNG) ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra(WeatherConstant.LOCATION_LAT) ?: ""
        }

        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra(WeatherConstant.PLACE_NAME) ?: ""
        }
    }

    private fun initView() {
        smart_refresh_layout.setRefreshHeader(PhoenixHeader(this)) //大楼动画

//        smart_refresh_layout.setRefreshHeader(MaterialHeader(this)) //经典Swip

    }

    private fun observeData() {
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                ToastUtil.onShowErrorToast(this, "无法成功获取天气信息")
                result.exceptionOrNull()?.printStackTrace()
            }
            smart_refresh_layout.finishRefresh()
        })
    }

    private fun showWeatherInfo(weather: Weather) {
        //城市名称
        tvPlaceName.text = viewModel.placeName
        //实时天气
        setRealTimeInfo(weather)
        //未来天气
        setForecastInfo(weather)
        //生活指数（当天）
        setRealTimeLifeInfo(weather)
        //显示布局
        scrollWeatherLayout.visibility = View.VISIBLE
    }

    private fun setRealTimeLifeInfo(weather: Weather) {
        // 填充layout_life_index.xml布局中的数据
        //生活指数
        val lifeIndex = weather.daily.life_index
        //感冒指数
        val coldRiskIndex = lifeIndex.coldRisk[0].desc
        //紫外线指数
        val ultravioletIndex = lifeIndex.ultraviolet[0].desc
        //穿衣指数
        val dressingIndex = lifeIndex.dressing[0].desc
        //洗车指数
        val carWashingIndex = lifeIndex.carWashing[0].desc
        tvColdRisk.text = coldRiskIndex
        tvUltraviolet.text = ultravioletIndex
        tvDressing.text = dressingIndex
        tvCarWashing.text = carWashingIndex
    }

    /**
     * 使用一个for-in循环来处理每天的天气信息
     * 在循环中动态加载layout_weather_forecast.xml布局并设置相应的数据，然后添加到父布局中
     */
    private fun setForecastInfo(weather: Weather) {
        //未来天气
        val daily = weather.daily
        // 填充layout_weather_forecast.xml布局中的数据
        //清空父布局中的子布局
        llForecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val forecastItemView = layoutInflater.inflate(R.layout.weather_item_forecast, llForecastLayout, false)
            val tvDateInfo = forecastItemView.findViewById(R.id.tvDateInfo) as TextView
            val ivSkyIcon = forecastItemView.findViewById(R.id.ivSkyIcon) as ImageView
            val tvSkyInfo = forecastItemView.findViewById(R.id.tvSkyInfo) as TextView
            val tvTemperatureInfo = forecastItemView.findViewById(R.id.tvTemperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            //日期
            tvDateInfo.text = simpleDateFormat.format(skycon.date)
            //天气
            val sky = getSky(skycon.value)
            ivSkyIcon.setImageResource(sky.icon)
            tvSkyInfo.text = sky.info
            //气温范围
            val tempScope = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            tvTemperatureInfo.text = tempScope
            //添加View到父布局中
            llForecastLayout.addView(forecastItemView)
        }
    }

    private fun setRealTimeInfo(weather: Weather) {
        //实时天气
        val realtime = weather.realtime
        //填充layout_weather_now.xml布局中的数据
        //实时气温
        val currentTemp = "${realtime.temperature.toInt()} ℃"
        tvCurrentTemp.text = currentTemp
        //天气状况
        tvCurrentSky.text = getSky(realtime.skycon).info
        ivRealSkyIcon.setImageResource(getSky(realtime.skycon).icon)
        //空气指数
        val currentPM25 = "空气指数 ${realtime.air_quality.aqi.chn.toInt()}  ${realtime.air_quality.description.chn}"
        tvCurrentAQI.text = currentPM25
        //气温范围
        val tempRealScope = "${weather.daily.temperature[0].min.toInt()} ~ ${weather.daily.temperature[0].max.toInt()} ℃"
        tvRealTempScope.text = tempRealScope
        //风速
        val windSpeed = "${realtime.wind.speed} 级"
        tvWind.text = windSpeed
        //实时天气背景图
        //rlNowLayout
        rlNowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
    }

    private fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return true
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun createPresenter(): MVPBasePresenter<*>? {
        return null
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_weather_container
    }
}