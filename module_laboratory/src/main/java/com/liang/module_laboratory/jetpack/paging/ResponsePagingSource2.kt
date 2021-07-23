package com.liang.module_laboratory.jetpack.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * 创建日期: 2021/7/22 on 2:39 PM
 * 描述: PagingSource，我们需要自定义一个子类去继承PagingSource，然后重写load()函数，并在这里提供对应当前页数的数据。
 * 在继承PagingSource时需要声明两个泛型类型，第一个类型表示页数的数据类型，我们没有特殊需求，所以直接用整型就可以了。
 * 第二个类型表示每一项数据（注意不是每一页）所对应的对象类型，这里使用刚才定义的Repo。
 * 作者: 杨亮
 */
class ResponsePagingSource2(private val pagingService: PagingService) : PagingSource<Int, ArticleBean>() {

    /**
     * 然后在load()函数当中，
     * 1、先通过params参数得到key，这个key就是代表着当前的页数。
     * 注意key是可能为null的，如果为null的话，我们就默认将当前页数设置为第一页。
     * 2、另外还可以通过params参数得到loadSize，表示每一页包含多少条数据，这个数据的大小我们可以在稍后设置。
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        try {
            //默认设置为第0页
            val page = params.key ?: 0
            val pageSize = params.loadSize
            //接下来调用刚才在PagingService中定义的searchPagingResponse()接口，并把page和pageSize传入，从服务器获取当前页所对应的数据
            val searchPagingResponse2 = pagingService.searchPagingResponse2(page)
            val datas = searchPagingResponse2.data?.datas

            //上一页的页数
            val prevKey = if (page > 1) page - 1 else null
            //下一页的页数
            val nextKey = if (datas?.isNotEmpty() == true) page + 1 else null

            //最后需要调用LoadResult.Page()函数，构建一个LoadResult对象并返回。
            //注意LoadResult.Page()函数接收3个参数，
            //第一个参数传入从响应数据解析出来的Repo列表即可，第二和第三个参数分别对应着上一页和下一页的页数
            //针对于上一页和下一页，我们还额外做了个判断，如果当前页已经是第一页或最后一页，那么它的上一页或下一页就为null
            return LoadResult.Page(datas!!, prevKey, nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleBean>): Int? {
        return null
    }


}