package com.liang.model_middleware.impl;

import com.alibaba.android.arouter.launcher.ARouter;
import com.liang.model_middleware.router.AppRouter;
import com.liang.model_middleware.service.DatePickerService;

/**
 * 创建日期：2020/8/3 on 4:26 PM
 * 描述:通过依赖注入解耦:服务管理
 * 3、路由服务管理中心
 * 作者:yangliang
 */
public class ServiceProvider {

    /**
     * 获取DatePicker库Service
     */
    public static DatePickerService getDatePickerService() {
        //4、使用依赖查找的方式发现服务，主动去发现服务并使用，下面方式是byType
        return (DatePickerService) ARouter.getInstance().build(AppRouter.DATE_PICKER_PATH).navigation();
    }

} 
