package com.liang.module_laboratory.jetpack

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.activity_jetpack_demo.*
import org.jetbrains.anko.startActivity


/**
 * 创建日期:2021/7/14 on 5:07 PM
 * 描述: JetPackDemo
 * 作者: 杨亮
 */
@Suppress("UNCHECKED_CAST")
class JetpackDemoActivity : MVVMBaseActivity() {

    //    private var currentSecond: MutableLiveData<Int?>? = null
    private var currentSecond: LiveData<Int?>? = null
    private val timerViewModel: TimerViewModel by lazy { ViewModelProvider(this).get(TimerViewModel::class.java) }
    private val timeLiveDataViewModel: TimeLiveDataViewModel by lazy { ViewModelProvider(this).get(TimeLiveDataViewModel::class.java) }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, JetpackDemoActivity::class.java)
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
        return R.layout.activity_jetpack_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
        initViewModel()
    }

    private fun initViewModel() {
        //使用ViewModel，不仅将界面和数据从代码上进行了分离，而且不再需要关心屏幕旋转带来的数据的丢失和获取问题。
        // 也许你会说onSaveInstanceState() 方法同样可以解决屏幕旋转带来的数据丢失问题，但它只能保存少量的能支持序列化的数据，
        // 而ViewModel没有这个限制，它能支持页面中所有的数据。但要注意的是，ViewModel不支持数据的持久化，当界面彻底销毁，ViewModel及其数据也就不存在了。
//        val timerViewModel: TimerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        timerViewModel.setOnTimeChangeListener(object : TimerViewModel.OnTimeChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onTimeChanged(second: Int) {
                //更新UI界面
                runOnUiThread {
                    btnTvTime.text = "Time: $second"
                }
            }
        })

        //得到ViewModel中的LiveData
//        currentSecond = timeLiveDataViewModel.getCurrentSecond() as MutableLiveData<Int?>?
        currentSecond = timeLiveDataViewModel.getCurrentSecond()
        //通过LiveData.observe()实现对ViewModel中数据变化的观察
        currentSecond?.observe(this, {
            //更新UI界面
            tvLiveDataTimer.text = "Time: $it"
        })
    }

    private fun initView() {
        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(true, true, false,
                true, false, true, false)
//        baseActionBarWidget.setActionBarTitle("JetPackDemo", ContextCompat.getColor(this,R.color.colorBlue))
        baseActionBarWidget.setActionBarTitle(this,"JetPackDemo", R.color.colorBlue)

        baseActionBarWidget.setOnLeftButtonClickListener {
            ToastUtil.showShortToast("左侧按钮1")
            finish()
        }
        baseActionBarWidget.setOnLeft2ButtonClickListener {
            ToastUtil.showShortToast("左侧按钮2")
            finish()
        }
        baseActionBarWidget.setOnRightButtonClickListener {
            ToastUtil.showShortToast("右侧按钮")
        }

//        //Convert to lambda
//        baseActionBarWidget.setOnRightButtonClickListener(object :BaseActionBarWidget.OnRightButtonClickListener{
//            override fun onRightButtonClick(view: View?) {
//            }
//
//        })

//        baseActionBarWidget.setActionBarBackgroundColor(resources.getColor(R.color.colorBlue))
//        baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)
//        baseActionBarWidget.setActionBarNetBackground(this,"https://img0.baidu.com/it/u=2109178944,253818567&fm=26&fmt=auto&gp=0.jpg")
        baseActionBarWidget.setActionBarNetBackground(this, "https://img1.baidu.com/it/u=2620121525,3057821586&fm=26&fmt=auto&gp=0.jpg")
    }

    private fun initListener() {
        setOnClickListener(btnLifeCycleDemo, btnTvTime, btnStartLiveDataTimer, btnResetTime, btnNavigation) {
            when (this) {
                btnLifeCycleDemo -> {
                    LifeCycleDemoActivity.actionStart(this@JetpackDemoActivity)
                }

                btnTvTime -> {
                    timerViewModel.startTiming()
                }

                btnStartLiveDataTimer -> {
                    timeLiveDataViewModel.startTiming()
                }

                btnResetTime -> {
                    //通过LiveData.setValue / LiveData.postValue 实现对ViewModel中数据的更新
                    //postValue()是在非UI线程中使用，如果在UI线程中，则使用setValue()方法
//                    currentSecond?.value = 0
                    timeLiveDataViewModel.resetTime()
                }

                btnNavigation -> {
                    startActivity<NavigationDemoActivity>()
                }

                else -> {

                }
            }
        }

    }


}