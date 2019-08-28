package liang.com.baseproject.setting.listener;

import liang.com.baseproject.base.MVPRetrofitListener;

public interface LogoutRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onLogoutSuccess(T data);

    void onLogoutFail(String content);

}
