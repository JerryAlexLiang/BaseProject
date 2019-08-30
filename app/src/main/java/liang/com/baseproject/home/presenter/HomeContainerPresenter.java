package liang.com.baseproject.home.presenter;

import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.interactor.HomeContainerInteractor;
import liang.com.baseproject.home.listener.GetArticleListRetrofitListener;
import liang.com.baseproject.home.view.HomeContainerView;

public class HomeContainerPresenter extends MVPBasePresenter<HomeContainerView> implements GetArticleListRetrofitListener<HomeBean> {

    private HomeContainerInteractor homeContainerInteractor;

    public HomeContainerPresenter() {
        this.homeContainerInteractor = new HomeContainerInteractor();
    }

    /**
     * 获取首页文章列表
     */
    public void getArticleList(int page){
        homeContainerInteractor.getArticleList(page,this);
    }

    @Override
    public void onGetArticleListSuccess(HomeBean data) {
        if (isViewAttached()){
            getView().onGetArticleListSuccess(data);
        }
    }

    @Override
    public void onGetArticleListFail(String content) {
        if (isViewAttached()){
            getView().onGetArticleListFail(content);
        }
    }

    @Override
    public void onRequestStart() {
        if (isViewAttached()){
            getView().onShowProgress();
        }
    }

    @Override
    public void onRequestSuccess(HomeBean data) {
        if (isViewAttached()){
            getView().onHideProgress();
        }
    }

    @Override
    public void onRequestFail(String content) {

    }

    @Override
    public void onRequestFinish() {

    }
}
