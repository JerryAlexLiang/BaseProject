package com.liang.module_weather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.WeatherActivity
import com.liang.module_weather.logic.network.WeatherConstant
import com.liang.module_weather.ui.weather.WeatherContainerActivity
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.android.synthetic.main.weather_layout_base_actionbar_default.*

/**
 * 创建日期:2020/8/26 on 5:21 PM
 * 描述: 城市搜索界面
 * 作者: 杨亮
 */
class PlaceFragment : Fragment() {

    private lateinit var placeAdapter: PlaceAdapter

    //1、使用lazy函数这种懒加载技术来获取PlaceViewModel的实例
    //这是一种非常棒的写法，允许我们在整个类中随时使用viewModel这个变量，而完全不用关心它何时初始化、是否为空等前提条件
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    companion object {
        private const val TAG = "PlaceFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is WeatherActivity && viewModel.isPlaceSaved()) {
            val savedPlace = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherContainerActivity::class.java).apply {
                putExtra(WeatherConstant.LOCATION_LNG, savedPlace.location.lng)
                putExtra(WeatherConstant.LOCATION_LAT, savedPlace.location.lat)
                putExtra(WeatherConstant.PLACE_NAME, savedPlace.name)
            }
            startActivity(intent)
            this.activity?.finish()
            return
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        rvCityPlaces.layoutManager = linearLayoutManager
        placeAdapter = PlaceAdapter(this, viewModel.placeList)
        rvCityPlaces.adapter = placeAdapter

        base_actionbar_left_icon.visibility = View.VISIBLE

        base_actionbar_left_icon.setOnClickListener {
            activity?.finish()
        }

        base_actionbar_left_tv.visibility = View.GONE

        edit_search_view.visibility = View.VISIBLE

        edit_search_view.setOnSearchTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
                ToastUtil.onShowTrueToast(context, "搜索$content")
            } else {
                rvCityPlaces.visibility = View.INVISIBLE
                viewModel.placeList.clear()
                placeAdapter.notifyDataSetChanged()
            }
        }

//        edit_search_view.setOnSearchClickListener {
//            val content = edit_search_view.text.toString().trim()
//            if (content.isNotEmpty()) {
//                viewModel.searchPlaces(content)
//                ToastUtil.onShowTrueToast(context, "搜索$content")
//            } else {
//                rvCityPlaces.visibility = View.INVISIBLE
//                viewModel.placeList.clear()
//                placeAdapter.notifyDataSetChanged()
//            }
//        }


        // 当需要观察 view 相关的 LiveData ，可以在 onCreateView()、onViewCreated() 或
        // onActivityCreated() 中 LiveData observe 方法中传入 viewLifecycleOwner 而不是传入 this

//        viewModel.placeLiveData.observe(this) { result ->
        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                rvCityPlaces.visibility = View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
                ToastUtil.onShowTrueToast(context, "查找到${places.size}条数据")
                LogUtil.d(TAG, viewModel.placeList.toString())
            } else {
                ToastUtil.onShowErrorToast(context, "未能查询到任何地点")
            }
        }

    }
}