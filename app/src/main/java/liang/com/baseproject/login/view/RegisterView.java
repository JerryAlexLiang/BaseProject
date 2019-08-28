package liang.com.baseproject.login.view;

import liang.com.baseproject.base.MVPBaseView;
import liang.com.baseproject.login.entity.Userbean;

public interface RegisterView extends MVPBaseView {

    void onRegisterSuccess(Userbean data);

    void onRegisterFail(String content);

}
