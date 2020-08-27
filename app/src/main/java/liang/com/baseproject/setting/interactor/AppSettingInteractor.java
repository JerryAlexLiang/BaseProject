package liang.com.baseproject.setting.interactor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import com.liang.module_core.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.retrofit.UrlService;
import liang.com.baseproject.setting.listener.LogoutRetrofitListener;

public class AppSettingInteractor {

    /**
     * 退出登录
     */
    public void goToLogout(LogoutRetrofitListener<String> listener) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL);
        RetrofitHelper
                .getMyService(UrlService.class)
                .goToLogout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<String>() {
                    @Override
                    protected void onStart() {
                        listener.onRequestStart();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        listener.onLogoutSuccess(data);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onLogoutFail(errorMsg);
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        listener.onRequestError(errorMsg);
                    }

                });

    }

}
