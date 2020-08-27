package com.liang.module_core.utils;

import com.liang.module_core.app.BaseApplication;
import com.liang.module_core.constant.Constant;


public class SettingUtils {

    private boolean mDarkTheme = false;

    private static class Holder {
        private static final SettingUtils INSTANCE = new SettingUtils();
    }

    public static SettingUtils getInstance() {
        return Holder.INSTANCE;
    }

    private SettingUtils() {
        mDarkTheme = (boolean) SPUtils.get(BaseApplication.getAppContext(), Constant.KEY_DARK_THEME, mDarkTheme);
    }

    public boolean isDarkTheme() {
        return mDarkTheme;
    }

    public void setDarkTheme(boolean mDarkTheme) {
        this.mDarkTheme = mDarkTheme;
        //本地持久化
        SPUtils.put(BaseApplication.getAppContext(), Constant.KEY_DARK_THEME, mDarkTheme);
    }
}
