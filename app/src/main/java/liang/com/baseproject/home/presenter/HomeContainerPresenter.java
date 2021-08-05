package liang.com.baseproject.home.presenter;

import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.mvp.MVPRetrofitListener;

import java.util.List;

import liang.com.baseproject.home.entity.ArticleHomeBannerBean;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.interactor.HomeContainerInteractor;
import liang.com.baseproject.home.view.HomeContainerView;

public class HomeContainerPresenter extends MVPBasePresenter<HomeContainerView> {

    private final HomeContainerInteractor homeContainerInteractor;

    public HomeContainerPresenter() {
        this.homeContainerInteractor = new HomeContainerInteractor();
    }

    /**
     * 获取首页文章列表
     */
    public void getArticleList(int page) {
        homeContainerInteractor.getArticleList(page, new MVPRetrofitListener<HomeBean>() {
            @Override
            public void onRequestStart() {
                if (isViewAttached()) {
                    getView().onShowProgress();
                }
            }

            @Override
            public void onRequestSuccess(HomeBean data) {
                if (isViewAttached()) {
                    getView().onHideProgress();
                    getView().onGetArticleListSuccess(data);
                }
            }

            @Override
            public void onRequestFail(String content) {
                if (isViewAttached()) {
                    getView().onHideProgress();
                    getView().onGetArticleListFail(content);
                }
            }

            @Override
            public void onRequestError(String content) {
                if (isViewAttached()) {
                    getView().onHideProgress();
                    getView().onRequestError(content);
                }
            }
        });
    }

    /**
     * 获取首页Banner
     */
    public void getArticleHomeBanner() {
        homeContainerInteractor.getArticleHomeBanner(new MVPRetrofitListener<List<ArticleHomeBannerBean>>() {
            @Override
            public void onRequestStart() {

            }

            @Override
            public void onRequestSuccess(List<ArticleHomeBannerBean> data) {
                if (isViewAttached()) {
                    getView().getArticleHomeBannerSuccess(data);
                }
            }

            @Override
            public void onRequestFail(String content) {
                if (isViewAttached()) {
                    getView().getArticleHomeBannerFail(content);
                }
            }

            @Override
            public void onRequestError(String content) {
                if (isViewAttached()) {
                    getView().getArticleHomeBannerFail(content);
                }
            }
        });
    }
}
