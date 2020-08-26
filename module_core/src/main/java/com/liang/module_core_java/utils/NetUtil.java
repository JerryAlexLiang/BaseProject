package com.liang.module_core_java.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import com.liang.module_core_java.constant.Constant;

/**
 * 创建日期：2018/12/24 on 11:22
 * 描述: 网络状态工具类
 * 作者: liangyang
 */
public class NetUtil {

//    //没有连接网络
//    public static final int NETWORK_NONE = -1;
//    //移动网络
//    public static final int NETWORK_MOBILE = 0;
//    //无线网络
//    public static final int NETWORK_WIFI = 1;

    /**
     * 获取网络状态
     */

    public static int getNetWorkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return Constant.NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return Constant.NETWORK_MOBILE;
            }
        } else {
            return Constant.NETWORK_NONE;
        }
        return Constant.NETWORK_NONE;
    }

    /**
     * 打开无线和网络设置界面
     */
    public static void openNetSetting(Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    /**
     * 打开Wifi网络设置界面
     */
    public static void openWifiSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
        activity.startActivity(intent);
        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    /**
     * 打开移动流量设置界面
     */
    public static void openMobileNetSetting(Activity activity) {
        activity.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
    }

    /**
     * 打开系统设置界面
     */
    public static void openSystemSetting(Activity activity) {
        try {
            if (10 < Build.VERSION.SDK_INT) {
                activity.startActivity(new Intent(
                        "android.settings.SETTINGS"));
                return;
            }
        } catch (Exception localException) {
            localException.printStackTrace();
            return;
        }
        activity.startActivity(new Intent(
                "android.settings.WIRELESS_SETTINGS"));
    }

    public static boolean isWiFiAvailable(Context inContext) {
        WifiManager mWifiManager = (WifiManager) inContext.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = null;
        if (mWifiManager != null) {
            wifiInfo = mWifiManager.getConnectionInfo();
        }
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        assert mWifiManager != null;
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWiFiActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI")
                            && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo == null) {
                return false;
            } else {
                if (networkInfo.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMobileActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo == null) {
            return false;
        } else {
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static NetworkInfo getActiveNetwork(Context context) {
        if (context == null)
            return null;
        ConnectivityManager mConnMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr == null)
            return null;
        NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo();
        return aActiveInfo;
    }
}
