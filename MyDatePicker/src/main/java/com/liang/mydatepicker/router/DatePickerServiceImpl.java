package com.liang.mydatepicker.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.liang.model_middleware.router.AppRouter;
import com.liang.model_middleware.service.DatePickerService;
import com.liang.mydatepicker.DatePickerActivity;

/**
 * 创建日期：2020/8/3 on 4:33 PM
 * 描述:通过依赖注入解耦:服务管理
 * 2、实现接口
 * 作者:yangliang
 */
@Route(path = AppRouter.DATE_PICKER_PATH)
public class DatePickerServiceImpl implements DatePickerService {

    @Override
    public void startDatePickerDemoActivity(Context context) {
        DatePickerActivity.actionStart(context);
    }

    @Override
    public void init(Context context) {

    }
}
