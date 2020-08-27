package liang.com.baseproject.setting.presenter;

import com.liang.module_core.mvp.MVPBasePresenter;
import liang.com.baseproject.setting.interactor.AppSettingInteractor;
import liang.com.baseproject.setting.listener.LogoutRetrofitListener;
import liang.com.baseproject.setting.view.AppSettingView;

public class AppSettingPresenter extends MVPBasePresenter<AppSettingView> implements LogoutRetrofitListener<String> {

    private AppSettingInteractor appSettingInteractor;

    public AppSettingPresenter() {
        this.appSettingInteractor = new AppSettingInteractor();
    }

    /**
     * 退出登录
     */
    public void goToLogout() {
        appSettingInteractor.goToLogout(this);
    }

    @Override
    public void onLogoutSuccess(String data) {
        if (isViewAttached()) {
            getView().logoutSuccess();
        }
    }

    @Override
    public void onLogoutFail(String content) {
        if (isViewAttached()) {
            getView().logoutFail(content);
        }
    }

    @Override
    public void onRequestStart() {
        if (isViewAttached()) {
            getView().onShowProgress();
        }
    }

    @Override
    public void onRequestSuccess(String data) {

    }

    @Override
    public void onRequestFail(String content) {

    }

    @Override
    public void onRequestError(String content) {
        if (isViewAttached()) {
            getView().onHideProgress();
        }
    }

}
