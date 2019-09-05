package liang.com.baseproject.main.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.base.MVPBaseBean;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.main.entity.CollectOutsideArticleBean;
import liang.com.baseproject.main.entity.CollectionLinkBean;
import liang.com.baseproject.main.interactor.WebViewInteractor;
import liang.com.baseproject.main.listener.CollectRetrofitListener;
import liang.com.baseproject.main.view.WebViewInterface;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;

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
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
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
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onCollectFailed(errorMsg);
                        }
                    }

                    @Override
                    protected void onFinish() {
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
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
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
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onCollectFailed(errorMsg);
                        }
                    }

                    @Override
                    protected void onFinish() {
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
                .getInstanceChangeBaseUrl(UrlConstants.WAN_ANDROID_BASE_URL)
                .getMyService()
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
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        if (isViewAttached()) {
                            getView().onCollectFailed(errorMsg);
                        }
                    }

                    @Override
                    protected void onFinish() {
                        if (isViewAttached()) {
                            getView().onHideProgress();
                        }
                    }
                });
    }
}
