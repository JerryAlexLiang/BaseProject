package com.liang.module_core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liang.module_core.R;

/**
 * 创建日期：2018/1/11 on 上午9:00
 * 描述:自定义可展开折叠的TextView
 * 作者:yangliang
 */
public class ExpandableTextView extends LinearLayout {

    TextView id_source_textView;
    TextView id_expand_textView;

    //允许显示最大行数
    int maxExpandLines = 0;
    //动画执行时间
    int duration = 0;

    //是否发生过文字变动
    boolean isChange = false;
    //文本框真实高度
    int realTextViewHeigt = 0;
    //默认处于收起状态
    boolean isCollapsed = true;
    //收起时候的整体高度
    int collapsedHeight = 0;
    //剩余点击按钮的高度
    int lastHeight = 0;
    //是否正在执行动画
    boolean isAnimate = false;

    OnExpandStateChangeListener listener;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextViewAttr);
        //设置默认最大显示行数
        maxExpandLines = array.getInteger(R.styleable.ExpandableTextViewAttr_maxExpandLines, 3);
        //设置动画执行时间
        duration = array.getInteger(R.styleable.ExpandableTextViewAttr_duration, 500);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        id_source_textView = findViewById(R.id.id_source_textview);
        id_expand_textView = findViewById(R.id.id_expand_textview);
        id_expand_textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandCollapseAnimation animation;

                isCollapsed = !isCollapsed;
                if (isCollapsed) {
                    id_expand_textView.setText("展开全部");
                    //修改TextView的DrawableRight图片
                    Drawable drawableDown = getResources().getDrawable(R.drawable.core_icon_expand_open);
                    drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
                    id_expand_textView.setCompoundDrawables(null, null, drawableDown, null);
                    if (listener != null) {
                        listener.onExpandStateChanged(true);
                    }
                    animation = new ExpandCollapseAnimation(getHeight(), collapsedHeight);
                } else {
                    id_expand_textView.setText("收起全部");
                    //修改TextView的DrawableRight图片
                    Drawable drawableUp = getResources().getDrawable(R.drawable.core_icon_expand_close);
                    drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
                    id_expand_textView.setCompoundDrawables(null, null, drawableUp, null);
                    if (listener != null) {
                        listener.onExpandStateChanged(false);
                    }
                    animation = new ExpandCollapseAnimation(getHeight(), realTextViewHeigt + lastHeight);
                }
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isAnimate = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        clearAnimation();
                        isAnimate = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                clearAnimation();
                startAnimation(animation);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //执行动画的过程中屏蔽事件
        return isAnimate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果隐藏控件或者textview的值没有发生改变，那么不进行测量
        if (getVisibility() == GONE || !isChange) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        isChange = false;

        //初始化默认状态，即正常显示文本
        id_expand_textView.setVisibility(GONE);
        id_source_textView.setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果本身没有达到收起展开的限定要求，则不进行处理
        if (id_source_textView.getLineCount() <= maxExpandLines) {
            return;
        }

        //初始化高度赋值，为后续动画事件准备数据
        realTextViewHeigt = getRealTextViewHeight(id_source_textView);

        //如果处于收缩状态，则设置最多显示行数
        if (isCollapsed) {
            id_source_textView.setMaxLines(maxExpandLines);
        }
        id_expand_textView.setVisibility(VISIBLE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isCollapsed) {
            id_source_textView.post(new Runnable() {
                @Override
                public void run() {
                    lastHeight = getHeight() - id_source_textView.getHeight();
                    collapsedHeight = getMeasuredHeight();
                }
            });
        }
    }

    /**
     * 获取textview的真实高度
     *
     * @param textView
     * @return
     */
    private int getRealTextViewHeight(TextView textView) {
        //getLineTop返回值是一个根据行数而形成等差序列，如果参数为行数，则值即为文本的高度
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        return textHeight + textView.getCompoundPaddingBottom() + textView.getCompoundPaddingTop();
    }

    public void setText(String text) {
        isChange = true;
        id_source_textView.setText(text);
    }

    public void setText(String text, boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
        if (isCollapsed) {
            id_expand_textView.setText("展开");
        } else {
            id_expand_textView.setText("收起");
        }
        clearAnimation();
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public void setListener(OnExpandStateChangeListener listener) {
        this.listener = listener;
    }

    private class ExpandCollapseAnimation extends Animation {
        int startValue = 0;
        int endValue = 0;

        public ExpandCollapseAnimation(int startValue, int endValue) {
            setDuration(duration);
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            int height = (int) ((endValue - startValue) * interpolatedTime + startValue);
            id_source_textView.setMaxHeight(height - lastHeight);
            ExpandableTextView.this.getLayoutParams().height = height;
            ExpandableTextView.this.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnExpandStateChangeListener {
        void onExpandStateChanged(boolean isExpanded);
    }
}
