package liang.com.baseproject.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import liang.com.baseproject.View.JuheNewsDetailWebView;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.utils.LogUtil;

/**
 * 创建日期：2019/2/20 on 15:46
 * 描述: 聚合新闻详情页
 * 作者: liangyang
 */
public class JuheNewsDetailPresenter extends MVPBasePresenter<JuheNewsDetailWebView> {

    private Activity activity;

    public JuheNewsDetailPresenter(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setWebView(String url) {

        JuheNewsDetailWebView urlView = getView();
        ProgressBar progressBar = urlView.getProgressBar();
        WebView webView = urlView.getWebView();
        ImageView errorView = urlView.getErrorView();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        settings.setBuiltInZoomControls(false);// 不显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击放大缩小

        webView.setWebViewClient(new WebViewClient() {
            //在开始加载网页时会回调
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                urlView.setToast("正在加载...");
                progressBar.setVisibility(View.VISIBLE);
            }

            //加载错误的时候会回调
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return;
                }
                errorView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.isForMainFrame()){
                        errorView.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                    }
                }
            }

            //加载完成的时候会回调
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

        //点击加载失败界面，重新载入这个网页；
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
                errorView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

    }
}
