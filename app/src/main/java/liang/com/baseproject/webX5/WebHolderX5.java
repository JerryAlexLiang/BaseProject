package liang.com.baseproject.webX5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.liang.module_core.utils.SettingUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import liang.com.baseproject.R;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * 创建日期: 2021/6/18 on 3:42 PM
 * 描述:
 * 作者: 杨亮
 */
public class WebHolderX5 {

    private OnPageTitleCallback mOnPageTitleCallback = null;
    private OnPageLoadCallback mOnPageLoadCallback = null;
    private OnPageProgressCallback mOnPageProgressCallback = null;
    private OnHistoryUpdateCallback mOnHistoryUpdateCallback = null;

    private boolean isProgressShown = false;
    private final WebView mWebView;
    private final MaterialProgressBar mProgressBar;
    private final Activity activity;

    public static WebHolderX5 with(Activity activity, WebContainerX5 container) {
        return new WebHolderX5(activity, container);
    }

//    private static void syncCookiesForWanAndroid(String url) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        String host = Uri.parse(url).getHost();
//        if (!TextUtils.equals(host, "www.wanandroid.com")) {
//            return;
//        }
//        List<Cookie> cookies = WanApp.getCookieJar().loadForRequest(HttpUrl.get(url));
//        if (cookies == null || cookies.isEmpty()) {
//            return;
//        }
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.removeSessionCookie();
//            cookieManager.removeExpiredCookie();
//        } else {
//            cookieManager.removeSessionCookies(null);
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            for (Cookie cookie : cookies) {
//                cookieManager.setCookie(url, cookie.name() + "=" + cookie.value());
//            }
//            CookieSyncManager.createInstance(WanApp.getAppContext());
//            CookieSyncManager.getInstance().sync();
//        } else {
//            for (Cookie cookie : cookies) {
//                cookieManager.setCookie(url, cookie.name() + "=" + cookie.value());
//            }
//            cookieManager.flush();
//        }
//    }

    @SuppressLint("SetJavaScriptEnabled")
    private WebHolderX5(Activity activity, WebContainerX5 container) {
        this.activity = activity;
//        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mWebView = new X5WebView(activity);
        mProgressBar = (MaterialProgressBar) LayoutInflater.from(activity).inflate(R.layout.basic_ui_progress_bar, container, false);
        mProgressBar.setMax(100);
        container.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        container.addView(mProgressBar, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                activity.getResources().getDimensionPixelSize(R.dimen.dp1)));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CookieManager.getInstance().setAcceptThirdPartyCookies(getWebView(), true);
//        }
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        mWebView.getView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setWebChromeClient(new CustomWebX5ChromeClient());
        mWebView.setWebViewClient(new CustomWebX5ViewClient());
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public WebView getWebView() {
        return mWebView;
    }

    public WebHolderX5 loadUrl(String url) {
        getWebView().loadUrl(url);
        return this;
    }

    @NonNull
    public String getUrl() {
        String url = getWebView().getUrl();
        return url == null ? "" : url;
    }

    @NonNull
    public String getTitle() {
        String title = getWebView().getTitle();
        return title == null ? "" : title;
    }

    public boolean canGoBack() {
        return getWebView().canGoBack();
    }

    public boolean canGoForward() {
        return getWebView().canGoForward();
    }

    public boolean canGoBackOrForward(int steps) {
        return getWebView().canGoBackOrForward(steps);
    }

    public void goBack() {
        getWebView().goBack();
    }

    public void goForward() {
        getWebView().goForward();
    }

    public void goBackOrForward(int steps) {
        getWebView().goBackOrForward(steps);
    }

    public void reload() {
        getWebView().reload();
    }

    public void stopLoading() {
        getWebView().stopLoading();
    }

    public void onPause() {
        getWebView().onPause();
    }

    public void onResume() {
        getWebView().onResume();
    }

    public void onDestroy() {
        ViewParent parent = getWebView().getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(getWebView());
        }
        getWebView().removeAllViews();
        getWebView().loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        getWebView().stopLoading();
        getWebView().setWebChromeClient(null);
        getWebView().setWebViewClient(null);
        getWebView().destroy();
    }

