package liang.com.baseproject.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import liang.com.baseproject.R;
import liang.com.baseproject.utils.ColorUtils;
import liang.com.baseproject.utils.SettingUtils;
/**
 * 创建日期：2019/10/30 on 14:01
 * 描述: 自定义的RelativeLayout-CollapsingToolbarLayout-滑动冲突
 * 作者: liangyang
 */
public class CustomScrollRelativeLayout extends RelativeLayout {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean mDarkTheme;
    private int mMaskColor = Color.TRANSPARENT;

    public CustomScrollRelativeLayout(Context context) {
        super(context);
    }

    public CustomScrollRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
//        if (mDarkTheme) {
//            mMaskColor = ColorUtils.alphaColor(ContextCompat.getColor(getContext(), R.color.background), 0.6F);
//        }
    }

    public void setCollapsingToolbarLayout(CollapsingToolbarLayout collapsingToolbarLayout) {
        this.collapsingToolbarLayout = collapsingToolbarLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            collapsingToolbarLayout.requestDisallowInterceptTouchEvent(false);
        } else {
            collapsingToolbarLayout.requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mDarkTheme) {
            canvas.drawColor(mMaskColor);
        }
    }
}
