package liang.com.baseproject.login.view;

import com.liang.module_core_java.mvp.MVPBaseView;
import liang.com.baseproject.login.entity.UserBean;

public interface LoginView extends MVPBaseView {

    void onLoginSuccess(UserBean data);

    void onLoginFail(String content);

//    void onLoginError(String content);


}
