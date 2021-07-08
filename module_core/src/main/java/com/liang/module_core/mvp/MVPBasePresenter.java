package com.liang.module_core.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 创建日期：2019/2/20 on 14:13
 * 描述: MVPBasePresenter
 * 作者: liangyang
 */
public abstract class MVPBasePresenter<V> {

    //1、将Presenter对View(Activity)的引用使用弱引用(WeakReference)的方式
    //View接口类型弱应用
    protected Reference<V> mViewRef;

    public void attachView(V view) {
        //创建弱引用
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        if (mViewRef != null) {
            return mViewRef.get();
        }
        return null;
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    //解除关联
    //结合Activity的生命周期去进行attachView和detachView,接触Presenter层与View层的关联
    public void detachView() {
        if (mViewRef != null) {
            //在View销毁的时候，手动GC，让系统回收内存
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
