package liang.com.baseproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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
        baseToolbar.setBackgroundColor(Color.RED);
        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarTitle.setText("致一科技");

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        addActivity(this, MainActivity.class);
    }

    private long waitTime = 2000;
    private long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
                System.exit(0);
            }
            return true;
        } else if (KeyEvent.KEYCODE_HOME == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.btn_change_toolbar_blue, R.id.btn_change_toolbar_black, R.id.btn_change_toolbar_red, R.id.btn_change_toolbar_white,
            R.id.btn_change_toolbar_background})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_toolbar_blue:
                baseToolbar.setBackgroundColor(Color.BLUE);
                llMainContainer.setBackgroundColor(Color.WHITE);
                break;

            case R.id.btn_change_toolbar_black:
                baseToolbar.setBackgroundColor(Color.BLACK);
                llMainContainer.setBackgroundColor(Color.WHITE);
                break;

            case R.id.btn_change_toolbar_red:
                baseToolbar.setBackgroundColor(Color.RED);
                llMainContainer.setBackgroundColor(Color.WHITE);
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
                break;

            default:
                break;
        }
    }

}
