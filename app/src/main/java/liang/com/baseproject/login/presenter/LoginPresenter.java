package liang.com.baseproject.login.presenter;

import liang.com.baseproject.base.MVPRetrofitListener;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.login.view.LoginView;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.login.interactor.LoginInteractor;
import liang.com.baseproject.utils.UserLoginUtils;

public class LoginPresenter extends MVPBasePresenter<LoginView> implements MVPRetrofitListener<UserBean> {

    private LoginInteractor loginInteractor;

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
        }
    }

    @Override
    public void onRequestFail(String content) {
        if (isViewAttached()) {
            getView().onLoginFail(content);
        }
    }

//    @Override
//    public void onError(String content) {
//        if (getView() != null) {
//            getView().onLoginError(content);
//        }
//    }

    @Override
    public void onRequestFinish() {
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
