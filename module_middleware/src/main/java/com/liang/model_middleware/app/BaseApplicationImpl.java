package com.liang.model_middleware.app;

import android.app.Application;

/**
 * 创建日期: 2020/8/26 on 2:27 PM
 * 描述:
 * 作者: 杨亮
 */
public interface BaseApplicationImpl {

//    void onCreate(Application application);

    void onCreate(Application application, boolean isDebug);
}