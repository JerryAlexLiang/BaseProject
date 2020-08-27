package liang.com.baseproject.setting.listener;

import com.liang.module_core.mvp.MVPRetrofitListener;

public interface LogoutRetrofitListener<T> extends MVPRetrofitListener<T> {

    void onLogoutSuccess(T data);

    void onLogoutFail(String content);

}
