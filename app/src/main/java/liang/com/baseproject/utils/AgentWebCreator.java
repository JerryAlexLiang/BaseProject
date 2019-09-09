package liang.com.baseproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import liang.com.baseproject.R;

/**
 * 创建日期：2019/9/3 on 14:17
 * 描述: AgentWeb设置类
 * 作者: liangyang
 */
public class AgentWebCreator {

    @SuppressLint("SetJavaScriptEnabled")
    public static AgentWeb create(Activity activity, FrameLayout container, String url, WebChromeClient webChromeClient, WebViewClient webViewClient) {
        AgentWeb agentWeb = AgentWeb.with(activity)
                .setAgentWebParent(container, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(ResUtils.getColor(R.color.red), 1)
                .interceptUnkownUrl()
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.layout_web_error, R.id.rl_web_error_container)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .setWebChromeClient(webChromeClient == null ? new WebChromeClient() : webChromeClient)
                .setWebViewClient(webViewClient == null ? new WebViewClient() : webViewClient)
                .createAgentWeb()
                .ready()
                .go(url);
        agentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        agentWeb.getWebCreator().getWebView().getSettings().setJavaScriptEnabled(true);
        return agentWeb;
    }

    public static AgentWeb create(Activity activity, FrameLayout container, String url) {
        return create(activity, container, url, null, null);
    }

}
