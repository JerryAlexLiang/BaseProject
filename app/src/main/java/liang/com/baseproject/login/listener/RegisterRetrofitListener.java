package liang.com.baseproject.login.listener;

import liang.com.baseproject.base.MVPRetrofitListener;

public interface RegisterRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onRegisterSuccess(T data);

    void onRegisterFail(String content);

}
