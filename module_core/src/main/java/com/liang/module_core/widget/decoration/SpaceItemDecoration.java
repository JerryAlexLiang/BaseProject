package com.liang.module_core.widget.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期: 2021/7/27 on 3:40 PM
 * 描述:
 * 作者: 杨亮
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int right;
    private int top;
    private int bottom;

    public SpaceItemDecoration() {
    }

    public SpaceItemDecoration setLeft(int left) {
        this.left = left;
        return this;
    }

    public SpaceItemDecoration setRight(int right) {
        this.right = right;
        return this;
    }

    public SpaceItemDecoration setTop(int top) {
        this.top = top;
        return this;
    }

    public SpaceItemDecoration setBottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = top;
        outRect.bottom = bottom;
        outRect.left = left;
        outRect.right = right;
    }

} 
