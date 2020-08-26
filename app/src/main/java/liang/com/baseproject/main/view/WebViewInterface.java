package liang.com.baseproject.main.view;

import com.liang.module_core_java.mvp.MVPBaseView;

public interface WebViewInterface extends MVPBaseView {

    void onCollectSuccess();

    void onCollectFailed(String msg);

}
