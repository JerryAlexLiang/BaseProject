package liang.com.baseproject.login.listener;

import com.liang.module_core_java.mvp.MVPRetrofitListener;

public interface RegisterRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onRegisterSuccess(T data);

    void onRegisterFail(String content);

}
