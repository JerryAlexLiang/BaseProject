package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.base.BaseActivity;
import liang.com.baseproject.utils.SPUtils;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.widget.CustomRadioGroup;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLACK;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLUE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_GREEN;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_RED;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_TRANSLATE;

public class ThemeSettingActivity extends BaseActivity implements CustomRadioGroup.OnCheckedChangeListener {

    private static final String TAG = ThemeSettingActivity.class.getSimpleName();
    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_actionbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseToolbar;
    @BindView(R.id.ll_main_container)
    LinearLayout llMainContainer;
    @BindView(R.id.custom_radio_group)
    CustomRadioGroup customRadioGroup;
    @BindView(R.id.radio_blue)
    RadioButton radioBlue;
    @BindView(R.id.radio_black)
    RadioButton radioBlack;
    @BindView(R.id.radio_red)
    RadioButton radioRed;
    @BindView(R.id.radio_white)
    RadioButton radioWhite;
    @BindView(R.id.radio_green)
    RadioButton radioGreen;
    @BindView(R.id.radio_translate)
    RadioButton radioTranslate;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ThemeSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_setting);
        ButterKnife.bind(this);

//        getActionBarTheme(baseToolbar);

        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarTitle.setText("致一科技");

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.GONE);
        addActivity(this, ThemeSettingActivity.class);

        //读取设置标题栏颜色设置
        Integer themeColorFlag = SPUtils.getActionBarThemeColor(ThemeSettingActivity.this, "themeColorFlag");
        switch (themeColorFlag) {
            case ACTIONBAR_COLOR_BLUE:
                radioBlue.setChecked(true);
                break;

            case ACTIONBAR_COLOR_BLACK:
                radioBlack.setChecked(true);
                break;

            case ACTIONBAR_COLOR_RED:
                radioRed.setChecked(true);
                break;

            case ACTIONBAR_COLOR_GREEN:
                radioGreen.setChecked(true);
                break;

            case ACTIONBAR_COLOR_TRANSLATE:
                radioTranslate.setChecked(true);
                break;
        }

        customRadioGroup.setOnCheckedChangeListener(this);

    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.base_actionbar_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.base_actionbar_right_icon:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CustomRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_blue:
                baseToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_BLUE);
                ToastUtil.showShortToast(ThemeSettingActivity.this, "改变标题栏颜色为蓝色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_BLUE);
                break;

            case R.id.radio_black:
                baseToolbar.setBackgroundColor(Color.BLACK);
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_BLACK);
                ToastUtil.showShortToast(ThemeSettingActivity.this, "改变标题栏颜色为黑色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_BLACK);
                break;

            case R.id.radio_red:
                baseToolbar.setBackgroundColor(Color.RED);
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_RED);
                ToastUtil.showShortToast(ThemeSettingActivity.this, "改变标题栏颜色为红色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_RED);
                break;

            case R.id.radio_white:
                WhiteActivity.actionStart(ThemeSettingActivity.this);
//                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                //实现淡入浅出的效
//                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                //跳入效果
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                ToastUtil.showShortToast(ThemeSettingActivity.this, "跳转改变标题栏颜色为白色，字体为黑色");
                break;

            case R.id.radio_green:
                baseToolbar.setBackgroundColor(getResources().getColor(R.color.palegreen));
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_GREEN);
                ToastUtil.showShortToast(ThemeSettingActivity.this, "改变标题栏颜色为绿色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_GREEN);
                break;

            case R.id.radio_translate:
                baseToolbar.setBackgroundColor(Color.TRANSPARENT);
                llMainContainer.setBackgroundResource(R.drawable.icon_bg_header);
                baseToolbar.setBackgroundResource(R.drawable.icon_bg_header);
                setActionBarTheme(ACTIONBAR_COLOR_TRANSLATE);
                ToastUtil.showShortToast(ThemeSettingActivity.this, "改变标题栏颜色为透明色(整体图片背景)");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_TRANSLATE);
                break;

            default:
                break;
        }
    }
}

