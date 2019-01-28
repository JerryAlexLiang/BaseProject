package liang.com.baseproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.utils.SPUtils;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_THEME;

public class TranslateActivity extends BaseActivity {

    @BindView(R.id.btn_log_out)
    Button btnLogOut;
    @BindView(R.id.base_toolbar_ll)
    LinearLayout baseToolbarLl;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ButterKnife.bind(this);
        getActionBarTheme(baseToolbar);
        addActivity(this, TranslateActivity.class);


    }

    @OnClick(R.id.btn_log_out)
    public void onViewClicked() {
        finishAll();
        finish();
    }
}
