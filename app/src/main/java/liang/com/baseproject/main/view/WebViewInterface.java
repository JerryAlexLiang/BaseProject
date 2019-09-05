package liang.com.baseproject.main.view;

import liang.com.baseproject.base.MVPBaseView;

public interface WebViewInterface extends MVPBaseView {

    void onCollectSuccess();

    void onCollectFailed(String msg);

}
