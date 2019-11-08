package liang.com.baseproject.login.view;

import liang.com.baseproject.base.MVPBaseView;
import liang.com.baseproject.login.entity.UserBean;

public interface LoginView extends MVPBaseView {

    void onLoginSuccess(UserBean data);

    void onLoginFail(String content);

//    void onLoginError(String content);


}
