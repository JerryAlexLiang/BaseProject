package liang.com.baseproject.main.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.utils.IntentUtils;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.utils.ResolutionUtils;
import com.liang.module_core.widget.popupwindow.CustomPopupWindow;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.entity.TagsBean;
import liang.com.baseproject.event.ReadLaterEvent;
import liang.com.baseproject.helperDao.ReadLaterBeanDaoHelpter;
import liang.com.baseproject.home.entity.ArticleBean;
import liang.com.baseproject.main.presenter.AgentWebPresenter;
import liang.com.baseproject.main.view.WebViewInterface;
import liang.com.baseproject.mine.entity.ReadLaterBean;
import liang.com.baseproject.utils.UserLoginUtils;

public class AgentWebActivityX5 extends MVPBaseActivity<WebViewInterface, AgentWebPresenter> implements CustomPopupWindow.ViewInterface, View.OnClickListener, WebViewInterface {

    private static final String TAG = AgentWebActivityX5.class.getSimpleName();
    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    //    @BindView(R.id.web_container)
//    WebContainer webContainer;
    @BindView(R.id.web_x5)
    WebView webViewX5;

    //传递过来的文章ID、标题、作者信息、Url等数据
    private int mArticleId = -1;
    private String mTitle = "";
    private String mAuthor = "";
    private String mUrl = "";

    //当前网页页面标题、链接
    private String mCurrTitle = "";
    private String mCurrUrl = "";
//    private AgentWeb mAgentWeb;

    private CustomPopupWindow customPopupWindow;
    private String superChapterName;
    private String chapterName;
    private String envelopePic;
    private ReadLaterBean readLaterBean;
    private String desc;
    private List<TagsBean> tags;

    public static void actionStart(Context context, ArticleBean articleBean) {
        Intent intent = new Intent(context, AgentWebActivityX5.class);
        intent.putExtra("articleBean", articleBean);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, int articleId, String title, String url) {
        Intent intent = new Intent(context, AgentWebActivityX5.class);
        intent.putExtra("articleId", articleId);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String title, String author, String url) {
        Intent intent = new Intent(context, AgentWebActivityX5.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String title, String url) {
        Intent intent = new Intent(context, AgentWebActivityX5.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return true;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected AgentWebPresenter createPresenter() {
        return new AgentWebPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_agent_web_x5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递过来的数据
        getIntentData();
        //初始化视图
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(mTitle);
        //设置跑马灯效果
        baseActionbarTitle.setSelected(true);
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarLeft2Icon.setVisibility(View.VISIBLE);
        baseActionbarRightIcon.setVisibility(View.VISIBLE);

        //初始化AgentWeb
//        mAgentWeb = AgentWebCreator.create(this, webContainer, mUrl, new WebChromeClient() {
//        mAgentWeb = AgentWebCreator.create(this, webContainer, "http://viewer.weiyunyingxiang.com/s/TVBSLjI5OA==", new WebChromeClient() {
//        mAgentWeb = AgentWebCreator.create(this, webContainer, "http://viewer.weiyunyingxiang.com/s/TVBSLjI5OA==", new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
////                showProgressDialog("Loading...", false);
////                webContainer.setVisibility(View.INVISIBLE);
//                mCurrTitle = title;
//                mCurrUrl = view.getUrl();
//                baseActionbarTitle.setText(mCurrTitle);
//                LogUtil.d(TAG, "title: " + title + "  链接: " + view.getUrl());
//            }
//        }, new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                hideProgressDialog();
////                webContainer.setVisibility(View.VISIBLE);
//            }
//        });

        WebSettings settings = webViewX5.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        settings.setBuiltInZoomControls(false);// 不显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击放大缩小

        webViewX5.setWebViewClient(new WebViewClient() {
            //在开始加载网页时会回调
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                onShowProgress();
            }

            //加载错误的时候会回调
            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                super.onReceivedError(webView, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                webView.setVisibility(View.GONE);
                onHideProgress();
            }

            @Override
            public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (webResourceRequest.isForMainFrame()) {
                        webView.setVisibility(View.GONE);
                    }
                }
                onHideProgress();
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                onHideProgress();
            }

            //加载完成的时候会回调
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                onHideProgress();
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

        webViewX5.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mCurrTitle = title;
                mCurrUrl = view.getUrl();
                baseActionbarTitle.setText(mCurrTitle);
                LogUtil.d(TAG, "title: " + title + "  链接: " + view.getUrl());
                super.onReceivedTitle(view, title);
            }
        });

