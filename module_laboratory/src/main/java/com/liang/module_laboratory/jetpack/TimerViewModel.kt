package com.liang.module_laboratory.jetpack

import androidx.lifecycle.ViewModel
import java.util.*


/**
 * 创建日期: 2021/7/15 on 2:00 PM
 * 描述: ViewModel最重要的作用是将界面和数据分离，并且独立于Activity的重建
 * 作者: 杨亮
 */
class TimerViewModel : ViewModel() {

    private val TAG = this.javaClass.name
    private var timer: Timer? = null
    private var currentSecond = 0

    /**
     * 开始计时
     */
    fun startTiming() {
        if (timer == null) {
            currentSecond = 0
            timer = Timer()
            val timerTask = object : TimerTask() {
                override fun run() {
                    currentSecond++
                    onTimeChangeListener?.onTimeChanged(currentSecond)
                }
            }
            timer!!.schedule(timerTask, 1000, 1000);//延迟3秒执行
        }
    }

    /**
     * 通过接口的方式，完成去通知调用者；这种方式不是太好，更好的方式是通过LiveData组件来实现这一功能
     * 这样做是可行的，但如果要观察的数据很多，则需要定义大量的接口，代码显得冗余。
     * 为此，Android为我们提供了LiveData组件，帮助我们完成ViewModel与页面组件之间的通信。所以，LiveData通常是被放在ViewModel中使用。
     */
    interface OnTimeChangeListener {
        fun onTimeChanged(second: Int)
    }

    private var onTimeChangeListener: OnTimeChangeListener? = null

    fun setOnTimeChangeListener(onTimeChangeListener: OnTimeChangeListener?) {
        this.onTimeChangeListener = onTimeChangeListener
    }

    /**
     * 1、由于屏幕旋转而导致的Activity重建，该方法不会被调用;
     * 2、只有ViewModel已经没有任何Activity与之有关联，系统则会调用该方法，你可以在此清理资源
     * 3、ViewModel是一个抽象类，其中只有一个方法onCleared()，当ViewModel不再被需要的时候，也就是与之相关的Activity都被销毁时，
     * 该方法会被系统调用，我们可以在这个方法里面执行一些资源释放的操作，以免内存泄漏；
     * 4、注意：既然ViewModel的销毁是由系统来判断和执行的，那么系统是如何判断的呢？是根据Context引用。
     * 因此，我们在使用ViewModel的时候，千万不能从外面传入Activity，Fragment或者View之类的含有Context引用的东西，
     * 否则系统会认为该ViewModel还在使用中，从而无法被系统销毁回收，导致内存泄漏的发生。
     * 5、使用ViewModel的时候，不能将任何含有Context引用的对象传入ViewModel，因为这可能会导致内存泄露。
     * 但如果你希望在ViewModel中使用Context怎么办呢？
     * 我们可以使用AndroidViewModel类，它继承自ViewModel，并且接收Application作为Context，既然是Application作为Context，
     * 也就意味着，我们能够明确它的生命周期和Application是一样的，这就不算是一个内存泄露了。
     */
    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        timer = null
    }

}