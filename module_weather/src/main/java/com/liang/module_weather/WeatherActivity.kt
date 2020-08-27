package com.liang.module_weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.liang.module_core.jetpack.JetPackActivity
import com.liang.module_core.mvp.MVPBasePresenter
/**
 * 创建日期:2020/8/27 on 5:02 PM
 * 描述: 天气界面
 * Android使用toolbar设置了fitSystemWindows = “true”的时候当edittext弹出了软键盘时toolbar被拉伸的问题
 * 在manifest下给问题activity添加一个属性问题
 * android:windowSoftInputMode = “adjustPan”
 * 作者: 杨亮
 */
class WeatherActivity : JetPackActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, WeatherActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_weather)
        ARouter.getInstance().inject(this)
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

    override fun createPresenter(): MVPBasePresenter<*>? {
        return null
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_weather
    }
}