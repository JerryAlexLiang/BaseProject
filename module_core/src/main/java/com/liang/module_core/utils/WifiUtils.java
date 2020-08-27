package com.liang.module_core.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

/**
 * 创建日期：2019/1/28 on 16:25
 * 描述: 获取Wifi信息工具类
 * 作者: liangyang
 */
public class WifiUtils {

    /**
     * 获取wifi名称
     */
    public static String getWifiName(Activity activity) {
        String ssid="unknown id";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O||Build.VERSION.SDK_INT==Build.VERSION_CODES.P) {

            WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT== Build.VERSION_CODES.O_MR1){

            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo()!=null){
                    return networkInfo.getExtraInfo().replace("\"","");
                }
            }
        }
        return ssid;
    }

    /**
     * 获取服务器地址
     */
    public static String getIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        DhcpInfo info = wifiManager.getDhcpInfo();
        int gateway = info.gateway;
        String ip = "http://" + intToIp(gateway) + ":8080";
        return ip;
    }

    /**
     * 获取WiFi网关地址
     */
    public static String intToIp(int address) {
        return ((address & 0xFF) + "." +
                ((address >>>= 8) & 0xFF) + "." +
                ((address >>>= 8) & 0xFF) + "." +
                ((address >>>= 8) & 0xFF));
    }
}
