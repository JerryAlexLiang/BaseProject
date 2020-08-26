package liang.com.baseproject.setting.view;

import com.liang.module_core_java.mvp.MVPBaseView;

public interface AppSettingView extends MVPBaseView {

    void logoutSuccess();

    void logoutFail(String content);

}
