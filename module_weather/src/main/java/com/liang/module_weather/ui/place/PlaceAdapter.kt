package com.liang.module_weather.ui.place

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_core.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.logic.model.Place

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
            ToastUtil.onShowToast(parent.context, placeList[viewHolder.adapterPosition].name)
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