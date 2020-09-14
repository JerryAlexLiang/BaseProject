package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.utils.SPUtils;
import com.liang.module_core.utils.SettingUtils;
import com.liang.module_core.widget.CustomRadioGroup;
import com.liang.module_core.widget.SearchEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLACK;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLUE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_GREEN;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_RED;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_TRANSLATE;

public class ThemeSettingActivity extends MVPBaseActivity implements CustomRadioGroup.OnCheckedChangeListener {

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
    FrameLayout baseActionBar;
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
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.edit_search_view)
    SearchEditText editSearchView;
    @BindView(R.id.iv_night_mode)
    ImageView ivNightMode;
    @BindView(R.id.card_night_mode)
    CardView cardNightMode;
    @BindView(R.id.iv_day_mode)
    ImageView ivDayMode;
    @BindView(R.id.card_day_mode)
    CardView cardDayMode;
    @BindView(R.id.ll_color_theme_style)
    LinearLayout llColorThemeStyle;

    private boolean mDarkTheme;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ThemeSettingActivity.class);
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
        return R.layout.activity_theme_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        
        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarTitle.setText("致一科技");

        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.GONE);
        addActivity(this, ThemeSettingActivity.class);

//        getActionBarTheme(baseActionBar,null);
        initDayNightMode();

        setBarColorStyle();

        customRadioGroup.setOnCheckedChangeListener(this);

    }

    private void initBarColorStyle() {
        //读取设置标题栏颜色设置
        Integer themeColorFlag = SPUtils.getActionBarThemeColor(ThemeSettingActivity.this, "themeColorFlag");
        switch (themeColorFlag) {
            case ACTIONBAR_COLOR_BLUE:
                baseActionBar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;

            case ACTIONBAR_COLOR_RED:
                baseActionBar.setBackgroundColor(getResources().getColor(R.color.red));
                break;

            case ACTIONBAR_COLOR_BLACK:
                baseActionBar.setBackgroundColor(Color.BLACK);
                break;

            case ACTIONBAR_COLOR_TRANSLATE:
                baseActionBar.setBackgroundColor(Color.TRANSPARENT);
                break;

            case ACTIONBAR_COLOR_GREEN:
                baseActionBar.setBackgroundColor(getResources().getColor(R.color.green));
                break;
        }
    }

    private void setBarColorStyle() {
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
    }

    private void initDayNightMode() {
        //主题模式
        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
        if (mDarkTheme) {
            //玄夜黑模式
            ivNightMode.setImageDrawable(getResources().getDrawable(R.drawable.collection));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivNightMode.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            }
            ivDayMode.setImageDrawable(getResources().getDrawable(R.drawable.no_collection));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivDayMode.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
            //隐藏标题栏颜色模式选项
            llColorThemeStyle.setVisibility(View.GONE);
        } else {
            //银翼白模式
            ivDayMode.setImageDrawable(getResources().getDrawable(R.drawable.collection));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivDayMode.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            }
            ivNightMode.setImageDrawable(getResources().getDrawable(R.drawable.no_collection));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivNightMode.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
            //显示标题栏颜色模式选项
            llColorThemeStyle.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.base_actionbar_left_icon, R.id.base_actionbar_right_icon, R.id.card_night_mode, R.id.card_day_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.base_actionbar_right_icon:
                finish();
                break;

            case R.id.card_night_mode:
                SettingUtils.getInstance().setDarkTheme(true);
                MyApplication.setDarkModeStatus();

                initDayNightMode();
                baseActionBar.setBackgroundColor(Color.BLACK);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Recreate所有Activity
                        MyApplication.recreate();

                        //方式1: 直接退出当前Activity - 最佳实现方案
                        MainHomeActivity.actionStart(ThemeSettingActivity.this);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

//                        //方式2: 使用recreate()方法重启Activity(会有闪屏问题)
//                        recreate();

                        //方式3: 不掉用recreate()方法，而是自己重启当前activity并为activity设置启动和退出动画即可,但是仅仅这样的话主界面MainHomeActivity没有产生效果,
                        //需要发送一个广播通知MainHomeActivity->重启
                        //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
//                        AppSettingActivity.actionStart(AppSettingActivity.this);
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        finish();
                    }
                }, 300);
                break;

            case R.id.card_day_mode:
                SettingUtils.getInstance().setDarkTheme(false);
                MyApplication.setDarkModeStatus();

                initDayNightMode();
                initBarColorStyle();

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Recreate所有Activity
                        MyApplication.recreate();
                        ThemeSettingActivity.actionStart(ThemeSettingActivity.this);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }, 300);

                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CustomRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_blue:
                baseActionBar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_BLUE);
                onShowToast("改变标题栏颜色为蓝色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_BLUE);
                break;

            case R.id.radio_black:
                baseActionBar.setBackgroundColor(Color.BLACK);
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_BLACK);
                onShowToast("改变标题栏颜色为黑色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_BLACK);
                break;

            case R.id.radio_red:
                baseActionBar.setBackgroundColor(getResources().getColor(R.color.red));
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_RED);
                onShowToast("改变标题栏颜色为红色");
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
                onShowToast("跳转改变标题栏颜色为白色，字体为黑色");
                break;

            case R.id.radio_green:
                baseActionBar.setBackgroundColor(getResources().getColor(R.color.green));
//                llMainContainer.setBackgroundColor(Color.WHITE);
                setActionBarTheme(ACTIONBAR_COLOR_GREEN);
                onShowToast("改变标题栏颜色为绿色");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_GREEN);
                break;

            case R.id.radio_translate:
                baseActionBar.setBackgroundColor(Color.TRANSPARENT);
                llMainContainer.setBackgroundResource(R.drawable.icon_bg_header);
                baseActionBar.setBackgroundResource(R.drawable.icon_bg_header);
                setActionBarTheme(ACTIONBAR_COLOR_TRANSLATE);
                onShowToast("改变标题栏颜色为透明色(整体图片背景)");
                //本地存储设置
                SPUtils.saveActionBarThemeColor(ThemeSettingActivity.this, ACTIONBAR_COLOR_TRANSLATE);
                break;

            default:
                break;
        }
    }

}

