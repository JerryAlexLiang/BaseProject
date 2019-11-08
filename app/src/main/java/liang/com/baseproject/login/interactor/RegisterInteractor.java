package liang.com.baseproject.login.interactor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.base.MVPRetrofitListener;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;

public class RegisterInteractor {

    public void goToRegister(String username, String password, String repassword, MVPRetrofitListener<UserBean> listener) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
                .goToRegister(username, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<UserBean>() {
                    @Override
                    protected void onStart() {
                        listener.onRequestStart();
                    }

                    @Override
                    protected void onSuccess(UserBean data) {
                        listener.onRequestSuccess(data);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onRequestFail(errorMsg);
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        listener.onRequestError(errorMsg);
                    }

                });

    }
}
