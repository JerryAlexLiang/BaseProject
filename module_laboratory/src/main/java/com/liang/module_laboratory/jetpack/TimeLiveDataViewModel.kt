package com.liang.module_laboratory.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * 创建日期: 2021/7/15 on 2:45 PM
 * 描述: 通过接口的方式，完成去通知调用者；这种方式不是太好，更好的方式是通过LiveData组件来实现这一功能
 * 这样做是可行的，但如果要观察的数据很多，则需要定义大量的接口，代码显得冗余。
 * 为此，Android为我们提供了LiveData组件，帮助我们完成ViewModel与页面组件之间的通信。所以，LiveData通常是被放在ViewModel中使用。
 * 作者: 杨亮
 */
class TimeLiveDataViewModel : ViewModel() {

    private val TAG = this.javaClass.name
    private var timer: Timer? = null

    //    private var currentSecond = 0
    //将second这个数据字段使用MutableLiveData包装起来
    private var currentSecond: MutableLiveData<Int?>? = null

    /**
     * 1、LiveData是一个可被观察的数据容器类。
     * 什么意思呢？我们可以将LiveData理解为一个数据的容器，
     * 它将数据包装起来，使得数据成为“被观察者”，页面成为“观察者”。
     * 这样，当该数据发生变化时，页面能够获得通知，进而更新UI。
     * 2、进一步区别一下ViewModel和LiveData。
     * ViewModel用于存放页面所需的各种数据，它还包括一些业务逻辑等，比如我们可以在ViewModel对数据进行加工，获取等操作。
     * 而对页面来说，它并不关心这些业务逻辑，它只关心需要展示的数据是什么，并且希望在数据发生变化时，能及时得到通知并做出更新。
     * LiveData的作用就是，在ViewModel中的数据发生变化时通知页面。从LiveData（实时数据）这个名字，我们也能推测出，它的特性与作用。
     * 3、LiveData能够感知页面的生命周期。
     * 它可以检测页面当前的状态是否为激活状态，或者页面是否被销毁。
     * 只有在页面处于激活状态（Lifecycle.State.ON_STARTED或Lifecycle.State.ON_RESUME）时，页面才会收到来自LiveData的通知；
     * 如果页面被销毁（Lifecycle.State.ON_DESTROY），那么LiveData会自动清除与页面的关联，从而避免了可能引发的内存泄漏问题。
     * 4、LiveData还提供了一个observeForever()方法，使用起来与observe()没有太大差别，
     * 它们的区别主要在于，当LiveData包装的数据发生变化时，无论页面处于什么状态，observeForever()都能收到通知。
     * 所以，在使用完之后，一定要记得调用removeObserver()方法来停止对LiveData的观察，否则LiveData会一直处于激活状态，你的Activity永远不会被系统自动回收。
     */
    fun getCurrentSecond(): LiveData<Int?>? {
        if (currentSecond == null) {
            currentSecond = MutableLiveData()
        }
        return currentSecond
    }

    fun resetTime() {
        if (currentSecond == null) {
            currentSecond = MutableLiveData()
        }
        //postValue()是在非UI线程中使用，如果在UI线程中，则使用setValue()方法。
        currentSecond?.value = 0
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    /**
     * 开始计时
     */
//    fun startTiming() {
//        if (timer == null) {
//            currentSecond = 0
//            timer = Timer()
//            val timerTask = object : TimerTask() {
//                override fun run() {
//                    currentSecond++
//                    onTimeChangeListener?.onTimeChanged(currentSecond)
//                }
//            }
//            timer!!.schedule(timerTask, 1000, 1000);//延迟3秒执行
//        }
//    }

    fun startTiming() {
        if (timer == null) {
            currentSecond?.value = 0
            timer = Timer()
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    //这里要用postValue方法，而不能用setValue方法，否则会报线程异常错误
                    currentSecond?.postValue(currentSecond?.value!! + 1)
                }
            }
            timer!!.schedule(timerTask, 1000, 1000);//延迟3秒执行
        }
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