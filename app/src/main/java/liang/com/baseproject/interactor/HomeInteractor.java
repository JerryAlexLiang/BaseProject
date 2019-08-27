package liang.com.baseproject.interactor;

import java.util.List;

import liang.com.baseproject.entity.BannerBean;

public class HomeInteractor {

    public interface onRetrofitListener{

        void getBannerSuccess(List<BannerBean> data);

        void getBannerError(String errorMsg);

    }

    public void getBanner(onRetrofitListener listener){
//        RxHttpUtils
//                .getSInstance()
//                .createSApi(MyService.class)
//                .getBanner()
//                .compose(Transformer.switchSchedulers())
//                .subscribe(new RxObserver<List<BannerBean>>() {
//                    @Override
//                    protected void onFail(String errorMsg) {
//                        listener.getBannerError(errorMsg);
//                    }
//
//                    @Override
//                    protected void onSuccess(List<BannerBean> data) {
//                        listener.getBannerSuccess(data);
//                        LogUtil.d("BannerData","BannerData:  "+ new Gson().toJson(data));
//                    }
//                });
    }
}
