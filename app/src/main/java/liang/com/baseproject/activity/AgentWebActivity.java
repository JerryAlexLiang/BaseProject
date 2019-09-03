package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.View.WebViewInterface;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.presenter.AgentWebPresenter;
import liang.com.baseproject.utils.AgentWebCreator;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ResolutionUtils;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.widget.WebContainer;
import liang.com.baseproject.widget.popupwindow.CustomPopupWindow;

public class AgentWebActivity extends MVPBaseActivity<WebViewInterface, AgentWebPresenter> implements CustomPopupWindow.ViewInterface, View.OnClickListener {

    private static final String TAG = AgentWebActivity.class.getSimpleName();
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
    @BindView(R.id.web_container)
    WebContainer webContainer;

    //传递过来的文章ID、标题、作者信息、Url等数据
    private int mArticleId = -1;
    private String mTitle = "";
    private String mAuthor = "";
    private String mUrl = "";

    //当前网页页面标题、链接
    private String mCurrTitle = "";
    private String mCurrUrl = "";
    private AgentWeb mAgentWeb;

    private CustomPopupWindow customPopupWindow;

    public static void actionStart(Context context, int articleId, String title, String url) {
        Intent intent = new Intent(context, AgentWebActivity.class);
        intent.putExtra("articleId", articleId);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String title, String author, String url) {
        Intent intent = new Intent(context, AgentWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("author", author);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String title, String url) {
        Intent intent = new Intent(context, AgentWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected AgentWebPresenter createPresenter() {
        return new AgentWebPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_agent_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递过来的数据
        getIntentData();
        //初始化视图
        initView();
    }

    private void initView() {
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(mTitle);
        //设置跑马灯效果
        baseActionbarTitle.setSelected(true);
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarLeft2Icon.setVisibility(View.VISIBLE);
        baseActionbarRightIcon.setVisibility(View.VISIBLE);

        //初始化AgentWeb
        mAgentWeb = AgentWebCreator.create(this, webContainer, mUrl, new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mCurrTitle = title;
                mCurrUrl = view.getUrl();
                baseActionbarTitle.setText(title);
                LogUtil.d(TAG, "title: " + title + "  链接: " + view.getUrl());
            }
        }, new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        //传递过来的文章ID、标题、作者信息、Url等数据
        mArticleId = intent.getIntExtra("articleId", -1);
        mTitle = intent.getStringExtra("title");
        mAuthor = intent.getStringExtra("author");
        mUrl = intent.getStringExtra("url");

        //当前网页页面标题、链接
        mCurrTitle = mTitle;
        mCurrUrl = mUrl;
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.base_actionbar_left2_icon, R.id.base_actionbar_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                if (!mAgentWeb.back()) {
                    finish();
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

        tvMenuShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_web_menu_tv_share:
                ToastUtil.showShortToast("分享");
                dismissMenuDialog();
                break;
        }
    }

    public void dismissMenuDialog() {
        if (customPopupWindow != null) {
            customPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
