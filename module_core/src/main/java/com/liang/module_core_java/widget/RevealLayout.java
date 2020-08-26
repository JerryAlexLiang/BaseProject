package com.liang.module_core_java.widget;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.liang.module_core_java.R;

/**
 * 创建日期：2019/8/30 on 14:28
 * 描述: 揭示效果布局
 * 可以指定2个子布局，以圆形揭示效果切换选中状态
 * 作者: liangyang
 */
public class RevealLayout extends FrameLayout {

    private View mCheckedView;
    private View mUncheckedView;

    private int mCheckedLayoutId;
    private int mUncheckedLayoutId;
    private boolean mChecked;
    private long mAnimDuration;
    private boolean mCheckWithExpand;
    private boolean mUncheckWithExpand;
    private boolean mAllowRevert;

    private float mCenterX;
    private float mCenterY;
    private float mRevealRadius = 0;
    private final Path mPath = new Path();
    private ValueAnimator mAnimator;
    private TimeInterpolator mInterpolator = null;

    private OnCheckedChangeListener mOnCheckedChangeListener = null;
    private OnAnimStateChangeListener mOnAnimStateChangeListener = null;

    public RevealLayout(Context context) {
        this(context, null);
    }

    public RevealLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initView();
    }

    /**
     * 设置选中状态改变的监听器
     *
     * @param onCheckedChangeListener OnCheckedChangeListener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    /**
     * 设置动画状态改变的监听器
     *
     * @param onAnimStateChangeListener OnAnimStateChangeListener
     */
    public void setOnAnimStateChangeListener(OnAnimStateChangeListener onAnimStateChangeListener) {
        mOnAnimStateChangeListener = onAnimStateChangeListener;
    }

    /**
     * 获取当前选中状态
     *
     * @return 是否选中
     */
    public boolean isChecked() {
        return mChecked;
    }

    /**
     * 设置选中状态，一般在初始化时调用
     *
     * @param checked 是否选中
     */
    public void setChecked(boolean checked) {
        mChecked = checked;
        if (mChecked) {
            mCheckedView.bringToFront();
        } else {
            mUncheckedView.bringToFront();
        }
    }

    /**
     * 切换选中状态，带有动画效果
     */
    public void toggle() {
        mChecked = !mChecked;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
        }
        if (mAnimator != null) {
            mAnimator.reverse();
            if (mOnAnimStateChangeListener != null) {
                mOnAnimStateChangeListener.onReverse();
            }
        } else {
            createRevealAnim();
        }
    }

    public void setAllowRevert(boolean allowRevert) {
        mAllowRevert = allowRevert;
    }

    public void setAnimDuration(long animDuration) {
        mAnimDuration = animDuration;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setCheckWithExpand(boolean checkWithExpand) {
        mCheckWithExpand = checkWithExpand;
    }

    public void setUncheckWithExpand(boolean uncheckWithExpand) {
        mUncheckWithExpand = uncheckWithExpand;
    }

    public void setCheckedView(View checkedView) {
        mCheckedView = checkedView;
        initView();
    }

    public void setUncheckedView(View uncheckedView) {
        mUncheckedView = uncheckedView;
        initView();
    }

    public void setCheckedLayoutId(int checkedLayoutId) {
        mCheckedLayoutId = checkedLayoutId;
        setCheckedView(createCheckedView());
    }

    public void setUncheckedLayoutId(int uncheckedLayoutId) {
        mUncheckedLayoutId = uncheckedLayoutId;
        setUncheckedView(createUncheckedView());
    }

    /**
     * 获取布局文件携带的属性，子类复写该方法，获取子类定义的属性。
     * 获取到子类属性后可以在{@link #createCheckedView()}和{@link #createUncheckedView()}中使用
     *
     * @param attrs AttributeSet
     */
    protected void initAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RevealLayout);
        mCheckedLayoutId = array.getResourceId(R.styleable.RevealLayout_rl_checked_layout, 0);
        mUncheckedLayoutId = array.getResourceId(R.styleable.RevealLayout_rl_unchecked_layout, 0);
        mChecked = array.getBoolean(R.styleable.RevealLayout_rl_checked, false);
        mAnimDuration = array.getInteger(R.styleable.RevealLayout_rl_anim_duration, 500);
        mCheckWithExpand = array.getBoolean(R.styleable.RevealLayout_rl_check_with_expand, true);
        mUncheckWithExpand = array.getBoolean(R.styleable.RevealLayout_rl_uncheck_with_expand, false);
        mAllowRevert = array.getBoolean(R.styleable.RevealLayout_rl_allow_revert, false);
        array.recycle();
    }

    /**
     * 创建选中状态的控件，子类可复写该方法，初始化自己的控件
     *
     * @return 选中状态的控件
     */
    protected View createCheckedView() {
        View checkedView;
        if (mCheckedLayoutId > 0) {
            checkedView = inflate(getContext(), mCheckedLayoutId, null);
        } else {
            checkedView = new View(getContext());
        }
        return checkedView;
    }

    /**
     * 创建非选中状态的控件，子类可复写该方法，初始化自己的控件
     *
     * @return 非选中状态的控件
     */
    protected View createUncheckedView() {
        View uncheckedView;
        if (mUncheckedLayoutId > 0) {
            uncheckedView = inflate(getContext(), mUncheckedLayoutId, null);
        } else {
            uncheckedView = new View(getContext());
        }
        return uncheckedView;
    }

    /**
     * 初始化选中和未选中状态的控件，并设置默认状态
     */
    private void initView() {
        removeAllViews();
        if (mCheckedView == null) {
            mCheckedView = createCheckedView();
        }
        if (mUncheckedView == null) {
            mUncheckedView = createUncheckedView();
        }
        addView(mCheckedView, getDefaultLayoutParams());
        addView(mUncheckedView, getDefaultLayoutParams());
        setChecked(mChecked);
    }

    private LayoutParams getDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        return params;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                return true;
            default:
                break;
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return isValidClick(event.getX(), event.getY());
            case MotionEvent.ACTION_UP:
                float unX = event.getX();
                float unY = event.getY();
                if (isValidClick(unX, unY)) {
                    if (mAnimator != null) {
                        if (mAllowRevert) {
                            toggle();
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        mRevealRadius = 0;
                        mCenterX = unX;
                        mCenterY = unY;
                        toggle();
                        return true;
                    }
                } else {
                    return false;
                }
            default:
                break;
        }
        return false;
    }

    /**
     * 判断触摸位置是否在view内部，是否是合法点击
     *
     * @param x 触摸点x坐标
     * @param y 触摸点y坐标
     * @return 点击是否合法
     */
    private boolean isValidClick(float x, float y) {
        return x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight();
    }

    /**
     * 创建揭示动画
     */
    private void createRevealAnim() {
        float[] value = calculateAnimOfFloat();
        mRevealRadius = value[0];
        mAnimator = ValueAnimator.ofFloat(value[0], value[1]);
        mAnimator.setInterpolator(mInterpolator != null ? mInterpolator : new DecelerateInterpolator());
        mAnimator.setDuration(mAnimDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRevealRadius = (float) animation.getAnimatedValue();
                resetPath();
                invalidate();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                resetPath();
                bringCurrentViewToFront();
                if (mOnAnimStateChangeListener != null) {
                    mOnAnimStateChangeListener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimator = null;
                bringCurrentViewToFront();
                if (mOnAnimStateChangeListener != null) {
                    mOnAnimStateChangeListener.onEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimator = null;
                if (mOnAnimStateChangeListener != null) {
                    mOnAnimStateChangeListener.onCancel();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();
    }

    /**
     * 根据选中状态和揭示动画的扩散效果计算动画开始和结束半径
     *
     * @return {起始半径，结束半径}
     */
    private float[] calculateAnimOfFloat() {
        float fromValue;
        float toValue;
        float maxRadius = calculateMaxRadius();
        if (mChecked) {
            if (mCheckWithExpand) {
                fromValue = 0;
                toValue = maxRadius;
            } else {
                fromValue = maxRadius;
                toValue = 0;
            }
        } else {
            if (mUncheckWithExpand) {
                fromValue = 0;
                toValue = maxRadius;
            } else {
                fromValue = maxRadius;
                toValue = 0;
            }
        }
        return new float[]{fromValue, toValue};
    }

    private void resetPath() {
        mPath.reset();
        mPath.addCircle(mCenterX, mCenterY, mRevealRadius, Path.Direction.CW);
    }

    /**
     * 将当前状态的view显示在顶部
     */
    private void bringCurrentViewToFront() {
        if (mRevealRadius == 0) {
            if (mChecked) {
                mCheckedView.bringToFront();
            } else {
                mUncheckedView.bringToFront();
            }
        }
    }

    /**
     * 计算揭示效果做大圆形半径，及圆心到4个边角的最大距离
     *
     * @return 最大半径
     */
    private float calculateMaxRadius() {
        float x = Math.max(mCenterX, getMeasuredWidth() - mCenterX);
        float y = Math.max(mCenterY, getMeasuredHeight() - mCenterY);
        return (float) Math.hypot(x, y);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (mAnimator == null) {
            return super.drawChild(canvas, child, drawingTime);
        }
        if (isBottomChild(child)) {
            return super.drawChild(canvas, child, drawingTime);
        }
        canvas.save();
        canvas.clipPath(mPath);
        boolean drawChild = super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return drawChild;
    }

    private boolean isBottomChild(View child) {
        return getChildAt(0) == child;
    }

    public interface OnCheckedChangeListener {
        /**
         * 选中状态改变
         *
         * @param revealLayout RevealLayout
         * @param isChecked    当前选中状态
         */
        void onCheckedChanged(RevealLayout revealLayout, boolean isChecked);
    }

    public interface OnAnimStateChangeListener {
        /**
         * 动画开始时调用
         */
        void onStart();

        /**
         * 动画回滚时调用。即上一个动画尚未结束时切换选中状态，动画回滚到之前状态
         */
        void onReverse();

        /**
         * 动画结束时调用
         */
        void onEnd();

        /**
         * 动画取消时调用
         */
        void onCancel();
    }

}
