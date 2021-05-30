package liang.com.baseproject.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.liang.module_core.BuildConfig;
import com.liang.module_core.app.BaseApplication;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.activity.MyCrashActivity;
import liang.com.baseproject.gen.DaoMaster;
import liang.com.baseproject.gen.DaoSession;
import liang.com.baseproject.update.http.OKHttpUpdateHttpService;

import com.liang.model_middleware.app.BaseApplicationImpl;
import com.liang.model_middleware.app.ModuleConfig;
import com.liang.module_core.retrofit.RetrofitHelper;
import com.liang.module_core.utils.DebugUtils;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.utils.ToastUtil;
import com.luck.picture.lib.tools.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;
import static liang.com.baseproject.Constant.Constant.APP_DB_NAME;

public class MyApplication extends BaseApplication {

    private static String TAG = "MyApplication";
//
//    private static MyApplication app;
//
//    private static List<Activity> activities = Collections.synchronizedList(new LinkedList<Activity>());
//
//    public static Context getAppContext() {
//        return app;
//    }
//
//    public static Context mContext;
//    private static boolean _bOuputLog = true;
//
//    public static Resources getAppResources() {
//        return app.getResources();
//    }

    public static PackageInfo packageInfo;

    private static DaoSession daoSession;

//    private static void outputLog(String strTAG, String strInfo) {
//        if (_bOuputLog)
//            Log.i(strTAG, strInfo);
//    }

//    /**
//     * 方法超过64K，需要采用分包
//     */
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        //初始化
//        MultiDex.install(this);
//    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
//        outputLog(TAG, "App");
        super.onCreate();

//        Utils.init(this);
//        app = this;

//        mContext = getApplicationContext();

//        if (isMainProcess()){
//            setDarkModeStatus();
//        }

        //腾讯Bugly
        initBugly();

        //Android程序崩溃框架—CustomActivityOnCrash
        initCrashActivity();

        // 初始化模块配置
        initModuleConfig();

        try {
            packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        //初始化路由SDK
//        initARouter();

//        /**
//         * Android7.0+系统解决拍照的问题
//         * Android不再允许在app中把file://Uri暴露给其他app，包括但不局限于通过Intent或ClipData 等方法，使用file://Uri会有一些风险
//         */
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            builder.detectFileUriExposure();
//        }

        //配置GreenDao数据库
        setupDatabase();

        //初始化XUpdate
        initUpdate();
    }

    /**
     * 在Application进行初始化配置：
     */
    private void initUpdate() {
        XUpdate.get()
                .debug(true)
                //默认设置只在wifi下检查版本更新
                .isWifiOnly(false)
                //默认设置使用get请求检查版本
                .isGet(true)
                //默认设置非自动模式，可根据具体使用配置
                .isAutoMode(false)
                //设置默认公共请求参数
                .param("versionCode", UpdateUtils.getVersionCode(this))
                .param("appKey", getPackageName())
                //设置版本更新出错的监听
                .setOnUpdateFailureListener(error -> {
                    //对不同错误进行处理
                    if (error.getCode() != UpdateError.ERROR.CHECK_NO_NEW_VERSION) {
                        ToastUtil.onShowErrorToast(getAppContext(), error.toString());
                    }
                })
                //设置是否支持静默安装，默认是true
                .supportSilentInstall(false)
                //这个必须设置！实现网络请求功能
                .setIUpdateHttpService(new OKHttpUpdateHttpService())
                //这个必须初始化
                .init(this);
    }


    /**
     * 腾讯bugly
     */
    private void initBugly() {
        CrashReport.setIsDevelopmentDevice(this, DebugUtils.isDebug());
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        userStrategy.setUploadProcess(isMainProcess());
        //注册时申请的APP ID
        //第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        //输出详细的Bugly SDK的Log；
        //每一条Crash都会被立即上报；
        //自定义日志将会在Logcat中输出。
        //建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(this, "5f342ffb5a", DebugUtils.isDebug(), userStrategy);
    }

