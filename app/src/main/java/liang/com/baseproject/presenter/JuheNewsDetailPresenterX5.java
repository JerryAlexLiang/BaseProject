package liang.com.baseproject.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.utils.LogUtil;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import liang.com.baseproject.View.JuheNewsDetailWebViewX5;


//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceError;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebSettings;
//import android.webkit.WebViewClient;

/**
 * 创建日期：2019/2/20 on 15:46
 * 描述: 聚合新闻详情页
 * 作者: liangyang
 */
public class JuheNewsDetailPresenterX5 extends MVPBasePresenter<JuheNewsDetailWebViewX5> {

    private Activity activity;

    public JuheNewsDetailPresenterX5(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setWebView(String url) {

        JuheNewsDetailWebViewX5 urlView = getView();
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
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                urlView.setToast("正在加载...");
                progressBar.setVisibility(View.VISIBLE);
            }

            //加载错误的时候会回调
            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                super.onReceivedError(webView, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                errorView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (webResourceRequest.isForMainFrame()) {
                        errorView.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            //加载完成的时候会回调
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                urlView.setToast("加载结束!");
                progressBar.setVisibility(View.GONE);
            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return super.shouldOverrideUrlLoading(webView, url);

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
