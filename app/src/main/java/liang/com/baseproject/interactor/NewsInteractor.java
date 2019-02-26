package liang.com.baseproject.interactor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.entity.NewsRes;
import liang.com.baseproject.retrofit.BaseObserver;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.utils.LogUtil;

public class NewsInteractor {

    public interface onRetrofitListener {

        void onHideProgress();

        void getNewsSuccess(List<NewsRes.ResultBean.DataBean> data);

        void getNewsFail(String message);

        void getNewsError(String message);

        void loadMoreData();

    }

    /**
     * 获取聚合数据-新闻数据
     */
    public void getNewsData(String serverUrl, String type, String key, onRetrofitListener listener) {
        RetrofitHelper
                .getJuheSingletonInstance(serverUrl)
                .getMyService()
                .getNews(type, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NewsRes>() {
                    @Override
                    public void onNext(NewsRes newsRes) {
                        super.onNext(newsRes);
                        String reason = newsRes.getReason();
                        if (reason.contains("成功的返回")) {
                            List<NewsRes.ResultBean.DataBean> data = newsRes.getResult().getData();
                            listener.getNewsSuccess(data);
                            listener.onHideProgress();
                        } else {
                            listener.getNewsFail(reason);
                            listener.onHideProgress();
                            LogUtil.d("NewsView", reason);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        listener.getNewsError(e.getMessage());
                        listener.onHideProgress();
                        LogUtil.d("NewsView", e.getMessage());
                    }
                });

    }
}