    /**
     * Android程序崩溃框架—CustomActivityOnCrash
     */
    private void initCrashActivity() {
        //整个配置属性，可以设置一个或多个，也可以一个都不设置
        CaocConfig.Builder.create()
                //程序在后台时，发生崩溃的三种处理方式
                //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
                //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
                //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                .enabled(true)  //false表示对崩溃的拦截阻止,用它来禁用customActivityonCrash框架
                .showErrorDetails(true)  //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪, —> 针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
                .showRestartButton(true)  //是否可以重启页面,针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
                .logErrorOnRestart(false)
                .trackActivities(false)  //错误页面中显示错误详细信息；针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
                .minTimeBetweenCrashesMs(2000)   //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理
                .restartActivity(MainHomeActivity.class)  //重新启动后的页面
                .errorDrawable(R.mipmap.icon_new_launcher) //崩溃页面显示的图标
                .errorActivity(MyCrashActivity.class) //程序崩溃后显示的页面
//                .errorActivity(DefaultErrorActivity.class) //程序崩溃后显示的页面(默认程序崩溃时错误页面)
                .eventListener(new CustomEventListener())//设置监听
                .apply();


    }

    /**
     * 程序崩溃时设置监听
     * The event listener cannot be an inner or anonymous class,
     * because it will need to be serialized. Change it to a class of its own, or make it a static inner class.
     */
    private static class CustomEventListener implements CustomActivityOnCrash.EventListener {
        @Override
        public void onLaunchErrorActivity() {
            //程序崩溃时回调
            LogUtil.d(TAG, "CustomActivityOnCrash =============>   程序崩溃时回调");
        }

        @Override
        public void onRestartAppFromErrorActivity() {
            //重启程序时回调
            LogUtil.d(TAG, "CustomActivityOnCrash =============>   重启程序时回调");
        }

        @Override
        public void onCloseAppFromErrorActivity() {
            //在崩溃提示页面关闭程序时回调
            LogUtil.d(TAG, "CustomActivityOnCrash =============>   在崩溃提示页面关闭程序时回调");
        }
    }


