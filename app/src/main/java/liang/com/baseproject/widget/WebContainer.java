package liang.com.baseproject.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * 创建日期：2019/9/3 on 11:12
 * 描述: WebContainer
 * 作者: liangyang
 */
public class WebContainer extends FrameLayout {

    private final float mTouchSlop;
    private final long mTapTimeout;
    private final long mDoubleTapTimeout;

    private long mDownTime = 0L;
    private float mDownX = 0;
    private float mDownY = 0;
    private float mLastDownX = 0;
    private float mLastDownY = 0;
    private long mLastTouchTime = 0L;

    private OnDoubleClickListener mOnDoubleClickListener = null;

    public WebContainer(@NonNull Context context) {
        this(context, null);
    }

    public WebContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.getTouchSlop();
        mTapTimeout = ViewConfiguration.getTapTimeout();
        mDoubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        mOnDoubleClickListener = onDoubleClickListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            default:
                break;
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                long currTime = System.currentTimeMillis();
                boolean isClick = getDistance(mDownX, mDownY, upX, upY) < mTouchSlop &&
                        currTime - mDownTime < mTapTimeout;
                if (isClick) {
                    if (currTime - mLastTouchTime < mDoubleTapTimeout &&
                            getDistance(mDownX, mDownY, mLastDownX, mLastDownY) < mTouchSlop * 5) {
                        if (mOnDoubleClickListener != null) {
                            mOnDoubleClickListener.onDoubleClick();
                        }
                    }
                    mLastDownX = mDownX;
                    mLastDownY = mDownY;
                    mLastTouchTime = currTime;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
    }

    public interface OnDoubleClickListener {
        void onDoubleClick();
    }
}
