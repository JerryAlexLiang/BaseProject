package liang.com.baseproject.login.listener;

import com.liang.module_core_java.mvp.MVPRetrofitListener;

public interface LoginRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onLoginSuccess(T data);

    void onLoginFail(String content);

}
