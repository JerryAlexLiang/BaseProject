package liang.com.baseproject.activity;

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
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.fragment.SettingsFragment;

/**
 * 创建日期：2019/2/21 on 17:08
 * 描述: 设置界面PreferenceFragment详解
 * 作者: liangyang
 */
public class SettingActivity extends MVPBaseActivity {

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
    @BindView(R.id.fly_setting_container)
    FrameLayout flySettingContainer;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarTitle.setVisibility(View.VISIBLE);
        baseToolbarTitle.setText("设置选项");

        //加载SettingFragment
        getFragmentManager().beginTransaction().add(R.id.fly_setting_container, new SettingsFragment()).commit();
    }

    @OnClick(R.id.base_actionbar_left_icon)
    public void onViewClicked() {
        finish();
    }
}
