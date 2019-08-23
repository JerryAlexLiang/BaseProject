package liang.com.baseproject.presenter;

import liang.com.baseproject.View.LoginView;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.entity.Userbean;
import liang.com.baseproject.interactor.LoginInteractor;

public class LoginPresenter extends MVPBasePresenter<LoginView> implements LoginInteractor.onRetrofitListener {

    private final LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter() {
        this.loginInteractor = new LoginInteractor();
        loginView = getView();
    }

    /**
     * 登陆
     */
    public void goToLogin(String username, String password) {
        if (loginView != null) {
            loginView.onShowProgress();
        }
        loginInteractor.goToLogin(username, password, this);
    }

    @Override
    public void onLoginSuccess(Userbean data) {
        if (loginView != null) {
            loginView.onHideProgress();
            loginView.onLoginSuccess(data);
        }
    }

    @Override
    public void onLoginError(String content) {
        if (loginView != null) {
            loginView.onHideProgress();
            loginView.onLoginError(content);
        }
    }
}
