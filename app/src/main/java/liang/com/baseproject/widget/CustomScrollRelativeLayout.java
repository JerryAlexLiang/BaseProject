package liang.com.baseproject.widget;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class CustomScrollRelativeLayout extends RelativeLayout {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    public CustomScrollRelativeLayout(Context context) {
        super(context);
    }

    public CustomScrollRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
}
