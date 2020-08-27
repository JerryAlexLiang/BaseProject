package com.liang.module_core.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 创建日期：2019/2/20 on 14:13
 * 描述: MVPBasePresenter
 * 作者: liangyang
 */
public abstract class MVPBasePresenter<V> {

    protected Reference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;

        }
    }
}