    /**
     * 通过反射拿到需要初始化的各种组件application
     * <p>
     * 1、创建BaseApplication类，ModuleConfig类以及BaseApplicationImpl类。
     * 2、BaseApplication类的onCreate()方法中初始化一些全局配置并且初始化模块配置。
     * 3、BaseApplicationImpl类是一个接口类，需要各模块自己去实现各个模块的配置。
     * 4、这些配置的类是定义在ModuleConfig中，在初始化的时候会通过反射创建这些类。
     * 5、使用：
     * 在各个子module中实现BaseApplicationImpl，这个类可以提供模块化的配置以及application context对象。清单文件都设置为BaseApplication即可。
     */
    private void initModuleConfig() {
        for (String modules : ModuleConfig.MODULE_LIST) {
            try {
                Class<?> clz = Class.forName(modules);
                Object obj = clz.newInstance();
                if (obj instanceof BaseApplicationImpl) {
                    ((BaseApplicationImpl) obj).onCreate(this, BuildConfig.DEBUG);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * 初始化路由SDK
//     */
//    private void initARouter() {
//        if (BuildConfig.DEBUG){  // 这两行必须写在init之前，否则这些配置在init过程中将无效
//            ARouter.openLog();   // 打印日志
//            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        // 尽可能早，推荐在Application中初始化
//        ARouter.init(app);
//    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, APP_DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        RetrofitHelper.deleteInstance();
    }

//    public static List<Activity> getActivities() {
//        return activities;
//    }
//
//    public static boolean isAppAlive() {
//        if (app == null) {
//            return false;
//        }
//        if (activities == null || activities.size() == 0) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 判断Android程序是否在前台运行
//     */
//    public static boolean isAppOnForeground() {
//        ActivityManager activityManager = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
//        if (activityManager == null) {
//            return false;
//        }
//        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
//        if (appProcesses == null) {
//            return false;
//        }
//        String packageName = getAppContext().getPackageName();
//        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * APP从后台切换回前台
//     */
//    public static void returnToForeground() {
//        if (!isAppOnForeground()) {
//            Activity currentActivity = currentActivity();
//            if (currentActivity != null) {
//                Intent intent = new Intent(getAppContext(), currentActivity.getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                getAppContext().startActivity(intent);
//            }
//        }
//    }
//
//    public static boolean isMainProcess() {
//        String mainProcessName = getAppContext().getPackageName();
//        String processName = currentProcessName();
//        return processName == null || TextUtils.equals(processName, mainProcessName);
//    }
//
//    @Nullable
//    public static String currentProcessName() {
//        return getProcessName(Process.myPid());
//    }
//
//    /**
//     * 获取进程号对应的进程名
//     *
//     * @param pid 进程号
//     * @return 进程名
//     */
//    @Nullable
//    private static String getProcessName(int pid) {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
//            String processName = reader.readLine();
//            if (!TextUtils.isEmpty(processName)) {
//                processName = processName.trim();
//            }
//            return processName;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取当前Activity
//     */
//    @Nullable
//    public static Activity currentActivity() {
//        if (activities == null || activities.isEmpty()) {
//            return null;
//        }
//        return activities.get(activities.size() - 1);
//    }
//
//    /**
//     * 按照指定类名找到activity
//     */
//    @Nullable
//    public static Activity findActivity(Class<?> cls) {
//        if (cls == null) {
//            return null;
//        }
//        if (activities == null || activities.isEmpty()) {
//            return null;
//        }
//        for (Activity activity : activities) {
//            if (activity.getClass().equals(cls)) {
//                return activity;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 结束当前Activity
//     */
//    public static void finishCurrentActivity() {
//        finishActivity(currentActivity());
//    }
//
//    /**
//     * 结束指定的Activity
//     */
//    public static void finishActivity(Activity activity) {
//        if (activity == null) {
//            return;
//        }
//        if (activities == null || activities.isEmpty()) {
//            return;
//        }
//        activities.remove(activity);
//        activity.finish();
//        activity = null;
//    }
//
//    /**
//     * 结束指定类名的Activity
//     */
//    public static void finishActivity(Class<? extends Activity> cls) {
//        if (cls == null) {
//            return;
//        }
//        if (activities == null || activities.isEmpty()) {
//            return;
//        }
//        for (int i = activities.size() - 1; i >= 0; i--) {
//            Activity activity = activities.get(i);
//            if (cls.equals(activity.getClass())) {
//                finishActivity(activity);
//            }
//        }
//    }
//
//    /**
//     * 结束所有Activity
//     */
//    public static void finishAllActivity() {
//        if (activities == null || activities.isEmpty()) {
//            return;
//        }
//        for (int i = activities.size() - 1; i >= 0; i--) {
//            Activity activity = activities.get(i);
//            if (!activity.isFinishing()) {
//                activity.finish();
//            }
//        }
//        activities.clear();
//    }
//
//    /**
//     * 退出应用程序
//     */
//    public static void exitApp() {
//        finishAllActivity();
//    }
//
//    /**
//     * 退出应用程序
//     */
//    public static void killProcess() {
//        exitApp();
//        Process.killProcess(Process.myPid());
//    }
//
//    public static void restart() {
//        finishActivityWithoutCount(1);
//        if (activities != null && !activities.isEmpty()) {
//            activities.get(0).recreate();
//        }
//    }
//
//    public static void recreate() {
//        if (activities != null && !activities.isEmpty()) {
//            for (Activity activity : activities) {
//                activity.recreate();
//            }
//        }
//    }
//
//    public static void finishActivityWithoutCount(int count) {
//        if (activities == null || activities.isEmpty()) {
//            return;
//        }
//        if (count <= 0) {
//            finishAllActivity();
//            return;
//        }
//        for (int i = activities.size() - 1; i >= count; i--) {
//            finishActivity(activities.get(i));
//        }
//    }
//
//    public static void finishActivityWithout(Class<? extends Activity> cls) {
//        if (cls == null) {
//            finishAllActivity();
//            return;
//        }
//        if (activities == null || activities.isEmpty()) {
//            return;
//        }
//        for (int i = activities.size() - 1; i >= 0; i--) {
//            Activity activity = activities.get(i);
//            if (!cls.equals(activity.getClass())) {
//                finishActivity(activity);
//            }
//        }
//    }
//
//    public static void finishActivityWithout(Activity activity) {
//        if (activity == null) {
//            finishAllActivity();
//            return;
//        }
//        finishActivityWithout(activity.getClass());
//    }
//
//    public static void setDarkModeStatus(){
//        if (SettingUtils.getInstance().isDarkTheme()){
//            //设置为黑色主题
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            ToastUtil.onShowTrueToast("夜晚主题");
//        }else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            ToastUtil.onShowTrueToast("白天主题");
//        }
//    }
}
