package liang.com.baseproject.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.View.JuheNewsDetailWebView;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.presenter.JuheNewsDetailPresenter;
import liang.com.baseproject.utils.ImageLoaderUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.SettingUtils;

/**
 * 创建日期：2019/2/20 on 13:31
 * 描述: 聚合新闻详情页- WebView加载Url
 * 作者: liangyang
 */
public class WebViewDetailActivity extends MVPBaseActivity<JuheNewsDetailWebView, JuheNewsDetailPresenter> implements JuheNewsDetailWebView {

    private static final String TAG = WebViewDetailActivity.class.getSimpleName();
    @BindView(R.id.iv_detail_top)
    ImageView ivDetailTop;
    @BindView(R.id.news_image_mask)
    View newsImageMask;
    @BindView(R.id.tv_imgSource)
    TextView tvImgSource;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.toolbar_back_layout)
    RelativeLayout toolbarBackLayout;
    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_toolbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.toolbar_right_layout)
    RelativeLayout toolbarRightLayout;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.url_web)
    WebView urlWeb;
    @BindView(R.id.iv_web_view_error)
    ImageView ivWebViewError;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;

    private String title;
    private String url;
    private String imageUrl;

    private String userIcon = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3706852963,2399513353&fm=26&gp=0.jpg";
    private boolean mDarkTheme;

    public static void actionStart(Context context, String title, String url, String imageUrl) {
        Intent intent = new Intent(context, WebViewDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("imageUrl", imageUrl);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String title, String url, String imageUrl, View view, String sharedElementName) {
        Intent intent = new Intent(context, WebViewDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("imageUrl", imageUrl);
        if (Build.VERSION.SDK_INT > 20) {
            Bundle options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, view, sharedElementName).toBundle();
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
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
    protected JuheNewsDetailPresenter createPresenter() {
        return new JuheNewsDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_juhe_news_detail;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juhe_news_detail);
        ButterKnife.bind(this);
        //随系统设置更改ToolBar状态栏颜色
        getActionBarTheme(null, toolbarLayout);
        //得到Intent传递的数据
        parseIntent();
        //初始化视图
        initView();
        //WebViewInterface
//        mPresenter.setWebView(url);
        ImageLoaderUtils.loadRadiusImage(WebViewDetailActivity.this, true, ivDetailTop,
                imageUrl, R.drawable.image_top_default, R.drawable.image_top_default, 0);
        mPresenter.setWebView("https://www.baidu.com/");
    }

    private void initView() {
        //自定义的RelativeLayout-CollapsingToolbarLayout-滑动冲突
//        customScrollContainer.setCollapsingToolbarLayout(toolbarLayout);
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        toolbarLayout.setTitle(title);
        toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
//        toolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
        //设置CollapsingToolbarLayout扩展状态标题栏颜色
        toolbarLayout.setExpandedTitleColor(Color.YELLOW);
        //设置CollapsingToolbarLayout收缩状态标题栏颜色
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("imageUrl");
        LogUtil.d(TAG, "title:  " + title + "  url:  " + url);

        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
    }

    @Override
    public ProgressBar getProgressBar() {
        return pbProgress;
    }

    @Override
    public WebView getWebView() {
        return urlWeb;
    }

    @Override
    public ImageView getErrorView() {
        return ivWebViewError;
    }

    @Override
    public void setToast(String content) {
        onShowTrueToast(content);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        urlWeb.destroy();
    }

    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, R.id.iv_detail_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                finish();
                break;

            case R.id.toolbar_right_layout:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, url);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到..."));
                break;

            case R.id.iv_detail_top:
                SinglePictureActivity.actionStart(WebViewDetailActivity.this, imageUrl, title);
                break;

            default:
                break;
        }
    }

}
