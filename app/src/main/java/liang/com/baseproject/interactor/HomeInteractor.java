package liang.com.baseproject.interactor;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.google.gson.Gson;

import java.util.List;

import liang.com.baseproject.entity.BannerBean;
import liang.com.baseproject.retrofit.MyService;
import liang.com.baseproject.rxhelper.RxObserver;
import liang.com.baseproject.utils.LogUtil;

public class HomeInteractor {

    public interface onRetrofitListener{

        void getBannerSuccess(List<BannerBean> data);

        void getBannerError(String errorMsg);

    }

    public void getBanner(onRetrofitListener listener){
        RxHttpUtils
                .getSInstance()
                .createSApi(MyService.class)
                .getBanner()
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        listener.getBannerError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(List<BannerBean> data) {
                        listener.getBannerSuccess(data);
                        LogUtil.d("BannerData","BannerData:  "+ new Gson().toJson(data));
                    }
                });
    }
}
