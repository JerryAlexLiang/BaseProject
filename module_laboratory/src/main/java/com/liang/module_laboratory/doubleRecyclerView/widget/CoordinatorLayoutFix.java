package com.liang.module_laboratory.doubleRecyclerView.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * 创建日期: 2021/5/25 on 3:38 PM
 * 描述:
 * 作者: 杨亮
 */
public class CoordinatorLayoutFix extends CoordinatorLayout {

    private OnInterceptTouchListener mListener;

    public interface OnInterceptTouchListener {
        void onIntercept();
    }

    public void setOnInterceptTouchListener(OnInterceptTouchListener listener) {
        mListener = listener;
    }

    public CoordinatorLayoutFix(@NonNull Context context) {
        super(context);
    }

    public CoordinatorLayoutFix(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatorLayoutFix(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mListener != null) {
            mListener.onIntercept();
        }
        return super.onInterceptTouchEvent(ev);
    }
}
