package com.liang.module_bluetooth.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.inuker.bluetooth.library.model.BleGattProfile
import com.inuker.bluetooth.library.search.SearchResult
import com.liang.module_bluetooth.R
import kotlinx.android.synthetic.main.ble_rv_adapter_device.view.*

/**
 * 创建日期: 2021/2/2 on 6:16 PM
 * 描述:
 * 作者: 杨亮
 */
class DeviceAdapter(layoutResId: Int) : BaseQuickAdapter<SearchResult, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: SearchResult) {
        holder.itemView.txt_name.text = item.name
        holder.itemView.txt_mac.text = item.address
        holder.itemView.txt_rssi.text = String.format("Rssi: %d", item.rssi)

        holder.itemView.btn_connect.setOnClickListener {
            if (mListener != null) {
                mListener!!.onConnect(item)
            }
        }

        holder.itemView.btn_disconnect.setOnClickListener {
            if (mListener != null) {
                mListener!!.onDisConnect(item)
            }
        }

        holder.itemView.btn_detail.setOnClickListener {
            if (mListener != null) {
                mListener!!.onDetail(item)
            }
        }

        if (isConnected) {
            holder.itemView.img_blue.setImageResource(R.drawable.ui_icon_ble_connected)
            holder.itemView.txt_name.setTextColor(-0xe2164a)
            holder.itemView.txt_mac.setTextColor(-0xe2164a)
            holder.itemView.layout_idle.visibility = View.GONE
            holder.itemView.layout_connected.visibility = View.VISIBLE
        } else {
            holder.itemView.img_blue.setImageResource(R.drawable.ui_icon_blue_remote)
            holder.itemView.txt_name.setTextColor(-0x1000000)
            holder.itemView.txt_mac.setTextColor(-0x1000000)
            holder.itemView.layout_idle.visibility = View.VISIBLE
            holder.itemView.layout_connected.visibility = View.GONE
        }
    }

    interface OnDeviceClickListener {
        fun onConnect(bleDevice: SearchResult?)
        fun onDisConnect(bleDevice: SearchResult?)
        fun onDetail(bleDevice: SearchResult?)
    }

    private var isConnected: Boolean = false
    private var mProfile: BleGattProfile? = null

    private var mListener: OnDeviceClickListener? = null

    fun setOnDeviceClickListener(listener: OnDeviceClickListener?) {
        mListener = listener
    }

    fun setDeviceConnect(mConnected: Boolean) {
        isConnected = mConnected
        notifyDataSetChanged()
    }

}