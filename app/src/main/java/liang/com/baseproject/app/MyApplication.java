package liang.com.baseproject.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.allen.library.RxHttpUtils;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;

public class MyApplication extends Application {
    private static String TAG = "App";

    private static MyApplication app;

    public static Context getAppContext(){
        return app;
    }

    public static Context mContext;
    private static boolean _bOuputLog = true;

    public static Resources getAppResources() {
        return app.getResources();
    }

    public static PackageInfo packageInfo;

    private static void outputLog(String strTAG, String strInfo) {
        if (_bOuputLog)
            Log.i(strTAG, strInfo);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        outputLog(TAG, "App");
        super.onCreate();

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

        //初始化配置
        initRxHttpUtils();
    }

    /**
     * 初始化配置
     */
    private void initRxHttpUtils() {
        RxHttpUtils.init(this);
        OkHttpClient.Builder client = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor.Builder()
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build());

        RxHttpUtils
                .getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(UrlConstants.BASE_URL)
                //全局的请求头信息
//                .setHeaders(map)
                //全局持久话cookie,保存本地每次都会携带在header中
                .setCookie(false)
                //全局ssl证书认证
                //信任所有证书,不安全有风险
//                .setSslSocketFactory()
                //使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.cer"))
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.bks"), "123456", getAssets().open("your.cer"))
                //全局超时配置
                //全局是否打开请求log日志
                .setOkClient(client.build())
                .setLog(true);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        RetrofitHelper.deleteInstance();
    }
}
