package com.liang.model_middleware.app;

/**
 * 创建日期: 2020/8/26 on 2:28 PM
 * 描述:
 * 作者: 杨亮
 */
public interface ModuleConfig {

    String MODULE_WEATHER = "com.liang.module_weather";
    String MODULE_EYEPETIZER = "com.liang.module_eyepetizer";
    String MODULE_BLUETOOTH = "com.liang.module_bluetooth.BleApplication";  //BleApplication

    String[] MODULE_LIST = {
            MODULE_WEATHER,
            MODULE_EYEPETIZER,
            MODULE_BLUETOOTH
    };

}