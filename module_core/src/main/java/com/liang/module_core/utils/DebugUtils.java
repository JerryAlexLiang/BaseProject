package com.liang.module_core.utils;

import android.text.TextUtils;

import com.liang.module_core.BuildConfig;

public class DebugUtils {

    private static final boolean DEBUG;

    static {
        DEBUG = BuildConfig.DEBUG && TextUtils.equals(BuildConfig.BUILD_TYPE, "debug");
    }

    public static boolean isDebug() {
        return DEBUG;
    }

}
