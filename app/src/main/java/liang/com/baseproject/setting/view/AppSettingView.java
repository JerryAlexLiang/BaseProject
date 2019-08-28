package liang.com.baseproject.setting.view;

import liang.com.baseproject.base.MVPBaseView;

public interface AppSettingView extends MVPBaseView {

    void logoutSuccess();

    void logoutFail(String content);

}
