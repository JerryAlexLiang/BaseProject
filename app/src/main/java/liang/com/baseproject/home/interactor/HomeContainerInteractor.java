package liang.com.baseproject.home.interactor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.base.MVPRetrofitListener;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;

public class HomeContainerInteractor {

    public void getArticleList(int page, MVPRetrofitListener<HomeBean> listener) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<HomeBean>() {
                    @Override
                    protected void onStart() {
                        listener.onRequestStart();
                    }

                    @Override
                    protected void onSuccess(HomeBean data) {
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
