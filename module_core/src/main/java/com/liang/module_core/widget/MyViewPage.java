package com.liang.module_core.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPage extends ViewPager {

    private int rightDistance = 950;

    public MyViewPage(@NonNull Context context) {
        this(context, null);
    }

    public MyViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getX() < rightDistance && getCurrentItem() == 0) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getX() < rightDistance && getCurrentItem() == 0) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setRightDistance(int distance) {
        this.rightDistance = distance * 12 / 13;
    }
}
