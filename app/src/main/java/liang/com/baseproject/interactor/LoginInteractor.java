package liang.com.baseproject.interactor;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;

import liang.com.baseproject.entity.Userbean;
import liang.com.baseproject.retrofit.MyService;
import liang.com.baseproject.rxhelper.RxObserver;

public class LoginInteractor {

    public interface onRetrofitListener {

        void onLoginSuccess(Userbean data);

        void onLoginError(String content);
    }

    public void goToLogin(String username, String password, onRetrofitListener listener) {

        RxHttpUtils
                .getSInstance()
                .createSApi(MyService.class)
                .goToLogin(username,password)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver<Userbean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        listener.onLoginError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(Userbean data) {
                        listener.onLoginSuccess(data);
                    }
                });
    }
}
