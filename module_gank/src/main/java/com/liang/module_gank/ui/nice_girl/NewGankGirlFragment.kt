package com.liang.module_gank.ui.nice_girl

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.widget.decoration.SpaceItemDecoration
import com.liang.module_gank.R
import kotlinx.android.synthetic.main.new_gank_girl_fragment.*

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

    private lateinit var newGankGirlAdapter: NewGankGirlAdapter

    private lateinit var viewModel: NewGankGirlViewModel

    override fun createViewLayoutId(): Int {
        return R.layout.new_gank_girl_fragment
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

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvNiceGirl.layoutManager = staggeredGridLayoutManager

        val decoration = SpaceItemDecoration()
                .setLeft(12)
                .setRight(12)
                .setTop(12)
                .setBottom(12)
        rvNiceGirl.addItemDecoration(decoration)

        newGankGirlAdapter = NewGankGirlAdapter(R.layout.item_rv_new_nice_gank)
        rvNiceGirl.adapter = newGankGirlAdapter

//        viewModel.getNiceGankGirlData(PAGE_START)

        smart_refresh_layout.setOnRefreshListener {
            currPage = PAGE_START
            viewModel.getNiceGankGirlData(currPage)
        }

        smart_refresh_layout.setOnLoadMoreListener {
            currPage++;
            viewModel.getNiceGankGirlData(currPage)
        }

        smart_refresh_layout.autoRefresh()

        observe()

    }

    private fun observe() {
        viewModel.niceGankGirlBeanLiveData.observe(viewLifecycleOwner) { result ->
            val girls = result.getOrNull()
            if (girls != null) {
                if (currPage == PAGE_START) {
                    viewModel.girlsList.clear()
                    viewModel.girlsList.addAll(girls)
                    newGankGirlAdapter.setNewInstance(viewModel.girlsList)
                    LogUtil.d("nice", "data: " + GsonUtils.toJson(viewModel.girlsList))
                } else {
                    //请求更多数据,直接添加list中
                    viewModel.girlsList.addAll(girls)
                    newGankGirlAdapter.addData(viewModel.girlsList)
                }

                if (girls.size == 0 && currPage != PAGE_START) {
                    onShowToast("没有更多数据了!")
                    //设置是否在全部加载结束之后Footer跟随内容
                    smart_refresh_layout.setNoMoreData(true)
                    smart_refresh_layout.setEnableFooterFollowWhenNoMoreData(true)
                } else {
                    smart_refresh_layout.setEnableLoadMore(true);
                }

                smart_refresh_layout.finishRefresh()
                smart_refresh_layout.finishLoadMore()
            } else {
                newGankGirlAdapter.setEmptyView(R.layout.core_rl_empty_container_view)
            }


        }
    }

}