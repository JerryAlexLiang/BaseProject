package com.liang.module_gank.ui.nice_girl

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.widget.decoration.SpaceItemDecoration
import com.liang.module_gank.R
import kotlinx.android.synthetic.main.new_gank_girl_fragment2.*

/**
 * 创建日期:2021/7/23 on 4:50 PM
 * 描述: Gank新版api 美女颜如玉
 * 作者: 杨亮
 */
class NewGankGirlFragment : MVVMBaseFragment() {

    companion object {

        @JvmStatic
        fun newInstance() = NewGankGirlFragment()
    }

    private val PAGE_START = 1
    private var currPage = PAGE_START
    private var isStaggeredGridModel = true

    //    private lateinit var newGankGirlAdapter: NewGankGirlAdapter
    private lateinit var newGirlAdapter: NewGirlRvAdapter

    private lateinit var viewModel: NewGankGirlViewModel

    override fun createViewLayoutId(): Int {
//        return R.layout.new_gank_girl_fragment
        return R.layout.new_gank_girl_fragment2
    }

    override fun initView(rootView: View?) {
    }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return true
    }

    override fun isSetRefreshFooter(): Boolean {
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewGankGirlViewModel::class.java)

        if (isStaggeredGridModel) {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvNiceGirl.layoutManager = staggeredGridLayoutManager
        } else {
            val linearLayoutManager = LinearLayoutManager(context)
            rvNiceGirl.layoutManager = linearLayoutManager
        }

        val decoration = SpaceItemDecoration()
                .setLeft(12)
                .setRight(12)
                .setTop(12)
                .setBottom(12)
        rvNiceGirl.addItemDecoration(decoration)

        newGirlAdapter = NewGirlRvAdapter(R.layout.item_rv_new_girl)
//        newGankGirlAdapter = NewGankGirlAdapter(R.layout.item_rv_new_nice_gank)
//        newGankGirlAdapter = NewGankGirlAdapter(R.layout.item_rv_new_nice_gank2)

        rvNiceGirl.adapter = newGirlAdapter

        initListener()

//        observe()

        observe2()

    }

    private fun initListener() {
        smart_refresh_layout.setOnRefreshListener {
            currPage = PAGE_START
//            viewModel.getNiceGankGirlData(currPage)
            viewModel.getNewNiceGirlData(currPage)
        }

        smart_refresh_layout.setOnLoadMoreListener {
            currPage++
//            viewModel.getNiceGankGirlData(currPage)
            viewModel.getNewNiceGirlData(currPage)
        }

        smart_refresh_layout.autoRefresh()

        fabSwitchModel.setOnClickListener {
            if (isStaggeredGridModel) {
                val linearLayoutManager = LinearLayoutManager(context)
                rvNiceGirl.layoutManager = linearLayoutManager
                isStaggeredGridModel = false
                ToastUtil.showShortToast("切换列表模式")
            } else {
                val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                rvNiceGirl.layoutManager = staggeredGridLayoutManager
                isStaggeredGridModel = true
                ToastUtil.showShortToast("切换流式布局模式")
            }
        }
    }

//    private fun observe() {
//        viewModel.niceGankGirlBeanLiveData.observe(viewLifecycleOwner) { result ->
//            val girls = result.getOrNull()
//            if (girls != null) {
//                if (currPage == PAGE_START) {
//                    newGankGirlAdapter.data.clear()
//                    viewModel.girlsList.clear()
//                    viewModel.girlsList.addAll(girls)
//                    newGankGirlAdapter.setNewInstance(viewModel.girlsList)
//                    LogUtil.d(TAG, "data refresh : data size: " + viewModel.girlsList.size + "  " + GsonUtils.toJson(viewModel.girlsList))
//                } else {
//                    //请求更多数据,直接添加list中
//                    viewModel.girlsList.addAll(girls)
//                    newGankGirlAdapter.setDiffNewData(viewModel.girlsList)
//                    LogUtil.d(TAG, "data loadMore : data size: " + viewModel.girlsList.size + "  " + GsonUtils.toJson(viewModel.girlsList))
//                }
//
//                if (girls.size == 0 && currPage != PAGE_START) {
//                    onShowToast("没有更多数据了!")
//                    //设置是否在全部加载结束之后Footer跟随内容
//                    smart_refresh_layout.setNoMoreData(true)
//                    smart_refresh_layout.setEnableFooterFollowWhenNoMoreData(true)
//                } else {
//                    smart_refresh_layout.setEnableLoadMore(true)
//                }
//
//                smart_refresh_layout.finishRefresh()
//                smart_refresh_layout.finishLoadMore()
//            } else {
//                newGankGirlAdapter.setEmptyView(R.layout.core_rl_empty_container_view)
//            }
//        }
//    }

    private fun observe2() {
        viewModel.newGirlBeanLiveData.observe(viewLifecycleOwner) { result ->
            val girls = result.getOrNull()
            if (girls != null) {
                if (currPage == PAGE_START) {
                    newGirlAdapter.data.clear()
                    viewModel.newGrilsList.clear()
                    viewModel.newGrilsList.addAll(girls)
                    newGirlAdapter.setNewInstance(viewModel.newGrilsList)
                    LogUtil.d(TAG, "data refresh : data size: " + viewModel.newGrilsList.size + "  " + GsonUtils.toJson(viewModel.newGrilsList))
                } else {
                    //请求更多数据,直接添加list中
                    viewModel.newGrilsList.addAll(girls)
                    newGirlAdapter.setDiffNewData(viewModel.newGrilsList)
                    LogUtil.d(TAG, "data loadMore : data size: " + viewModel.newGrilsList.size + "  " + GsonUtils.toJson(viewModel.newGrilsList))
                }

                if (girls.size == 0 && currPage != PAGE_START) {
                    onShowToast("没有更多数据了!")
                    //设置是否在全部加载结束之后Footer跟随内容
                    smart_refresh_layout.setNoMoreData(true)
                    smart_refresh_layout.setEnableFooterFollowWhenNoMoreData(true)
                } else {
                    smart_refresh_layout.setEnableLoadMore(true)
                }

                smart_refresh_layout.finishRefresh()
                smart_refresh_layout.finishLoadMore()
            } else {
                newGirlAdapter.setEmptyView(R.layout.core_rl_empty_container_view)
            }
        }
    }

}