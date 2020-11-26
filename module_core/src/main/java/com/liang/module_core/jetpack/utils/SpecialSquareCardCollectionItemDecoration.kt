package com.liang.module_core.jetpack.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_core.jetpack.utils.dp2px

/**
 * 创建日期: 2020/11/26 on 6:20 PM
 * 描述:
 * 作者: 杨亮
 */
class SpecialSquareCardCollectionItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val count = parent.adapter?.itemCount //item count
        val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        val spanCount = 2
        val lastRowFirstItemPosition = count?.minus(spanCount)   //最后一行,第一个item索引
//        val space = dp2px(2f)
//        val space = DensityUtil.dpToPx(parent.context,2)
        val space = dp2px(parent.context, 2f)

//        val rightCountSpace = dp2px(14f)
//        val rightCountSpace = DensityUtil.dpToPx(parent.context,14)
        val rightCountSpace = dp2px(parent.context, 14f)

        when (spanIndex) {
            0 -> {
                outRect.bottom = space
            }
            spanCount - 1 -> {
                outRect.top = space
            }
            else -> {
                outRect.top = space
                outRect.bottom = space
            }
        }
        when {
            position < spanCount -> {
                outRect.right = space
            }
            position < lastRowFirstItemPosition!! -> {
                outRect.left = space
                outRect.right = space
            }
            else -> {
                outRect.left = space
                outRect.right = rightCountSpace
            }
        }
    }
}