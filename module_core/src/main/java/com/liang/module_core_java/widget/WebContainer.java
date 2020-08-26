package com.liang.module_core_java.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.liang.module_core_java.R;
import com.liang.module_core_java.utils.ColorUtils;
import com.liang.module_core_java.utils.SettingUtils;

//
///**
// * 创建日期：2019/9/3 on 11:12
// * 描述: WebContainer
// * 作者: liangyang
// */
//public class WebContainer extends FrameLayout {
//
//    private final float mTouchSlop;
//    private final long mTapTimeout;
//    private final long mDoubleTapTimeout;
//    private final boolean mDarkTheme;
//
//    private int mMaskColor = Color.TRANSPARENT;
//
//    private long mDownTime = 0L;
//    private float mDownX = 0;
//    private float mDownY = 0;
//    private float mLastDownX = 0;
//    private float mLastDownY = 0;
//    private long mLastTouchTime = 0L;
//
//    private OnDoubleClickListener mOnDoubleClickListener = null;
//
//    public WebContainer(@NonNull Context context) {
//        this(context, null);
//    }
//
//    public WebContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public WebContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        mTouchSlop = ViewConfiguration.getTouchSlop();
//        mTapTimeout = ViewConfiguration.getTapTimeout();
//        mDoubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
//
//        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
//        if (mDarkTheme) {
//            mMaskColor = ColorUtils.alphaColor(ContextCompat.getColor(getContext(), R.color.background), 0.6F);
//        }
//    }
//
//    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
//        mOnDoubleClickListener = onDoubleClickListener;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            default:
//                break;
//            case MotionEvent.ACTION_DOWN:
//                mDownX = event.getX();
//                mDownY = event.getY();
//                mDownTime = System.currentTimeMillis();
//                break;
//            case MotionEvent.ACTION_UP:
//                float upX = event.getX();
//                float upY = event.getY();
//                long currTime = System.currentTimeMillis();
//                boolean isClick = getDistance(mDownX, mDownY, upX, upY) < mTouchSlop &&
//                        currTime - mDownTime < mTapTimeout;
//                if (isClick) {
//                    if (currTime - mLastTouchTime < mDoubleTapTimeout &&
//                            getDistance(mDownX, mDownY, mLastDownX, mLastDownY) < mTouchSlop * 5) {
//                        if (mOnDoubleClickListener != null) {
//                            mOnDoubleClickListener.onDoubleClick();
//                        }
//                    }
//                    mLastDownX = mDownX;
//                    mLastDownY = mDownY;
//                    mLastTouchTime = currTime;
//                }
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }
//
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        if (mDarkTheme) {
//            canvas.drawColor(mMaskColor);
//        }
//    }
//
//    private double getDistance(double x1, double y1, double x2, double y2) {
//        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
//    }
//
//    public interface OnDoubleClickListener {
//        void onDoubleClick();
//    }
//}


/**
 * @author CuiZhen
 * @date 2019/8/31
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class WebContainer extends FrameLayout {

    private final float mTouchSlop;
    private final long mTapTimeout;
    private final long mDoubleTapTimeout;
    private final boolean mDarkTheme;

    private int mMaskColor = Color.TRANSPARENT;

    private long mDownTime = 0L;
    private float mDownX = 0;
    private float mDownY = 0;
    private float mLastDownX = 0;
    private float mLastDownY = 0;
    private long mLastTouchTime = 0L;

    private OnDoubleClickListener mOnDoubleClickListener = null;
    private OnTouchDownListener mOnTouchDownListener = null;
    private ImageView mImageView;

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

        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
        if (mDarkTheme) {
            mMaskColor = ColorUtils.alphaColor(ContextCompat.getColor(getContext(), R.color.background), 0.6F);
        }
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        mOnDoubleClickListener = onDoubleClickListener;
    }

    public void setOnTouchDownListener(OnTouchDownListener onTouchDownListener) {
        mOnTouchDownListener = onTouchDownListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDownX = getWidth() / 2;
        mDownY = getHeight() / 2;
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
                onTouchDown();
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
                        onDoubleClicked(mDownX, mDownY);
                    }
                    mLastDownX = mDownX;
                    mLastDownY = mDownY;
                    mDownX = getWidth() / 2;
                    mDownY = getHeight() / 2;
                    mLastTouchTime = currTime;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mDownX = getWidth() / 2;
                mDownY = getHeight() / 2;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mDarkTheme) {
            canvas.drawColor(mMaskColor);
        }
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
    }

    private void onDoubleClicked(float x, float y) {
        if (mOnDoubleClickListener != null) {
            mOnDoubleClickListener.onDoubleClick(x, y);
        }
    }

    private void onTouchDown() {
        if (mOnTouchDownListener != null) {
            mOnTouchDownListener.onTouchDown();
        }
    }

    public void showCollectAnim() {
        showCollectAnim(mDownX, mDownY);
    }

    public void showCollectAnim(float x, float y) {
        mImageView = createImageView();
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(mImageView, "alpha", 0F, 1F);
        alphaIn.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator scaleXIn = ObjectAnimator.ofFloat(mImageView, "scaleX", 0.3F, 1F);
        scaleXIn.setInterpolator(new OvershootInterpolator());
        ObjectAnimator scaleYIn = ObjectAnimator.ofFloat(mImageView, "scaleY", 0.3F, 1F);
        scaleYIn.setInterpolator(new OvershootInterpolator());
        AnimatorSet setIn = new AnimatorSet();
        setIn.playTogether(alphaIn, scaleXIn, scaleYIn);
        setIn.setDuration(300);
        ObjectAnimator alphaOut = ObjectAnimator.ofFloat(mImageView, "alpha", 1F, 0F);
        alphaIn.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator scaleXOut = ObjectAnimator.ofFloat(mImageView, "scaleX", 1F, 0.3F);
        scaleXOut.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator scaleYOut = ObjectAnimator.ofFloat(mImageView, "scaleY", 1F, 0.3F);
        scaleYOut.setInterpolator(new AccelerateInterpolator());
        AnimatorSet setOut = new AnimatorSet();
        setOut.playTogether(alphaOut, scaleXOut, scaleYOut);
        setOut.setDuration(300);
        setOut.setStartDelay(200);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(setIn, setOut);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(mImageView);
                mImageView = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mImageView.setVisibility(View.INVISIBLE);
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mImageView.setX(x - mImageView.getWidth() / 2);
                mImageView.setY(y - mImageView.getHeight() / 2);
                set.start();
            }
        });
        addView(mImageView);
    }

    private ImageView createImageView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.ic_heart);
        imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.accent));
        imageView.setLayoutParams(new LayoutParams(getWidth() / 3, getWidth() / 3));
        return imageView;
    }

    public interface OnDoubleClickListener {
        void onDoubleClick(float x, float y);
    }

    public interface OnTouchDownListener {
        void onTouchDown();
    }
}
