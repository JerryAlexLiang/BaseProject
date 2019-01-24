package liang.com.baseproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WhiteActivity extends BaseActivity {

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
    @BindView(R.id.btn_jump_translate_activity)
    Button btnJumpTranslateActivity;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WhiteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white);
        ButterKnife.bind(this);
        baseToolbar.setBackgroundColor(Color.WHITE);
        //设置状态栏黑色字体
        changeStatusBarTextColor(true);

        baseToolbarLeftTv.setVisibility(View.VISIBLE);
        baseToolbarLeftTv.setText("返回");
        baseToolbarLeftTv.setTextColor(Color.BLACK);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarTitle.setTextColor(Color.BLACK);
        baseToolbarTitle.setText("白色主题沉浸式状态栏");

        addActivity(this, WhiteActivity.class);
    }

    @OnClick({R.id.base_toolbar_left_tv, R.id.base_toolbar_right_icon, R.id.btn_jump_translate_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_toolbar_left_tv:
            case R.id.base_toolbar_right_icon:
                finish();
                break;

            case R.id.btn_jump_translate_activity:
                startActivity(TranslateActivity.class, null, false);
                break;
        }
    }

}
