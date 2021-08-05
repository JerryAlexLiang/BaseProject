package liang.com.baseproject.login.presenter;

import com.liang.module_core.mvp.MVPRetrofitListener;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.login.view.LoginView;
import com.liang.module_core.mvp.MVPBasePresenter;
import liang.com.baseproject.login.interactor.LoginInteractor;
import liang.com.baseproject.utils.UserLoginUtils;

public class LoginPresenter extends MVPBasePresenter<LoginView> implements MVPRetrofitListener<UserBean> {

    private final LoginInteractor loginInteractor;

    public LoginPresenter() {
        this.loginInteractor = new LoginInteractor();
    }

    /**
     * 登陆
     */
    public void goToLogin(String username, String password) {
        loginInteractor.goToLogin(username, password, this);
    }

    @Override
    public void onRequestSuccess(UserBean data) {
        //本地化存储登录信息数据
        UserLoginUtils.getInstance().login(data);
        if (isViewAttached()) {
            getView().onLoginSuccess(data);
            getView().onHideProgress();
        }
    }

    @Override
    public void onRequestFail(String content) {
        if (isViewAttached()) {
            getView().onLoginFail(content);
            getView().onHideProgress();
        }
    }

    @Override
    public void onRequestError(String content) {
        if (isViewAttached()) {
            getView().onHideProgress();
        }
    }

    @Override
    public void onRequestStart() {
        if (isViewAttached()) {
            getView().onShowProgress();
        }
    }

}
