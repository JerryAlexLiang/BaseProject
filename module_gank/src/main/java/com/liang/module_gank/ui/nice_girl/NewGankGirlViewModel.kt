package com.liang.module_gank.ui.nice_girl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.liang.module_gank.logic.GankRepository
import com.liang.module_gank.logic.model.GankGirlRes
import java.util.ArrayList

/**
 * 创建日期:2021/7/23 on 5:59 PM
 * 描述: ViewModel相当于逻辑层与UI层之间的一个桥梁，虽然它更偏向于逻辑层的部分，但是由于ViewModel通常和Activity或Fragment是一一对应的，
 * 因此，还是习惯将它们放在一起
 * 作者: 杨亮
 */
class NewGankGirlViewModel : ViewModel() {

    private val pageLiveData = MutableLiveData<Int>()

    //妹子列表数据List
    //和界面相关的数据，定义在ViewModel中，可以保证它们在手机屏幕发生旋转的时候不会丢失，稍后在编写UI层代码的时候就会用到这几个变量
    var girlsList = ArrayList<GankGirlRes>()

    //通过switchMap()转换函数转换后，仓库层返回的LiveData对象就可以转换成一个可供Activity和Fragment观察的LiveData对象了
    val niceGankGirlBeanLiveData = Transformations.switchMap(pageLiveData) {
        GankRepository.getNiceGankGirlData(it)
    }

    fun getNiceGankGirlData(page: Int) {
        //这里没有直接调用仓库层中的Repository.getNiceGankGirlData()方法，而是将传入的搜索参数赋值给一个pageLiveData对象，
        //并使用Transformations.switchMap(pageLiveData)方法来观察这个对像，否则仓库层返回的LiveData对象将无法进行观察。
        pageLiveData.value = page
    }

}