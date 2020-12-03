package com.liang.module_core.widget.pageMenuLayout.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期: 2020/12/1 on 2:10 PM
 * 描述:
 * 作者: 杨亮
 */
public abstract class PageSlideRvHolder<T> extends RecyclerView.ViewHolder {

    public PageSlideRvHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    protected abstract void initView(View itemView);

    public abstract void bindView(RecyclerView.ViewHolder holder,T data,int pos);
}
