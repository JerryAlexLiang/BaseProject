package liang.com.baseproject.View;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 创建日期：2019/2/20 on 13:59
 * 描述: 聚合新闻详情
 * 作者: liangyang
 */
public interface JuheNewsDetailWebView {

    ProgressBar getProgressBar();

    WebView getWebView();

    ImageView getErrorView();

    void setToast(String content);
}
