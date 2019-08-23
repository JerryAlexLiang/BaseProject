package liang.com.baseproject.View;

import liang.com.baseproject.base.MVPBaseView;
import liang.com.baseproject.entity.Userbean;

public interface LoginView extends MVPBaseView {

    void onLoginSuccess(Userbean data);

    void onLoginError(String content);


}
