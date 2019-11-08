package liang.com.baseproject.home.listener;

import java.util.List;

import liang.com.baseproject.base.MVPRetrofitListener;
import liang.com.baseproject.login.entity.UserBean;

public interface GetArticleListRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onGetArticleListSuccess(T data);

    void onGetArticleListFail(String content);

    void onGetLocalMarkerDataSuccess(List<UserBean> localMarkerDataList);

    void onGetLocalMarkerDataFail(String content);

}
