package liang.com.baseproject.login.fragment;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.entity.Userbean;
import liang.com.baseproject.login.presenter.RegisterPresenter;
import liang.com.baseproject.login.view.RegisterView;
import liang.com.baseproject.utils.GsonUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends MVPBaseFragment<RegisterView, RegisterPresenter> implements RegisterView {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    @BindView(R.id.ll_go_login)
    LinearLayout llGoLogin;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.tv_user_name_notice)
    TextView tvUserNameNotice;
    @BindView(R.id.btn_clear_user_name)
    ImageView btnClearUserName;
    @BindView(R.id.rl_user_name_container)
    RelativeLayout rlUserNameContainer;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_password_notice)
    TextView tvPasswordNotice;
    @BindView(R.id.btn_clear_password)
    ImageView btnClearPassword;
    @BindView(R.id.btn_password_eye)
    ImageView btnPasswordEye;
    @BindView(R.id.rl_password_container)
    RelativeLayout rlPasswordContainer;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.tv_confirm_password_notice)
    TextView tvConfirmPasswordNotice;
    @BindView(R.id.btn_confirm_clear_password)
    ImageView btnConfirmClearPassword;
    @BindView(R.id.btn_confirm_password_eye)
    ImageView btnConfirmPasswordEye;
    @BindView(R.id.rl_confirm_password_container)
    RelativeLayout rlConfirmPasswordContainer;
    @BindView(R.id.btn_register)
    Button btnRegister;
    Unbinder unbinder;
    private LoginActivity mActivity;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LoginActivity) context;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View rootView) {

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

    @OnClick({R.id.ll_go_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_go_login:
                //切换到登陆Fragment
                mActivity.changeToLoginFragment();
                break;

            case R.id.btn_register:
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    onShowToast("用户名不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    onShowToast("密码不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    onShowToast("请输入确认密码!");
                    return;
                }
                mPresenter.goToRegister(userName, password, confirmPassword);
                break;
        }
    }

    @Override
    public void onRegisterSuccess(Userbean data) {
        LogUtil.e(TAG, "注册登录成功~" + "  用户账号信息:  " + GsonUtils.toJson(data));
        onShowToast("注册登录成功~");
        //订阅注册事件总线
        new LoginEvent(true).post();
        //关闭当前界面
        finishPage();
    }

    @Override
    public void onRegisterFail(String content) {
        LogUtil.e(TAG, "注册失败!  " + content);
        onShowToast("注册失败!  " + content);
    }

    @Override
    public void onShowToast(String content) {
        ToastUtil.setCustomToast(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                false, content, getResources().getColor(R.color.toast_bg), Color.WHITE, Gravity.BOTTOM, Toast.LENGTH_SHORT);
    }

    @Override
    public void onShowProgress() {
        showProgressDialog("Loading...", false);
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }
}