    public boolean handleKeyEvent(int keyCode, KeyEvent keyEvent) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (getWebView().canGoBack()) {
            getWebView().goBack();
            return true;
        }
        return false;
    }

    public WebHolderX5 setOnPageTitleCallback(OnPageTitleCallback onPageTitleCallback) {
        mOnPageTitleCallback = onPageTitleCallback;
        return this;
    }

    public WebHolderX5 setOnPageLoadCallback(OnPageLoadCallback onPageLoadCallback) {
        mOnPageLoadCallback = onPageLoadCallback;
        return this;
    }

    public WebHolderX5 setOnPageProgressCallback(OnPageProgressCallback onPageProgressCallback) {
        mOnPageProgressCallback = onPageProgressCallback;
        return this;
    }

    public WebHolderX5 setOnHistoryUpdateCallback(OnHistoryUpdateCallback onHistoryUpdateCallback) {
        mOnHistoryUpdateCallback = onHistoryUpdateCallback;
        return this;
    }

    private View customView;
    private IX5WebChromeClient.CustomViewCallback customViewCallback;

    private void showCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        int requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        activity.setRequestedOrientation(requestedOrientation);

        if (customView != null || view.getParent() != null) {
            callback.onCustomViewHidden();
            return;
        }
        FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();
        frameLayout.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    private void hideCustomView() {
        int requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        activity.setRequestedOrientation(requestedOrientation);

        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);

        FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();
        frameLayout.removeView(customView);
        customView = null;
        customViewCallback.onCustomViewHidden();
        mWebView.setVisibility(View.VISIBLE);
    }

    private void setStatusBarVisibility(boolean visible) {
        int flagFullscreen;
        if (visible) {
            flagFullscreen = 0;
        } else {
            flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        activity.getWindow().setFlags(flagFullscreen, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public class CustomWebX5ChromeClient extends WebChromeClient {

        //WebView使用h5播放视频时横竖屏切换的解决方法
        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            super.onShowCustomView(view, customViewCallback);
            showCustomView(view, customViewCallback);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            hideCustomView();
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 95) {
                if (!isProgressShown) {
                    isProgressShown = true;
                    onShowProgress();
                }
                onProgressChanged(newProgress);
            } else {
                onProgressChanged(newProgress);
                if (isProgressShown) {
                    isProgressShown = false;
                    onHideProgress();
                }
            }
        }

        private void onShowProgress() {
            mProgressBar.setVisibility(View.VISIBLE);
            setProgress(0);
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onShowProgress();
            }
        }

        private void onProgressChanged(int progress) {
            setProgress(progress);
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onProgressChanged(progress);
            }
        }

        private void onHideProgress() {
            mProgressBar.setVisibility(View.GONE);
            setProgress(100);
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onHideProgress();
            }
        }

        private void setProgress(int progress) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProgressBar.setProgress(progress, true);
            } else {
                mProgressBar.setProgress(progress);
            }
        }
    }

    public class CustomWebX5ViewClient extends WebViewClient {

        private boolean shouldInterceptRequest(Uri uri) {
//            syncCookiesForWanAndroid(uri.toString());
            return false;
        }

        private boolean shouldOverrideUrlLoading(String url) {
//        private boolean shouldOverrideUrlLoading(Uri uri) {
//            switch (SettingUtils.getInstance().getUrlInterceptType()) {
//                default:
//                case HostInterceptUtils.TYPE_NOTHING:
//                    return false;
//                case HostInterceptUtils.TYPE_ONLY_WHITE:
//                    return !HostInterceptUtils.isWhiteHost(uri.getHost());
//                case HostInterceptUtils.TYPE_INTERCEPT_BLACK:
//                    return HostInterceptUtils.isBlackHost(uri.getHost());
//            }

            if (url == null) return false;
            try {
                if (url.startsWith("alipays:") || url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(intent);
                    return true;
                } else if (url.contains(".apk?") || url.endsWith(".apk")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (isXiaomi()) {
                        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    }
                    activity.startActivity(intent);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            mWebView.loadUrl(url);
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (shouldInterceptRequest(Uri.parse(url))) {
                return new WebResourceResponse(null, null, null);
            }
            return super.shouldInterceptRequest(view, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (shouldInterceptRequest(request.getUrl())) {
                return new WebResourceResponse(null, null, null);
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//            return shouldOverrideUrlLoading(Uri.parse(url));
            return shouldOverrideUrlLoading(url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl() + "");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(getUrl());
            }
            if (mOnPageLoadCallback != null) {
                mOnPageLoadCallback.onPageStarted();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(getTitle());
            }
            if (mOnPageLoadCallback != null) {
                mOnPageLoadCallback.onPageFinished();
            }
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            if (mOnHistoryUpdateCallback != null) {
                mOnHistoryUpdateCallback.onHistoryUpdate(isReload);
            }
        }
    }

    // 是否是小米手机
    private static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

    public interface OnPageTitleCallback {
        void onReceivedTitle(@NonNull String title);
    }

    public interface OnPageLoadCallback {
        void onPageStarted();

        void onPageFinished();
    }

    public interface OnPageProgressCallback {
        void onShowProgress();

        void onProgressChanged(int progress);

        void onHideProgress();
    }

    public interface OnHistoryUpdateCallback {
        void onHistoryUpdate(boolean isReload);
    }

} 
