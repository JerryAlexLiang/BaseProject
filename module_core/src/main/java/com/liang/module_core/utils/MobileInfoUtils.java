package com.liang.module_core.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;

/**
 * 创建日期：2019/1/30 on 15:16
 * 描述: 获取手机信息工具类
 * 作者: liangyang
 */
public class MobileInfoUtils {
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "123456789012345";
            }
            String imei = telephonyManager.getDeviceId();
            if (imei == null) {
                imei = "123456789012345";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "123456789012345";
        }
    }
}
