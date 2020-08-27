package liang.com.baseproject.home.interactor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.liang.module_core.mvp.MVPRetrofitListener;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import com.liang.module_core.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.retrofit.UrlService;

public class HomeContainerInteractor {

    public void getArticleList(int page, MVPRetrofitListener<HomeBean> listener) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL);
        RetrofitHelper
                .getMyService(UrlService.class)
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
