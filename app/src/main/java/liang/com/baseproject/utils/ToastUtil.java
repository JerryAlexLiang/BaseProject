package liang.com.baseproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.Toast;

import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.widget.toast.CustomToast;


/**
 * 创建日期：2018/12/24 on 16:34
 * 描述: 自定义Toast工具类 & Toast工具类(只弹出一次，解决toast重复弹出的问题)
 * 作者: liangyang
 */
public class ToastUtil {

    public static Toast mToast;

    public static CustomToast toast;

    @SuppressLint("ShowToast")
    public static void showLongToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showLongToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.mContext, content, Toast.LENGTH_LONG);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showShortToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showShortToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.mContext, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 初始化封装自定义Toast
     */
    public static void setCustomToast(Context context, Bitmap icon, boolean isShowIcon, String message, int backgroundColor,
                                      int textColor, int gravity, int duration) {
        //创建Toast
        toast = new CustomToast.Builder(context)
                .setMessage(message)//设置提示文字
                .showIcon(isShowIcon)//是否显示图标
                .setIcon(icon)//图标
                .setBackgroundColor(backgroundColor)//设置背景颜色
                .setGravity(gravity)//设置吐司位置,Gravity.CENTER
                .setTextColor(textColor)//设置字体的颜色
                .setDuration(duration)
                .build();//创建吐司
        toast.show();
    }

    public static void setCustomToast(Bitmap icon, boolean isShowIcon, String message, int backgroundColor,
                                      int textColor, int gravity, int duration) {
        //创建Toast
        toast = new CustomToast.Builder(MyApplication.mContext)
                .setMessage(message)//设置提示文字
                .showIcon(isShowIcon)//是否显示图标
                .setIcon(icon)//图标
                .setBackgroundColor(backgroundColor)//设置背景颜色
                .setGravity(gravity)//设置吐司位置,Gravity.CENTER
                .setTextColor(textColor)//设置字体的颜色
                .setDuration(duration)
                .build();//创建吐司
        toast.show();
    }

}
