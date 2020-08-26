package liang.com.baseproject.login.interactor;

import com.liang.module_core_java.mvp.MVPRetrofitListener;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import com.liang.module_core_java.retrofit.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.retrofit.UrlService;

public class LoginInteractor {

//    public interface onRetrofitListener {
//
//        void onLoginSuccess(UserBean data);
//
//        void onLoginFail(String content);
//
//        void onLoginError(String content);
//
//        void onLoginFinish();
//    }

    //    public void goToLogin(String username, String password, onRetrofitListener listener) {
    public void goToLogin(String username, String password, MVPRetrofitListener<UserBean> listener) {
//    public void goToLogin(String username, String password, LoginRetrofitListener<UserBean> listener) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL);
        RetrofitHelper
                .getMyService(UrlService.class)
                .goToLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<UserBean>() {
                    @Override
                    protected void onStart() {
                        listener.onRequestStart();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onRequestFail(errorMsg);
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        listener.onRequestError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(UserBean data) {
                        listener.onRequestSuccess(data);
                    }
                });
    }
}
