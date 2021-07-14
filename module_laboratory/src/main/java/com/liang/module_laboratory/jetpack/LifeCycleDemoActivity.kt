package com.liang.module_laboratory.jetpack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_laboratory.R

/**
 * 创建日期:2021/7/14 on 3:14 PM
 * 描述: 利用LifeCycle解耦组件
 * 作者: 杨亮
 */
class LifeCycleDemoActivity : MVVMBaseActivity() {

    private lateinit var lifeObserverListener: MyLifeObserverListener

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, LifeCycleDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_life_cycle_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifeObserverListener = MyLifeObserverListener(this, object : MyLifeObserverListener.OnLocationChangedListener {
            override fun onChanged(latitude: Double, longitude: Double) {
                //展示收到的的位置消息
            }
        })

        //将观察者与被观察者进行绑定
        lifecycle.addObserver(lifeObserverListener)
    }
}