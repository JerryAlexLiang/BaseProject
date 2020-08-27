package liang.com.baseproject.main.interactor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.liang.module_core.mvp.MVPBaseBean;
import com.liang.module_core.mvp.MVPRetrofitListener;
import liang.com.baseproject.main.entity.CollectOutsideArticleBean;
import liang.com.baseproject.main.entity.CollectionLinkBean;
import liang.com.baseproject.retrofit.MVPBaseObserver;
import com.liang.module_core.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.retrofit.UrlService;

public class WebViewInteractor {

    public interface collectRetrofitListener extends MVPRetrofitListener {

        void onCollectSuccess();

        void onCollectFailed(String msg);
    }

    /**
     * 收藏站内文章
     *
     * @param id 文章id，拼接在链接中
     */
    public void goToCollectInsideArticle(int id, collectRetrofitListener listener) {

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

                    }

                    @Override
                    protected void onSuccess(MVPBaseBean data) {
                        listener.onCollectSuccess();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onCollectFailed(errorMsg);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                });

    }

    /**
     * 收藏站外文章
     * 方法：POST
     * 参数：
     * title，author，link
     */
    public void goToCollectOutsideArticle(String title, String author, String link, collectRetrofitListener listener) {
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

                    }

                    @Override
                    protected void onSuccess(CollectOutsideArticleBean data) {
                        listener.onCollectSuccess();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onCollectFailed(errorMsg);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                });
    }

    /**
     * 收藏网址
     * 方法：POST
     * 参数：
     * name,link
     */
    public void goToCollectLink(String name, String link, collectRetrofitListener listener) {
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

                    }

                    @Override
                    protected void onSuccess(CollectionLinkBean data) {
                        listener.onCollectSuccess();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        listener.onCollectFailed(errorMsg);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                });
    }
}
