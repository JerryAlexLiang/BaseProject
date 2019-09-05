package liang.com.baseproject.main.listener;

import liang.com.baseproject.base.MVPRetrofitListener;

public interface CollectRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onCollectSuccess(T data);

    void onCollectFailed(String msg);

}
