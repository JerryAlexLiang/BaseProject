package liang.com.baseproject.main.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.liang.module_core_java.mvp.MVPBaseBean;
import com.liang.module_core_java.mvp.MVPBasePresenter;

import liang.com.baseproject.main.entity.CollectOutsideArticleBean;
import liang.com.baseproject.main.entity.CollectionLinkBean;
import liang.com.baseproject.main.view.WebViewInterface;

import liang.com.baseproject.retrofit.MVPBaseObserver;
import com.liang.module_core_java.retrofit.RetrofitHelper;

import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.retrofit.UrlService;

public class AgentWebPresenter extends MVPBasePresenter<WebViewInterface> {

    private WebViewInterface webViewInterface;

    public AgentWebPresenter(WebViewInterface webViewInterface) {
        this.webViewInterface = webViewInterface;
    }

    /**
     * 收藏站内文章
     *
     * @param id 文章id，拼接在链接中
     */
    public void goToCollectInsideArticle(int id) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL);
        RetrofitHelper
                .getMyService(UrlService.class)
                .goToCollectInsideArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<MVPBaseBean>() {
                    @Override
                    protected void onStart() {
                        if (isViewAttached()) {
                            getView().onShowProgress();
                        }
                    }

                    @Override
                    protected void onSuccess(MVPBaseBean data) {
                        if (isViewAttached()) {
                            getView().onCollectSuccess();
                            getView().onHideProgress();
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onCollectFailed(errorMsg);
                            getView().onHideProgress();
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onHideProgress();
                        }
                    }

                });

    }

    /**
     * 收藏站外文章
     * 方法：POST
     * 参数：
     * title，author，link
     */
    public void goToCollectOutsideArticle(String title, String author, String link) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL);
        RetrofitHelper
                .getMyService(UrlService.class)
                .goToCollectOutsideArticle(title, author, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<CollectOutsideArticleBean>() {
                    @Override
                    protected void onStart() {
                        if (isViewAttached()) {
                            getView().onShowProgress();
                        }
                    }

                    @Override
                    protected void onSuccess(CollectOutsideArticleBean data) {
                        if (isViewAttached()) {
                            getView().onCollectSuccess();
                            getView().onHideProgress();
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onCollectFailed(errorMsg);
                            getView().onHideProgress();
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onHideProgress();
                        }
                    }

                });
    }

    /**
     * 收藏网址
     * 方法：POST
     * 参数：
     * name,link
     */
    public void goToCollectLink(String name, String link) {
        RetrofitHelper
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL);
        RetrofitHelper
                .getMyService(UrlService.class)
                .goToCollectLink(name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPBaseObserver<CollectionLinkBean>() {
                    @Override
                    protected void onStart() {
                        if (isViewAttached()) {
                            getView().onShowProgress();
                        }
                    }

                    @Override
                    protected void onSuccess(CollectionLinkBean data) {
                        if (isViewAttached()) {
                            getView().onCollectSuccess();
                            getView().onHideProgress();
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onCollectFailed(errorMsg);
                            getView().onHideProgress();
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onHideProgress();
                        }
                    }

                });
    }
}
