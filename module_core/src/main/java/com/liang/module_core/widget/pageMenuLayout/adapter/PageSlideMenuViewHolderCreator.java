package com.liang.module_core.widget.pageMenuLayout.adapter;

import android.view.View;

/**
 * 创建日期: 2020/12/1 on 2:10 PM
 * 描述:
 * 作者: 杨亮
 */
public interface PageSlideMenuViewHolderCreator {

    PageSlideRvHolder createHolder(View itemView);

    int getLayoutId();
}