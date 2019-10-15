package liang.com.baseproject.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import liang.com.baseproject.gen.DaoMaster;
import liang.com.baseproject.gen.DaoSession;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.utils.Utils;

import static liang.com.baseproject.Constant.Constant.APP_DB_NAME;
public class MyApplication extends MultiDexApplication {
    private static String TAG = "App";

    private static MyApplication app;

    public static Context getAppContext() {
        return app;
    }

    public static Context mContext;
    private static boolean _bOuputLog = true;

    public static Resources getAppResources() {
        return app.getResources();
    }

    public static PackageInfo packageInfo;

    private static DaoSession daoSession;

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
        // TODO Auto-generated method stub
        outputLog(TAG, "App");
        super.onCreate();

        Utils.init(this);
        app = this;

        mContext = getApplicationContext();

        try {
            packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * Android7.0+系统解决拍照的问题
         * Android不再允许在app中把file://Uri暴露给其他app，包括但不局限于通过Intent或ClipData 等方法，使用file://Uri会有一些风险
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        //配置GreenDao数据库
        setupDatabase();
    }

    private void setupDatabase() {
        //DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "face.db", null);
        //        SQLiteDatabase db = helper.getWritableDatabase();
        //        DaoMaster daoMaster = new DaoMaster(db);
        //        daoSession = daoMaster.newSession();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, APP_DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        RetrofitHelper.deleteInstance();
    }
}
