package com.liang.module_core.widget.toast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.TypedValue;

/**
 * 创建日期：2018/6/18 on 上午12:18
 * 描述:底层背景
 * 作者:yangliang
 */
public class BackgroundDrawable extends Drawable {

    private final Paint paint;
    private final Context mContext;

    public BackgroundDrawable(@ColorInt int color, Context context) {
        //上下文
        mContext = context.getApplicationContext();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//画笔
        paint.setColor(color);
        paint.setDither(true);//设置防抖动
        paint.setStyle(Paint.Style.FILL);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(@NonNull Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawRoundRect(0, 0, width, height, dp2px(20), dp2px(20), paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    /**
     * 设置半透明
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private int dp2px(int values) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, values,
                mContext.getResources().getDisplayMetrics());
    }
}
