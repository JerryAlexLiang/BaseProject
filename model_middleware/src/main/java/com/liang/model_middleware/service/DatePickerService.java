package com.liang.model_middleware.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 创建日期：2020/8/3 on 4:06 PM
 * 描述:通过依赖注入解耦:服务管理
 * 1、声明接口,其他组件通过接口来调用服务
 * 作者:yangliang
 */
public interface DatePickerService extends IProvider {

    void startDatePickerDemoActivity(Context context);

}