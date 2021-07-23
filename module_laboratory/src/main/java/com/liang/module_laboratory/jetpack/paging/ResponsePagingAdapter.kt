package com.liang.module_laboratory.jetpack.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_laboratory.R

/**
 * 创建日期: 2021/7/22 on 3:58 PM
 * 描述: 必须继承自PagingDataAdapter
 * 作者: 杨亮
 */
class ResponsePagingAdapter : PagingDataAdapter<PagingBean.ItemsBean, ResponsePagingAdapter.MyViewHolder>(COMPARATOR) {

    /**
     * 1、相比于一个传统的RecyclerView Adapter，这里最特殊的地方就是要提供一个COMPARATOR。
     * 因为Paging3在内部会使用DiffUtil来管理数据变化，所以这个COMPARATOR是必须的。如果你以前用过DiffUtil的话，对此应该不会陌生。
     *
     * 2、除此之外，我们并不需要传递数据源给到父类，因为数据源是由Paging3在内部自己管理的。
     * 同时也不需要重写getItemCount()函数了，原因也是相同的，有多少条数据Paging3自己就能够知道。
     */

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PagingBean.ItemsBean>() {

            override fun areItemsTheSame(oldItem: PagingBean.ItemsBean, newItem: PagingBean.ItemsBean): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: PagingBean.ItemsBean, newItem: PagingBean.ItemsBean): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_paging_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.tvPagingTitle.text = item.name
            holder.tvPagingContent.text = item.description
            holder.tvPagingStarScore.text = item.stargazers_count.toString()
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPagingTitle: TextView = itemView.findViewById(R.id.tvPagingTitle)
        val tvPagingContent: TextView = itemView.findViewById(R.id.tvPagingContent)
        val tvPagingStarScore: TextView = itemView.findViewById(R.id.tvPagingStarScore)
    }


}