package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.utils.ToastUtil;

public class AppSettingActivity extends MVPBaseActivity {

    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private AlertView logoutAlertView;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AppSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_app_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(getResources().getString(R.string.setting));

        //初始化登出提示弹框
        initLogoutAlert();
    }

    private void initLogoutAlert() {
        logoutAlertView = new AlertView.Builder()
                .setContext(AppSettingActivity.this)
                .setTitle("提示")
                .setMessage(getString(R.string.ensure_go_to_logout))
                .setDestructive("取消", "确定")
                .setOthers(null)
                .setStyle(AlertView.Style.Alert)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position){
                            case 0:
                                ToastUtil.showShortToast("取消");
                                break;

                            case 1:
                                ToastUtil.showShortToast("确定");
                                break;
                        }
                    }
                })
                .build();
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.ll_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.ll_logout:
                logoutAlertView.show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            if (logoutAlertView.isShowing()) {
                logoutAlertView.dismiss();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
