package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.View.JuheNewsDetailWebView;
import liang.com.baseproject.base.BaseActivity;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.presenter.JuheNewsDetailPresenter;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ToastUtil;

/**
 * 创建日期：2019/2/20 on 13:31
 * 描述: 聚合新闻详情页- WebView加载Url
 * 作者: liangyang  <IGankWebView,GankWebPresenter> implements IGankWebView
 */
public class JuheNewsDetailActivity extends MVPBaseActivity<JuheNewsDetailWebView, JuheNewsDetailPresenter> implements JuheNewsDetailWebView {

    private static final String TAG = JuheNewsDetailActivity.class.getSimpleName();
    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.base_toolbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_toolbar)
    FrameLayout baseToolbar;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.url_web)
    WebView urlWeb;
    private String title;
    private String url;

    public static void actionStart(Context context, String title, String url) {
        Intent intent = new Intent(context, JuheNewsDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected JuheNewsDetailPresenter createPresenter() {
        return new JuheNewsDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_juhe_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_juhe_news_detail);
//        ButterKnife.bind(this);

        getActionBarTheme(baseToolbar);

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setImageResource(R.drawable.icon_share);
        baseToolbarTitle.setVisibility(View.VISIBLE);

        //得到Intent传递的数据
        parseIntent();
        //WebView
        mPresenter.setWebView(url);
//        mPresenter.setWebView("http:\\/\\/mini.eastday.com\\/mobile\\/190220155306754.html");

    }

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        LogUtil.d(TAG, "title:  " + title + "  url:  " + url);
        baseToolbarTitle.setText(title);
    }

    @OnClick({R.id.base_toolbar_left_icon, R.id.base_toolbar_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_toolbar_left_icon:
                finish();
                break;

            case R.id.base_toolbar_right_icon:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,url);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"分享到..."));
                break;
        }
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
    public void setToast(String content) {
        ToastUtil.setCustomToast(JuheNewsDetailActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                true, content, Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        urlWeb.destroy();
    }
}
