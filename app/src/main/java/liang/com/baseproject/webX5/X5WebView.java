package liang.com.baseproject.webX5;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.view.ScrollingView;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * 创建日期: 2021/6/18 on 3:36 PM
 * 描述:
 * 作者: 杨亮
 */
public class X5WebView extends WebView implements ScrollingView {

    public X5WebView(Context context) {
        super(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return getView().canScrollHorizontally(direction);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return getView().canScrollVertically(direction);
    }
}
