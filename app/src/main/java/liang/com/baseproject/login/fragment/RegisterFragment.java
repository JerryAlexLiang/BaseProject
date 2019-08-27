package liang.com.baseproject.login.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.login.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_go_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_go_login:
                //切换到登陆Fragment
                mActivity.changeToLoginFragment();
                break;

            case R.id.btn_register:
                break;
        }
    }
}
