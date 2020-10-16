package com.liang.module_core.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Process;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.liang.module_core.BuildConfig;
import com.liang.module_core.utils.SettingUtils;
import com.liang.module_core.utils.ToastUtil;
import com.liang.module_core.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建日期: 2020/8/25 on 4:58 PM
 * 描述:
 * 作者: 杨亮
 */
public class BaseApplication extends MultiDexApplication {

    private static String TAG = "App";

    private static BaseApplication app;

    private static List<Activity> activities = Collections.synchronizedList(new LinkedList<Activity>());

    public static Context getAppContext() {
        return app;
    }

    public static Context mContext;
    private static boolean _bOuputLog = true;

    public static Resources getAppResources() {
        return app.getResources();
    }

    private static void outputLog(String strTAG, String strInfo) {
        if (_bOuputLog)
            Log.i(strTAG, strInfo);
    }

    /**
     * 方法超过64K，需要采用分包
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        outputLog(TAG, "App");
        super.onCreate();
        // 初始化全局配置
        initGlobalConfig();
    }

    private void initGlobalConfig() {
        Utils.init(this);

        app = this;

        mContext = getApplicationContext();

        if (isMainProcess()) {
            setDarkModeStatus();
        }

        //初始化路由SDK
        initARouter();

        /**
         * Android7.0+系统解决拍照的问题
         * Android不再允许在app中把file://Uri暴露给其他app，包括但不局限于通过Intent或ClipData 等方法，使用file://Uri会有一些风险
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
    }

    /**
     * 初始化路由SDK
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();   // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(app);
    }

    public static List<Activity> getActivities() {
        return activities;
    }

    public static boolean isAppAlive() {
        if (app == null) {
            return false;
        }
        if (activities == null || activities.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断Android程序是否在前台运行
     */
    public static boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        String packageName = getAppContext().getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * APP从后台切换回前台
     */
    public static void returnToForeground() {
        if (!isAppOnForeground()) {
            Activity currentActivity = currentActivity();
            if (currentActivity != null) {
                Intent intent = new Intent(getAppContext(), currentActivity.getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getAppContext().startActivity(intent);
            }
        }
    }

    public static boolean isMainProcess() {
        String mainProcessName = getAppContext().getPackageName();
        String processName = currentProcessName();
        return processName == null || TextUtils.equals(processName, mainProcessName);
    }

    @Nullable
    public static String currentProcessName() {
        return getProcessName(Process.myPid());
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    @Nullable
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取当前Activity
     */
    @Nullable
    public static Activity currentActivity() {
        if (activities == null || activities.isEmpty()) {
            return null;
        }
        return activities.get(activities.size() - 1);
    }

    /**
     * 按照指定类名找到activity
     */
    @Nullable
    public static Activity findActivity(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        if (activities == null || activities.isEmpty()) {
            return null;
        }
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束当前Activity
     */
    public static void finishCurrentActivity() {
        finishActivity(currentActivity());
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activities == null || activities.isEmpty()) {
            return;
        }
        activities.remove(activity);
        activity.finish();
        activity = null;
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<? extends Activity> cls) {
        if (cls == null) {
            return;
        }
        if (activities == null || activities.isEmpty()) {
            return;
        }
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (cls.equals(activity.getClass())) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (activities == null || activities.isEmpty()) {
            return;
        }
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    /**
     * 退出应用程序
     */
    public static void exitApp() {
        finishAllActivity();
    }

    /**
     * 退出应用程序
     */
    public static void killProcess() {
        exitApp();
        Process.killProcess(Process.myPid());
    }

    public static void restart() {
        finishActivityWithoutCount(1);
        if (activities != null && !activities.isEmpty()) {
            activities.get(0).recreate();
        }
    }

    public static void recreate() {
        if (activities != null && !activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.recreate();
            }
        }
    }

    public static void finishActivityWithoutCount(int count) {
        if (activities == null || activities.isEmpty()) {
            return;
        }
        if (count <= 0) {
            finishAllActivity();
            return;
        }
        for (int i = activities.size() - 1; i >= count; i--) {
            finishActivity(activities.get(i));
        }
    }

    public static void finishActivityWithout(Class<? extends Activity> cls) {
        if (cls == null) {
            finishAllActivity();
            return;
        }
        if (activities == null || activities.isEmpty()) {
            return;
        }
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (!cls.equals(activity.getClass())) {
                finishActivity(activity);
            }
        }
    }

    public static void finishActivityWithout(Activity activity) {
        if (activity == null) {
            finishAllActivity();
            return;
        }
        finishActivityWithout(activity.getClass());
    }

    public static void setDarkModeStatus() {
        if (SettingUtils.getInstance().isDarkTheme()) {
            //设置为黑色主题
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            ToastUtil.onShowTrueToast(BaseApplication.mContext, "夜晚主题");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            ToastUtil.onShowTrueToast(BaseApplication.mContext, "白天主题");
        }
    }
}
