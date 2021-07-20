package com.liang.module_core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 创建日期:2021/3/12 on 3:55 PM
 * 描述: Android自定义viewpager且高度自适应 - 滑动页面工具类
 * 作者: 杨亮
 */
public class AutoHeightViewPager extends ViewPager {

    private boolean scrollable = true;   //是否可滑动

    private int currentIndex;
    private int height;
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();
    public AutoHeightViewPager(@NonNull Context context) {
        super(context);
    }

    public AutoHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > currentIndex){
            View child = mChildrenViews.get(currentIndex);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        if (mChildrenViews.size() != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        if (height > 0) {
            setMeasuredDimension(getMeasuredWidth(), height);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        currentIndex = current;
        if (mChildrenViews.size() > current) {
            ViewGroup.LayoutParams layoutParams =  getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
            } else {
                layoutParams.height = height;
            }
            setLayoutParams(layoutParams);
        }
    }

    public void setViewForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollable) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isScrollable() {
        return scrollable;
    }

    /**
     * 外部接口控制页面的滑动
     *
     * @param scrollable
     */
    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(scrollable){
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
