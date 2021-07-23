package com.liang.module_laboratory.jetpack.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_core.utils.ToastUtil
import com.liang.module_laboratory.R

/**
 * 创建日期: 2021/7/22 on 5:05 PM
 * 描述: 创建一个FooterAdapter来作为RecyclerView的底部适配器，注意它必须继承自LoadStateAdapter
 * 作者: 杨亮
 */
class PagingFooterAdapter(val retry: () -> Unit) : LoadStateAdapter<PagingFooterAdapter.MyFooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyFooterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_paging_footer_item, parent, false)
        return MyFooterViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyFooterViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error) {
            holder.llLoadingContainer.visibility = View.GONE
            holder.footerBtnRetry.visibility = View.VISIBLE
            holder.footerBtnRetry.setOnClickListener {
                //使用Kotlin的高阶函数来给重试按钮注册点击事件，这样当点击重试按钮时，构造函数中传入的函数类型参数就会被回调，我们待会将在那里加入重试逻辑。
                retry()
            }
        } else if (loadState is LoadState.Loading) {
            holder.llLoadingContainer.visibility = View.VISIBLE
            holder.footerBtnRetry.visibility = View.GONE
            ToastUtil.showShortToast("Loading...")
        }
    }

    class MyFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llLoadingContainer: LinearLayout = itemView.findViewById(R.id.llLoadingContainer)
        val footerProgressBar: ProgressBar = itemView.findViewById(R.id.footerProgressBar)
        val footerBtnRetry: Button = itemView.findViewById(R.id.footerBtnRetry)
    }
}