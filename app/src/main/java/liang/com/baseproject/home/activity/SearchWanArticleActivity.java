package liang.com.baseproject.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.widget.SearchEditText;

import liang.com.baseproject.map.MapLocationActivity;

public class SearchWanArticleActivity extends MVPBaseActivity {

    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.edit_search_view)
    SearchEditText editSearchView;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchWanArticleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search_wan_article;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        editSearchView.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                String s = editSearchView.getText().toString().trim();
                onShowToast("开始搜索~  " + s);
            }
        });
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarRightTv.setVisibility(View.VISIBLE);
        baseActionbarRightTv.setText("搜索");
        editSearchView.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.base_actionbar_right_tv, R.id.edit_search_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;
            case R.id.base_actionbar_right_tv:
                onShowToast("右侧");
                break;

            case R.id.edit_search_view:
                MapLocationActivity.actionStart(this);
                break;
        }
    }
}
