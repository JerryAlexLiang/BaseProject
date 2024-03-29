package com.liang.module_core.widget.pageMenuLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.liang.module_core.R;
import com.liang.module_core.utils.ScreenUtil;

/**
 * 创建日期: 2020/12/7 on 3:51 PM
 * 描述:
 * 作者: 杨亮
 */
public class PageSlideIndicatorViewRect extends View {

    private int indicatorColor;
    private int indicatorColorSelected;
    private int indicatorWidth = 0;
    private int indicatorHeight =0;
    private int gravity = 0;
    private Paint mPaint;
    private int indicatorCount = 0;
    private int currentIndicator = 0;
    private final Context mContext;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x12) {
                invalidate();
            }
        }
    };

    public PageSlideIndicatorViewRect(Context context) {
        this(context, null, 0);
    }

    public PageSlideIndicatorViewRect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageSlideIndicatorViewRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PageSlideIndicatorView);
            indicatorColor = typedArray.getColor(R.styleable.PageSlideIndicatorView_indicatorColor, Color.rgb(0, 0, 0));
            indicatorColorSelected = typedArray.getColor(R.styleable.PageSlideIndicatorView_indicatorColorSelected, Color.rgb(0, 0, 0));
            indicatorWidth = ScreenUtil.instance(context).dip2px(typedArray.getInt(R.styleable.PageSlideIndicatorView_indicatorWidth, 0));
            indicatorHeight = ScreenUtil.instance(context).dip2px(typedArray.getInt(R.styleable.PageSlideIndicatorView_indicatorHeight,0));
            gravity = typedArray.getInt(R.styleable.PageSlideIndicatorView_gravity, 0);
            typedArray.recycle();
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        indicatorColor = Color.rgb(0, 0, 0);
        indicatorColorSelected = Color.rgb(0, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int totalWidth = indicatorWidth * (2 * indicatorCount - 1);

        if (indicatorCount > 0) {
            for (int i = 0; i < indicatorCount; i++) {
                if (i == currentIndicator) {
                    mPaint.setColor(indicatorColorSelected);
                } else {
                    mPaint.setColor(indicatorColor);
                }
                int left = (viewWidth - totalWidth) / 2 + (i * indicatorWidth) + indicatorWidth/2;
                switch (gravity) {
                    case 0:
                        left = (viewWidth - totalWidth) / 2 + (i * indicatorWidth) + indicatorWidth/2;
                        break;
                    case 1:
                        left = i * 2 * indicatorWidth;
                        break;
                    case 2:
                        left = viewWidth - totalWidth + (i * indicatorWidth);
                        break;
                    default:
                        break;
                }
                int top = (viewHeight - indicatorWidth) / 2;
                int right = left + indicatorWidth;
                int bottom = top + indicatorWidth;
                RectF rectF = new RectF(left, top, right, bottom);

                canvas.drawRect(rectF, mPaint);
                int radius = indicatorHeight/2;
                if (i == 0) {
                    canvas.drawCircle(left + 2, radius, radius, mPaint);
                } else if (i == indicatorCount - 1) {
                    canvas.drawCircle(right - 2, radius, radius, mPaint);
                }
            }
        }
    }

    public void setIndicatorCount(int indicatorCount) {
        this.indicatorCount = indicatorCount;
    }

    public void setCurrentIndicator(int currentIndicator) {
        this.currentIndicator = currentIndicator;
        handler.sendEmptyMessage(0x12);
    }

}