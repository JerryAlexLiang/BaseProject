package liang.com.baseproject.login.view;

import com.liang.module_core_java.mvp.MVPBaseView;
import liang.com.baseproject.login.entity.UserBean;

public interface RegisterView extends MVPBaseView {

    void onRegisterSuccess(UserBean data);

    void onRegisterFail(String content);

}
