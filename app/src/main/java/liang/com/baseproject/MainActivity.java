package liang.com.baseproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.base.BaseActivity;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLACK;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLUE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_RED;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_TRANSLATE;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
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
    @BindView(R.id.btn_change_toolbar_blue)
    Button btnChangeToolbarBlue;
    @BindView(R.id.btn_change_toolbar_black)
    Button btnChangeToolbarBlack;
    @BindView(R.id.btn_change_toolbar_white)
    Button btnChangeToolbarWhite;
    @BindView(R.id.ll_main_container)
    LinearLayout llMainContainer;
    @BindView(R.id.btn_change_toolbar_background)
    Button btnChangeToolbarBackground;
    @BindView(R.id.btn_change_toolbar_red)
    Button btnChangeToolbarRed;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getActionBarTheme(baseToolbar);

        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarTitle.setText("致一科技");

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        addActivity(this, MainActivity.class);
    }

    @OnClick({R.id.btn_change_toolbar_blue, R.id.btn_change_toolbar_black, R.id.btn_change_toolbar_red, R.id.btn_change_toolbar_white,
            R.id.btn_change_toolbar_background, R.id.base_toolbar_left_icon, R.id.base_toolbar_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_toolbar_blue:
                baseToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_BLUE);
                break;

            case R.id.btn_change_toolbar_black:
                baseToolbar.setBackgroundColor(Color.BLACK);
                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_BLACK);
                break;

            case R.id.btn_change_toolbar_red:
                baseToolbar.setBackgroundColor(Color.RED);
                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_RED);
                break;

            case R.id.btn_change_toolbar_white:
                WhiteActivity.actionStart(MainActivity.this);
//                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                //实现淡入浅出的效
//                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                //跳入效果
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                break;

            case R.id.btn_change_toolbar_background:
                baseToolbar.setBackgroundColor(Color.TRANSPARENT);
                llMainContainer.setBackgroundResource(R.drawable.login_bg);
                setActionBarTheme(ACTIONBAR_COLOR_TRANSLATE);
                break;

            case R.id.base_toolbar_left_icon:
                finish();
                break;

            case R.id.base_toolbar_right_icon:
                finish();
                break;

            default:
                break;
        }
    }

}
