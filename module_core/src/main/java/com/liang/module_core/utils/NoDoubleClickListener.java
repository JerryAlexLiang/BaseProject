package com.liang.module_core.utils;

import android.view.View;

import java.util.Calendar;

/**
 * 创建日期: 2021/7/14 on 5:36 PM
 * 描述: 重写Android的OnClickListener 方法，判断两次点击事件间隔
 * 作者: 杨亮
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    /**
     * 方法一
     */
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
    }

    protected abstract void onNoDoubleClick(View v);

    /**
     * 方法二
     * 既然是为了防止连续点击，可以判断该按钮两次点击的时间间隔，如果两个点击的时间差小于某一个规定值，则不响应点击事件。
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }

        lastClickTime = time;

        return false;
    }

    //使用时调用
    //	onNoDoubleClick（View v）;
    //
    //	mShareLayout.setOnClickListener(new NoDoubleClickListener() {
    //  	  @Override
    //   	 protected void onNoDoubleClick(View v) {
    //
    //  	 }
    //});

}
