package liang.com.baseproject.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import liang.com.baseproject.View.JuheNewsDetailWebView;
import liang.com.baseproject.base.BasePresenter;
import liang.com.baseproject.utils.LogUtil;

/**
 * 创建日期：2019/2/20 on 15:46
 * 描述: 聚合新闻详情页
 * 作者: liangyang
 */
public class JuheNewsDetailPresenter extends BasePresenter<JuheNewsDetailWebView> {

    private Activity activity;

    public JuheNewsDetailPresenter(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setWebView(String url) {

        JuheNewsDetailWebView urlView = getView();
        ProgressBar progressBar = urlView.getProgressBar();
        WebView webView = urlView.getWebView();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        settings.setBuiltInZoomControls(false);// 不显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击放大缩小

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                urlView.setToast("正在加载...");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                urlView.setToast("加载结束!");
                progressBar.setVisibility(View.GONE);
            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                LogUtil.d("网页标题: ", title);
                activity.setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });

        webView.loadUrl(url);

    }
}
