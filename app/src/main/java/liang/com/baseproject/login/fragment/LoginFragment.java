package liang.com.baseproject.login.fragment;


import android.content.Context;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.login.view.LoginView;
import com.liang.module_core.mvp.MVPBaseFragment;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.presenter.LoginPresenter;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.widget.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends MVPBaseFragment<LoginView, LoginPresenter> implements LoginView {

    private static final String TAG = LoginFragment.class.getSimpleName();
    @BindView(R.id.ll_go_register)
    LinearLayout llGoRegister;
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
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;

    private LoginActivity mActivity;

    private CustomProgressDialog customProgressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LoginActivity) context;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View rootView) {
        customProgressDialog = new CustomProgressDialog(getActivity(), "Loading...", false);
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

    @OnClick({R.id.ll_go_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_go_register:
                //切换注册Fragment
                mActivity.changeToRegisterFragment();
                break;

            case R.id.btn_login:
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    onShowToast("用户名不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    onShowToast("密码不能为空!");
                    return;
                }
                mPresenter.goToLogin(userName, password);
                break;
        }
    }

    @Override
    public void onLoginSuccess(UserBean data) {
        LogUtil.e(TAG, "登陆成功~" + "  用户账号信息:  " + new Gson().toJson(data));
        onShowToast("登陆成功~");
        //订阅登录事件总线
        new LoginEvent(true).post();
        //关闭当前页面
        finishPage();
    }

    @Override
    public void onLoginFail(String content) {
        LogUtil.e(TAG, "登陆失败!  " + content);
        onShowToast("登陆失败!  " + content);
    }

//    @Override
//    public void onLoginError(String content) {
//        LogUtil.e(TAG, "登陆失败!  网络异常:  " + content);
//        onShowToast("登陆失败!  网络异常:  " + content);
//    }

    @Override
    public void onShowToast(String content) {
        onShowTrueToast(content);
    }

    @Override
    public void onShowProgress() {
        if (customProgressDialog != null && !customProgressDialog.isShow()) {
            customProgressDialog.show();
        }
    }

    @Override
    public void onHideProgress() {
        if (customProgressDialog != null && customProgressDialog.isShow()) {
            customProgressDialog.dismiss();
        }
    }

    @Override
    public void onRequestError(String content) {

    }

//    private void finishPage() {
//        if (mActivity != null) {
//            mActivity.finish();
//        }
//    }
}
