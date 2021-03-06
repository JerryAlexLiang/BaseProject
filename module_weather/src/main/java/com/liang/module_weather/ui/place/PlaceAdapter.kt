package com.liang.module_weather.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_core.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.network.WeatherConstant
import com.liang.module_weather.ui.weather.WeatherContainerActivity
import kotlinx.android.synthetic.main.activity_weather_container.*

/**
 * 创建日期: 2020/8/27 on 3:31 PM
 * 描述: 彩云天气城市搜索列表适配器
 * 作者: 杨亮
 */
//class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_rv_place, parent, false)
        val viewHolder = MyViewHolder(view)

        viewHolder.itemView.setOnClickListener {

            val adapterPosition = viewHolder.adapterPosition
            val place = placeList[adapterPosition]

            ToastUtil.onShowDefaultToast(parent.context, place.name)

            val activity = fragment.activity
            if (activity is WeatherContainerActivity) {
                //如果当前是WeatherContainerActivity，则不需要再次跳转，只需关闭DrawerLayout，并请求新数据并刷新界面即可
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherContainerActivity::class.java).apply {
                    putExtra(WeatherConstant.LOCATION_LNG, place.location.lng)
                    putExtra(WeatherConstant.LOCATION_LAT, place.location.lat)
                    putExtra(WeatherConstant.PLACE_NAME, place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            //存储点击的Item对应的Place数据
//            (fragment as PlaceFragment).viewModel.savePlace(place)
            fragment.viewModel.savePlace(place)
        }

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place = placeList[position]
        holder.tvPlaceName.text = place.name
        holder.tvPlaceAddress.text = "地址: ${place.address}"
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlaceName: TextView = view.findViewById(R.id.tvPlaceName)
        val tvPlaceAddress: TextView = view.findViewById(R.id.tvPlaceAddress)
    }
}