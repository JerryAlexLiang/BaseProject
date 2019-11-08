package liang.com.baseproject.login.presenter;

import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.base.MVPRetrofitListener;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.login.interactor.RegisterInteractor;
import liang.com.baseproject.login.view.RegisterView;
import liang.com.baseproject.utils.UserLoginUtils;

public class RegisterPresenter extends MVPBasePresenter<RegisterView> implements MVPRetrofitListener<UserBean> {

    private RegisterInteractor registerInteractor;

    public RegisterPresenter() {
        this.registerInteractor = new RegisterInteractor();
    }

    /**
     * 注册
     */
    public void goToRegister(String username, String password, String repassword) {
        registerInteractor.goToRegister(username, password, repassword, this);
    }

    @Override
    public void onRequestStart() {
        if (isViewAttached()) {
            getView().onShowProgress();
        }
    }

    @Override
    public void onRequestSuccess(UserBean data) {
        //注册成功直接登陆成功
        UserLoginUtils.getInstance().login(data);
        if (isViewAttached()) {
            getView().onRegisterSuccess(data);
        }
    }

    @Override
    public void onRequestFail(String content) {
        if (isViewAttached()) {
            getView().onRegisterFail(content);
        }
    }

    @Override
    public void onRequestFinish() {
        if (isViewAttached()) {
            getView().onHideProgress();
        }
    }
}
