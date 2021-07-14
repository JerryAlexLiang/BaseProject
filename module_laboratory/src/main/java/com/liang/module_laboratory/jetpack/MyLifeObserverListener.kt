package com.liang.module_laboratory.jetpack

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.liang.module_core.utils.LogUtil

/**
 * 创建日期: 2021/7/14 on 3:16 PM
 * 描述: 目地是将这个功能从Activity中独立出去，减少耦合度的同时，又不影响对生命周期的监听。
 * 作者: 杨亮
 */
class MyLifeObserverListener(private val context: Activity, private val onLocationChangedListener: OnLocationChangedListener) : LifecycleObserver {

    private val TAG = this.javaClass.name

    /**
     * 当Activity执行onCreate()方法时，该方法会被自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        LogUtil.d(TAG, "执行onCreate()")
    }

    /**
     * 当Activity执行onStart()方法时，该方法会被自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        LogUtil.d(TAG, "执行onStart()")
    }

    /**
     * 当Activity 执行onResume()方法时，该方法会被自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        LogUtil.d(TAG, "执行onResume()  startGetLocation()")
    }

    /**
     * 当Activity 执行onPause()方法时，该方法会被自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        LogUtil.d(TAG, "执行onPause()  stopGetLocation()")
    }

    /**
     * 当Activity执行onStop()方法时，该方法会被自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        LogUtil.d(TAG, "执行onStop()")
    }

    /**
     * 当Activity执行onDestroy()方法时，该方法会被自动调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        LogUtil.d(TAG, "执行onDestroy()")
    }


    //模拟地理位置发生变化时，通过该接口通知调用者
    interface OnLocationChangedListener {
        // 其他一些业务代码
        fun onChanged(latitude: Double, longitude: Double)
    }
}