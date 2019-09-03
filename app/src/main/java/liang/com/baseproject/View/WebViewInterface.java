package liang.com.baseproject.View;

import liang.com.baseproject.base.MVPBaseView;

public interface WebViewInterface extends MVPBaseView {

    void onCollectSuccess();

    void onCollectFailed(String msg);

}
