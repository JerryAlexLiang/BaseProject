package liang.com.baseproject.presenter;

import java.util.List;

import liang.com.baseproject.View.HomeView;
import liang.com.baseproject.base.BasePresenter;
import liang.com.baseproject.entity.BannerBean;
import liang.com.baseproject.interactor.HomeInteractor;

public class HomePresenter extends BasePresenter<HomeView> implements HomeInteractor.onRetrofitListener {

    private HomeInteractor homeInteractor;

    public HomePresenter(HomeInteractor homeInteractor) {
        this.homeInteractor = homeInteractor;
    }

    public void getBanner() {
        homeInteractor.getBanner(this);
    }

    @Override
    public void getBannerSuccess(List<BannerBean> data) {
        if (getView() != null) {
            getView().getBannerSuccess(data);
        }
    }

    @Override
    public void getBannerError(String errorMsg) {
        if (getView() != null) {
            getView().getBannerError(errorMsg);
        }
    }
}
