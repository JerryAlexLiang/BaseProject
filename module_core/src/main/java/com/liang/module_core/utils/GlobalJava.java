package com.liang.module_core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class GlobalJava {

    private static Context context;

    private static int mScreenWidth;
    private static int mScreenHeight;
    private static float mDensity;

    public static void init(Context context) {
        GlobalJava.context = context;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mDensity = dm.density;
    }

    /** 单位转换dp转px */
    public static int dp2px(int dp) {
        return (int) (dp * mDensity + 0.5f);
    }

    public static void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 读取assets目录下的文件
     * @param fileName 文件名
     * @return
     */
    public static String readAssets(String fileName) {
        String str = "";
        try {
            InputStream stream = context.getResources().getAssets().open(fileName);
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = stream.read(buffer)) != -1) {
                // 读取数据到内存中
                baos.write(buffer, 0, len);
            }
            str = baos.toString("utf-8");
            stream.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String formatDate(long time) {
        return DateFormat.format("yyyy-MM-dd kk:mm:ss", time).toString();
    }

    /**
     * 根据图片名称获取资源id
     * 例如：传入"avatar_01", 表示获取整数值R.drawable.avatar_01
     * @param context
     * @param drawableName
     * @return
     */
    public static int getResId(Context context, String drawableName) {
        return context.getResources().getIdentifier(
                drawableName, "drawable", context.getPackageName());
    }

    /** 获取宫格图片的宽高 */
    public static int getGridWidth() {
        // 左边距：   10dp
        // 右边距：   10dp
        // 图片宫格的宽度 = (屏幕宽度-左边距-右边距) / 3
        return (GlobalJava.mScreenWidth - GlobalJava.dp2px(10 + 10)) / 3;
    }

    /**
     * 根据一张原图生成一张指定宽高的图片
     *
     * @param bitmap 原图
     * @param width 要生成图片的宽高
     * @return
     */
    public static Bitmap createBitmap(Bitmap bitmap, int width) {
        float scaleX = ((float)width) / bitmap.getWidth();
        float scaleY = ((float)width) / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}

















