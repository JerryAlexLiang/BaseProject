package liang.com.baseproject.login.interactor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.base.MVPRetrofitListener;
import liang.com.baseproject.login.entity.Userbean;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;

public class LoginInteractor {

//    public interface onRetrofitListener {
//
//        void onLoginSuccess(Userbean data);
//
//        void onLoginFail(String content);
//
//        void onLoginError(String content);
//
//        void onLoginFinish();
//    }

    //    public void goToLogin(String username, String password, onRetrofitListener listener) {
    public void goToLogin(String username, String password, MVPRetrofitListener<Userbean> listener) {
//    public void goToLogin(String username, String password, LoginRetrofitListener<Userbean> listener) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
                .goToLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<Userbean>() {
                    @Override
                    protected void onStart() {
                        listener.onRequestStart();
                    }

                    @Override
                    protected void onFinish() {
                        listener.onRequestFinish();
                    }

//                    @Override
//                    protected void onError(String errorMsg) {
//                        listener.onError(errorMsg);
//                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onRequestFail(errorMsg);
                    }

                    @Override
                    protected void onSuccess(Userbean data) {
                        listener.onRequestSuccess(data);
                    }
                });
    }
}
