package com.liang.model_middleware.impl;

import com.alibaba.android.arouter.launcher.ARouter;
import com.liang.model_middleware.router.AppRouter;
import com.liang.model_middleware.service.DatePickerService;
import com.liang.model_middleware.service.EyeModuleService;
import com.liang.model_middleware.service.MainService;
import com.liang.model_middleware.service.TestLaboratoryService;
import com.liang.model_middleware.service.WeatherModuleService;

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

    /**
     * 获取Module_Weather库的Service
     */
    public static WeatherModuleService getWeatherService() {
        //使用依赖查找的方法发现服务，主动去发现服务并使用
        return (WeatherModuleService) ARouter.getInstance().build(AppRouter.MODULE_WEATHER_PATH).navigation();
    }

    /**
     * 获取Module_EyePetizer的Service
     */
    public static EyeModuleService getEyeModuleService() {
        //使用依赖查找的方法发现服务，主动去发现服务并使用
        return (EyeModuleService) ARouter.getInstance().build(AppRouter.MODULE_EYE_PETIZER).navigation();
    }

    /**
     * 获取Module_Laboratory的Service
     */
    public static TestLaboratoryService getTestLaboratoryModuleService() {
        //使用依赖查找的方法发现服务，主动去发现服务并使用
        return (TestLaboratoryService) ARouter.getInstance().build(AppRouter.MODULE_TEST_LABORATORY).navigation();
    }

    public static MainService getMainService(){
        return (MainService) ARouter.getInstance().build(AppRouter.MODULE_MAIN_HOME).navigation();
    }

} 
