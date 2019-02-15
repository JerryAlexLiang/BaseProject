package liang.com.baseproject.presenter;

import java.util.List;

import liang.com.baseproject.View.NewsView;
import liang.com.baseproject.entity.NewsRes;
import liang.com.baseproject.interactor.NewsInteractor;

public class NewsPresenter implements NewsInteractor.onRetrofitListener {

    private NewsView newsView;
    private NewsInteractor newsInteractor;

    public NewsPresenter(NewsView newsView, NewsInteractor newsInteractor) {
        this.newsView = newsView;
        this.newsInteractor = newsInteractor;
    }

    /**
     * 获取新闻数据
     */
    public void getNewsData(String serverUrl, String type, String key) {
        if (newsView != null) {
            newsView.showProgress();
        }
        newsInteractor.getNewsData(serverUrl, type, key, this);
    }

    @Override
    public void onHideProgress() {
        if (newsView != null) {
            newsView.hideProgress();
        }
    }

    @Override
    public void getNewsSuccess(List<NewsRes.ResultBean.DataBean> data) {
        if (newsView != null) {
            newsView.getNewsSuccess(data);
        }
    }

    @Override
    public void getNewsFail(String message) {
        if (newsView != null) {
            newsView.setToastShow(message);
        }
    }

    @Override
    public void getNewsError(String message) {
        if (newsView != null) {
            newsView.setToastShow(message);
        }
    }

    @Override
    public void loadMoreData() {

    }

    public void onDestroy() {
        newsView = null;
    }
}
