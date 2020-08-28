package com.liang.module_weather.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_core.constant.Constant
import com.liang.module_core.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.WeatherActivity
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.network.WeatherConstant
import com.liang.module_weather.ui.weather.WeatherContainerActivity

/**
 * 创建日期: 2020/8/27 on 3:31 PM
 * 描述: 彩云天气城市搜索列表适配器
 * 作者: 杨亮
 */
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_rv_place, parent, false)
        val viewHolder = MyViewHolder(view)

        viewHolder.itemView.setOnClickListener {

            val adapterPosition = viewHolder.adapterPosition
            val place = placeList[adapterPosition]

            ToastUtil.onShowToast(parent.context, place.name)

            val intent = Intent(parent.context, WeatherContainerActivity::class.java).apply {
                putExtra(WeatherConstant.LOCATION_LNG, place.location.lng)
                putExtra(WeatherConstant.LOCATION_LAT, place.location.lat)
                putExtra(WeatherConstant.PLACE_NAME, place.name)
            }
            fragment.startActivity(intent)
//            fragment.activity?.finish()
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