        webViewX5.loadUrl(mUrl);
//        webViewX5.loadUrl("http://viewer.weiyunyingxiang.com/s/TVBSLjI5OA==");
    }

    private void getIntentData() {
        //解析传递过来的文章对象
        Intent intent = getIntent();
        ArticleBean articleBean = (ArticleBean) intent.getSerializableExtra("articleBean");
        if (articleBean != null) {
            //传递过来的文章ID、标题、作者信息、Url等数据
            mArticleId = articleBean.getId();
            mTitle = articleBean.getTitle();
            mAuthor = articleBean.getAuthor();
            mUrl = articleBean.getLink();
            superChapterName = articleBean.getSuperChapterName();//一级分类
            chapterName = articleBean.getChapterName();//二级分类
            envelopePic = articleBean.getEnvelopePic();//图片
            desc = articleBean.getDesc();//描述
            tags = articleBean.getTags();//tags列表
        } else {
            //传递过来的文章ID、标题、作者信息、Url等数据
            mArticleId = intent.getIntExtra("articleId", -1);
            mTitle = intent.getStringExtra("title");
            mAuthor = intent.getStringExtra("author");
            mUrl = intent.getStringExtra("url");
        }

//        String json = GsonUtils.toJson(intent);
//        LogUtil.d(TAG, "接收数据: " + json);

        //当前网页页面标题、链接
        mCurrTitle = mTitle;
        mCurrUrl = mUrl;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.base_actionbar_left_icon, R.id.base_actionbar_left2_icon, R.id.base_actionbar_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
//                if (!mAgentWeb.back()) {
//                    finish();
//                }
                if (!webViewX5.canGoBack()) {
                    finish();
                }else {
                    webViewX5.goBack();
                }
                break;

            case R.id.base_actionbar_left2_icon:
                //直接关闭当前页面
                finish();
                break;

            case R.id.base_actionbar_right_icon:
                showMenuWindow();
                break;
        }
    }

    private void showMenuWindow() {
        if (customPopupWindow != null && customPopupWindow.isShowing()) {
            return;
        }

        View menuView = LayoutInflater.from(this).inflate(R.layout.dialog_web_menu, null);
        //测量View的高和宽
        int menuWidth = ResolutionUtils.dip2px(this, 120);
        CustomPopupWindow.measureWidthAndHeight(menuView);
        customPopupWindow = new CustomPopupWindow.Builder(this)
                .setView(R.layout.dialog_web_menu)
                .setWidthAndHeight(menuWidth, menuView.getMeasuredHeight())
                .setBackGroundLevel(1.0f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.MenuDialog)
                .setViewOnclickListener(this, 0)
                .create();
        int leftPx = ResolutionUtils.dip2px(this, -90);
        customPopupWindow.showAsDropDown(baseActionbarRightIcon, leftPx, 40);
//        customPopupWindow.showAtLocation(menuView, Gravity.TOP,0,0);
    }

    /**
     * customPopupWindow.setViewOnclickListener(this)
     */
    @Override
    public void getChildView(View view, int layoutResId, int flag) {
        TextView tvMenuShare = view.findViewById(R.id.dialog_web_menu_tv_share);
        TextView tvMenuCollect = view.findViewById(R.id.dialog_web_menu_tv_collect);
        TextView tvMenuReadLater = view.findViewById(R.id.dialog_web_menu_tv_read_later);
        TextView tvMenuOpenByBrowser = view.findViewById(R.id.dialog_web_menu_tv_browser);

        ReadLaterBean readLaterByTitle = ReadLaterBeanDaoHelpter.findReadLaterByTitle(mCurrTitle);
        if (readLaterByTitle != null) {
            if (TextUtils.equals(readLaterByTitle.getTitle(), mCurrTitle)) {
                tvMenuReadLater.setText("移除稍后阅读");
            } else {
                tvMenuReadLater.setText("稍后阅读");
            }
        } else {
            tvMenuReadLater.setText("稍后阅读");
        }

        tvMenuShare.setOnClickListener(this);
        tvMenuCollect.setOnClickListener(this);
        tvMenuReadLater.setOnClickListener(this);
        tvMenuOpenByBrowser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_web_menu_tv_share:
                IntentUtils.goToShareUrl(this, mCurrUrl);
                break;

            case R.id.dialog_web_menu_tv_collect:
                if (UserLoginUtils.getInstance().doIfNeedLogin()) {
                    //已登录
                    if (TextUtils.equals(mCurrUrl, mUrl)) {
                        //当前页面
                        if (mArticleId != -1) {
                            //收藏站内文章
                            mPresenter.goToCollectInsideArticle(mArticleId);
                        } else {
                            if (TextUtils.isEmpty(mAuthor)) {
                                //收藏网址
                                mPresenter.goToCollectLink(mTitle, mUrl);
                            } else {
                                //收藏站外文章
                                mPresenter.goToCollectOutsideArticle(mTitle, mAuthor, mUrl);
                            }
                        }
                    } else {
                        //收藏网址(HTML页面内跳转链接后的页面进行收藏)
                        mPresenter.goToCollectLink(mCurrTitle, mCurrUrl);
                    }

                } else {
                    onShowToast(getResources().getString(R.string.please_login_first));
                }
                break;

            case R.id.dialog_web_menu_tv_read_later:
                readLaterBean = new ReadLaterBean();
                readLaterBean.setTitle(mCurrTitle);
                readLaterBean.setLink(mCurrUrl);
                if (!TextUtils.isEmpty(mAuthor)) {
                    readLaterBean.setAuthor(mAuthor);
                } else {
                    readLaterBean.setAuthor("网页");
                }
                if (!TextUtils.isEmpty(envelopePic)) {
                    readLaterBean.setEnvelopePic(envelopePic);
                } else {
                    readLaterBean.setEnvelopePic("");
                }
                if (!TextUtils.isEmpty(chapterName)) {
                    readLaterBean.setChapterName(chapterName);
                } else {
                    readLaterBean.setChapterName("");
                }
                if (!TextUtils.isEmpty(superChapterName)) {
                    readLaterBean.setSuperChapterName(superChapterName);
                } else {
                    readLaterBean.setSuperChapterName("");
                }
                if (!TextUtils.isEmpty(desc)) {
                    readLaterBean.setDesc(desc);
                } else {
                    readLaterBean.setDesc("");
                }

                if (tags != null && tags.size() > 0) {
                    readLaterBean.setTagsBeanList(tags);
                } else {
                    readLaterBean.setTagsBeanList(null);
                }

                readLaterBean.setTime(System.currentTimeMillis());

                //本地化存储-加入稍后阅读
                ReadLaterBean readLaterByTitle = ReadLaterBeanDaoHelpter.findReadLaterByTitle(mCurrTitle);
                if (readLaterByTitle != null) {
                    if (TextUtils.equals(readLaterByTitle.getTitle(), mCurrTitle)) {
                        ReadLaterBeanDaoHelpter.removeReaderLaterBean(readLaterBean);
                        onShowToast("移除稍后阅读");
                        //事件总线
                        ReadLaterEvent.postUnReadLaterWithTitle(mCurrTitle);
                    } else {
                        ReadLaterBeanDaoHelpter.saveReaderLaterBean(readLaterBean);
                        onShowToast("已加入稍后阅读");
                        //事件总线
                        ReadLaterEvent.postReadLaterWithTitle(mCurrTitle);
                    }
                } else {
                    ReadLaterBeanDaoHelpter.saveReaderLaterBean(readLaterBean);
                    onShowToast("已加入稍后阅读");
                    //事件总线
                    ReadLaterEvent.postReadLaterWithTitle(mCurrTitle);
                }
                break;

            case R.id.dialog_web_menu_tv_browser:
                IntentUtils.openBrowser(this, mCurrUrl);
                break;
        }
        dismissMenuDialog();
    }

    public void dismissMenuDialog() {
        if (customPopupWindow != null) {
            customPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
//        mAgentWeb.getWebLifeCycle().onResume();
        webViewX5.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
//        mAgentWeb.getWebLifeCycle().onPause();
        webViewX5.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
//        mAgentWeb.getWebLifeCycle().onDestroy();
        try {
            webViewX5.removeAllViews();
            webViewX5.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webViewX5.stopLoading();
            webViewX5.setWebChromeClient(null);
            webViewX5.setWebViewClient(null);
            webViewX5.destroy();
        } catch (Exception ignore) {
        }
        super.onDestroy();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (handleKeyEvent(keyCode, event)){
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    public boolean handleKeyEvent(int keyCode, KeyEvent keyEvent) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (webViewX5.canGoBack()) {
            webViewX5.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void onCollectSuccess() {
        onShowToast("收藏成功~");
    }

    @Override
    public void onCollectFailed(String msg) {
        onShowToast(msg);
    }

    @Override
    public void onShowToast(String content) {
        onShowTrueToast(content);
    }

    @Override
    public void onShowProgress() {
        showProgressDialog("Loading...", false);
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onRequestError(String content) {

    }


}
