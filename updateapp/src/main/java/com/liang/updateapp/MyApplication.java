package com.liang.updateapp;

import android.app.Application;
import android.widget.Toast;

import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.utils.UpdateUtils;

/**
 * 创建日期: 2021/6/1 on 10:32 AM
 * 描述:
 * 作者: 杨亮
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化XUpdate
        initUpdate();
    }

    /**
     * 在Application进行初始化配置：
     */
    private void initUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)
                .isGet(true)
                .isAutoMode(false)
                .param("versionCode", UpdateUtils.getVersionCode(this))
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(error -> {
                    if (error.getCode() != UpdateError.ERROR.CHECK_NO_NEW_VERSION) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .supportSilentInstall(false)
                .setIUpdateHttpService(new OKHttpUpdateHttpService())
                .init(this);
    }
}
