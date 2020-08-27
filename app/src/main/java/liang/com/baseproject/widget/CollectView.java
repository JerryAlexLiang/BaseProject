package liang.com.baseproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.liang.module_core.widget.RevealLayout;

import liang.com.baseproject.R;
import liang.com.baseproject.utils.UserLoginUtils;


public class CollectView extends RevealLayout implements View.OnTouchListener {

    private OnClickListener mOnClickListener = null;

    public CollectView(Context context) {
        this(context, null);
    }

    public CollectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initAttr(AttributeSet attrs) {
        super.initAttr(attrs);
        setCheckWithExpand(true);
        setUncheckWithExpand(false);
        setCheckedLayoutId(R.layout.layout_collect_view_checked);
        setUncheckedLayoutId(R.layout.layout_collect_view_unchecked);
        setAnimDuration(500);
        setAllowRevert(true);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            default:
                break;
            case MotionEvent.ACTION_UP:
                if (UserLoginUtils.getInstance().doIfNeedLogin()) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(this);
                    }
                }
                break;
        }
        return !UserLoginUtils.getInstance().isLogin();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(CollectView v);
    }
}