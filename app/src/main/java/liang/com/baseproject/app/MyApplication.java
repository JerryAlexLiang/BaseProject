package liang.com.baseproject.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import liang.com.baseproject.retrofit.RetrofitHelper;

public class MyApplication extends Application{
    private static String TAG = "App";


    public static Context mContext;
    private static boolean _bOuputLog = true;

    private static void outputLog(String strTAG, String strInfo) {
        if (_bOuputLog)
            Log.i(strTAG, strInfo);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        outputLog(TAG, "App");
        super.onCreate();

        mContext = getApplicationContext();
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

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        RetrofitHelper.deleteInstance();
    }
}
