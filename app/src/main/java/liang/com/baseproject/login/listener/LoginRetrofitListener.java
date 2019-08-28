package liang.com.baseproject.login.listener;

import liang.com.baseproject.base.MVPRetrofitListener;

public interface LoginRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onLoginSuccess(T data);

    void onLoginFail(String content);

}
