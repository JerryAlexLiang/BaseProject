package com.liang.module_laboratory.jetpack.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * 创建日期: 2021/7/22 on 2:51 PM
 * 描述:
 * 作者: 杨亮
 */
object PagingRepository {

    //pageSize:定义从 PagingSource 一次加载的项目数
    private const val PAGE_SIZE = 1

    private val pagingService = PagingService.create()

    private val config: PagingConfig = PagingConfig(PAGE_SIZE)

    private val factory: ResponsePagingSource = ResponsePagingSource(pagingService)

    /**
     * 定义了一个getPagingData()函数，这个函数的返回值是Flow<PagingData<PagingBean.ItemsBean>>，注意除了PagingBean.ItemsBean部分是可以改的，其他部分都是固定的
     */

    fun getPagingData(): Flow<PagingData<PagingBean.ItemsBean>> {

        //协程的Flow，可以简单将它理解成协程中对标RxJava的一项技术
        //这里创建了一个Pager对象，并调用.flow将它转换成一个Flow对象
//        return Pager(
//                config = PagingConfig(PAGE_SIZE),
//                pagingSourceFactory = { ResponsePagingSource(pagingService) }
//        ).flow

        return Pager(
                config = config,
                pagingSourceFactory = { factory }
        ).flow
    }

    fun getPagingData2(): Flow<PagingData<ArticleBean>> {
        //协程的Flow，可以简单将它理解成协程中对标RxJava的一项技术
        //这里创建了一个Pager对象，并调用.flow将它转换成一个Flow对象
        return Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { ResponsePagingSource2(pagingService) }
        ).flow


    }

}