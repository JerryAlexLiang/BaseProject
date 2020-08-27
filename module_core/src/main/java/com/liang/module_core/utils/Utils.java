package com.liang.module_core.utils;

import android.annotation.SuppressLint;
import android.content.Context;


public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    public static void init(Context context) {
        Utils.context = context;
    }

    public static Context getAppContext() {
        if (context == null) {
            throw new RuntimeException("Utils未在Application中初始化");
        }
        return context;
    }
}
