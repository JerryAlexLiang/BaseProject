package liang.com.baseproject.main.listener;

import com.liang.module_core.mvp.MVPRetrofitListener;

public interface CollectRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onCollectSuccess(T data);

    void onCollectFailed(String msg);

}
