package liang.com.baseproject.home.listener;

import liang.com.baseproject.base.MVPRetrofitListener;

public interface GetArticleListRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onGetArticleListSuccess(T data);

    void onGetArticleListFail(String content);

}
