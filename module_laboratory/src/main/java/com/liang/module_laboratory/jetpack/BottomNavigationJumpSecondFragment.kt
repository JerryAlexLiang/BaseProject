package com.liang.module_laboratory.jetpack

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_laboratory.R
import com.liang.module_laboratory.jetpack.paging.PagingFooterAdapter
import com.liang.module_laboratory.jetpack.paging.PagingViewModel
import com.liang.module_laboratory.jetpack.paging.ResponsePagingAdapter
import com.liang.module_laboratory.jetpack.paging.ResponsePagingAdapter2
import kotlinx.android.synthetic.main.fragment_bottom_navigation_jump_second.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 创建日期:2021/7/22 on 4:09 PM
 * 描述: Paging3
 * 使用Paging3+RecyclerView 的列表分页功能，可以不断往下滑，Paging3会自动加载更多的数据，仿佛让你永远也滑不到头一样。
 * 总结一下，相比于传统的分页实现方案，Paging3将一些琐碎的细节进行了隐藏，比如你不需要监听列表的滑动事件，
 * 也不需要知道知道何时应该加载下一页的数据，这些都被Paging3封装掉了。我们只需要按照Paging3搭建好的框架去编写逻辑实现，告诉Paging3如何去加载数据，其他的事情Paging3都会帮我们自动完成。
 * 作者: 杨亮
 */
class BottomNavigationJumpSecondFragment : MVVMBaseFragment() {

    private lateinit var responsePagingAdapter: ResponsePagingAdapter

    private lateinit var responsePagingAdapter2: ResponsePagingAdapter2

    private val viewModel: PagingViewModel by lazy { ViewModelProvider(this).get(PagingViewModel::class.java) }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_bottom_navigation_jump_second
    }

    override fun initView(rootView: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(true, false, false,
                true, false, false, false)
        baseActionBarWidget.setActionBarTitle(context, "Paging3", R.color.colorWhite)
        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)

        baseActionBarWidget.setOnLeftButtonClickListener {
            findNavController().popBackStack()
        }

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvPaging.layoutManager = linearLayoutManager

//        responsePagingAdapter = ResponsePagingAdapter()
//        //调用RepoAdapter的withLoadStateFooter()函数即可将FooterAdapter集成到RepoAdapter当中
//        //另外注意这里使用Lambda表达式来作为传递给FooterAdapter的函数类型参数，在Lambda表示式中，调用RepoAdapter的retry()函数即可重新加载。
////        rvPaging.adapter = responsePagingAdapter.withLoadStateFooter(PagingFooterAdapter { responsePagingAdapter.retry() })
//        rvPaging.adapter = responsePagingAdapter.withLoadStateFooter(PagingFooterAdapter(retry = { responsePagingAdapter.retry() }))


        responsePagingAdapter2 = ResponsePagingAdapter2()
        //调用RepoAdapter的withLoadStateFooter()函数即可将FooterAdapter集成到RepoAdapter当中
        //另外注意这里使用Lambda表达式来作为传递给FooterAdapter的函数类型参数，在Lambda表示式中，调用RepoAdapter的retry()函数即可重新加载。
        rvPaging.adapter = responsePagingAdapter2.withLoadStateFooter(footer = PagingFooterAdapter(retry = { responsePagingAdapter2.retry() }))

        setData()

        initLoadStateListener()

    }

    private fun initLoadStateListener() {
//        responsePagingAdapter.addLoadStateListener {
//            when (it.refresh) {
//                is LoadState.NotLoading -> {
//                    hideProgressDialog()
//                }
//
//                is LoadState.Loading -> {
//                    showProgressDialog("Loading...", true)
//                }
//
//                is LoadState.Error -> {
//                    val error = it.refresh as LoadState.Error
//                    onShowErrorToast("Load Error: $error")
//                    hideProgressDialog()
//                }
//            }
//        }

        responsePagingAdapter2.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    hideProgressDialog()
                }

                is LoadState.Loading -> {
                    showProgressDialog("Loading...", true)
                }

                is LoadState.Error -> {
                    val error = it.refresh as LoadState.Error
                    onShowErrorToast("Load Error: $error")
                    hideProgressDialog()
                }
            }
        }
    }

    /**
     * 1、这里最重要的一段代码就是调用了RepoAdapter的submitData()函数。这个函数是触发Paging3分页功能的核心，调用这个函数之后，Paging3就开始工作了。
     *
     * 2、submitData()接收一个PagingData参数，这个参数我们需要调用ViewModel中返回的Flow对象的collect()函数才能获取到，
     * collect()函数有点类似于Rxjava中的subscribe()函数，总之就是订阅了之后，消息就会源源不断往这里传。
     *
     * 3、不过由于collect()函数是一个挂起函数，只有在协程作用域中才能调用它，因此这里又调用了lifecycleScope.launch()函数来启动一个协程。
     */
    private fun setData() {
//        lifecycleScope.launch {
//            viewModel.getPagingData().collect {
//                responsePagingAdapter.submitData(it)
//            }
//        }

        lifecycleScope.launch {
            viewModel.getPagingData2().collect {
                responsePagingAdapter2.submitData(it)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = BottomNavigationJumpSecondFragment()
    }
}