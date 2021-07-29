package com.liang.module_core.widget.behavior;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.annotations.NonNull;

/**
 * 创建日期: 2021/7/29 on 10:54 AM
 * 描述: 自定义BeHavior来滑动RecyclerView,浮动按钮的显示和隐藏
 * 实现原理：翻看源码可知CoordinatorLayout是继承自ViewGroup。
 * 通过协调并调度里面的子控件或者布局来实现触摸（一般是指滑动）产生一些相关的动画效果。所以我们通过设置view的Behavior来实现触摸的动画调度。
 * 作者: 杨亮
 */
public class FabBehavior extends FloatingActionButton.Behavior {

    private boolean visible = true;//是否可见

    public FabBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target,
                                       int nestedScrollAxes) {
        // 当观察的View（RecyclerView）发生滑动的开始的时候回调的
        //nestedScrollAxes:滑动关联轴， 我们现在只关心垂直的滑动。
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
                target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed);
        // 当观察的view滑动的时候回调的
        //根据情况执行动画
        if (dyConsumed > 0 && visible) {
            //show
            visible = false;
            onHide(child);
        } else if (dyConsumed < 0) {
            //hide
            visible = true;
            onShow(child);
        }
    }

    public void onHide(FloatingActionButton fab) {
        // 隐藏动画
        ViewCompat.animate(fab).scaleX(0f).scaleY(0f).start();
    }

    public void onShow(FloatingActionButton fab) {
        // 显示动画
        ViewCompat.animate(fab).scaleX(1f).scaleY(1f).start();
    }

}

