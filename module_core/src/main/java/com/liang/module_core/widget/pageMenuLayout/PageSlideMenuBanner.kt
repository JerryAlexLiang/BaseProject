package com.liang.module_core.widget.pageMenuLayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.liang.module_core.R
import com.liang.module_core.widget.pageMenuLayout.adapter.PageSlideRvAdapter
import com.liang.module_core.widget.pageMenuLayout.adapter.PageSlideMenuViewHolderCreator
import com.liang.module_core.widget.pageMenuLayout.adapter.PageViewPagerAdapter
import java.util.*
import kotlin.math.ceil

/**
 * 创建日期: 2020/12/1 on 2:07 PM
 * 描述: 分页菜单控件
 * 作者: 杨亮
 */
class PageSlideMenuBanner<T> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {
    private var mSlideMenuBannerViewPager: PageSlideMenuBannerViewPager? = null

    /**
     * 行数
     */
    private var mRowCount = DEFAULT_ROW_COUNT

    /**
     * 列数
     */
    private var mSpanCount = DEFAULT_SPAN_COUNT
    private fun initView(context: Context, attrs: AttributeSet?) {
        mSlideMenuBannerViewPager = PageSlideMenuBannerViewPager(context)
        addView(mSlideMenuBannerViewPager, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageSlideMenuBanner)
        if (typedArray != null) {
            mRowCount = typedArray.getInteger(R.styleable.PageSlideMenuBanner_page_menu_row_count, DEFAULT_ROW_COUNT)
            mSpanCount = typedArray.getInteger(R.styleable.PageSlideMenuBanner_page_menu_column_count, DEFAULT_SPAN_COUNT)
            typedArray.recycle()
        }
    }

    fun setPageData(data: List<T>, creatorSlide: PageSlideMenuViewHolderCreator) {
        setPageData(mRowCount, mSpanCount, data, creatorSlide)
    }

    /**
     * @return 菜单总页数
     */
    val pageCount: Int
        get() = if (mSlideMenuBannerViewPager != null && mSlideMenuBannerViewPager!!.adapter != null) {
            mSlideMenuBannerViewPager!!.adapter!!.count
        } else {
            0
        }

    /**
     * 页面滚动监听
     * @param pageListener
     */
    fun setOnPageListener(pageListener: ViewPager.OnPageChangeListener?) {
        if (mSlideMenuBannerViewPager != null) {
            mSlideMenuBannerViewPager!!.addOnPageChangeListener(pageListener!!)
        }
    }

    /**
     * 设置行数
     * @param rowCount
     */
    fun setRowCount(rowCount: Int) {
        mRowCount = rowCount
    }

    /**
     * 设置列数
     * @param spanCount
     */
    fun setSpanCount(spanCount: Int) {
        mSpanCount = spanCount
    }

    fun setPageData(rowCount: Int, spanCount: Int, data: List<T>, creatorSlide: PageSlideMenuViewHolderCreator) {
        var data = data
        if (data == null) {
            data = ArrayList()
        }
        mRowCount = rowCount
        mSpanCount = spanCount
        if (mRowCount == 0 || mSpanCount == 0) {
            return
        }
        val pageSize = mRowCount * mSpanCount
        val pageCount = ceil(data.size * 1.0 / pageSize).toInt()
        val viewList: MutableList<View> = ArrayList()
        for (index in 0 until pageCount) {
            val recyclerView = RecyclerView(this.context)
            recyclerView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            recyclerView.layoutManager = GridLayoutManager(this.context, mSpanCount)
            val entranceAdapter = PageSlideRvAdapter(creatorSlide, data, index, pageSize)
            recyclerView.adapter = entranceAdapter
            viewList.add(recyclerView)
        }
        val adapter = PageViewPagerAdapter(viewList)
        mSlideMenuBannerViewPager!!.adapter = adapter
    }

    companion object {
        private const val DEFAULT_ROW_COUNT = 2
        private const val DEFAULT_SPAN_COUNT = 5
    }

    init {
        initView(context, attrs)
    }
}