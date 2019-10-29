package liang.com.baseproject.utils;

import liang.com.baseproject.app.MyApplication;

import static liang.com.baseproject.Constant.Constant.KEY_DARK_THEME;

public class SettingUtils {

    private boolean mDarkTheme = false;

    private static class Holder {
        private static final SettingUtils INSTANCE = new SettingUtils();
    }

    public static SettingUtils getInstance() {
        return Holder.INSTANCE;
    }

    private SettingUtils() {
        mDarkTheme = (boolean) SPUtils.get(MyApplication.getAppContext(), KEY_DARK_THEME, mDarkTheme);
    }

    public boolean isDarkTheme() {
        return mDarkTheme;
    }

    public void setDarkTheme(boolean mDarkTheme) {
        this.mDarkTheme = mDarkTheme;
        //本地持久化
        SPUtils.put(MyApplication.getAppContext(), KEY_DARK_THEME, mDarkTheme);
    }
}
