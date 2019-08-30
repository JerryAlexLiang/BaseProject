package liang.com.baseproject.home.view;

import liang.com.baseproject.base.MVPBaseView;
import liang.com.baseproject.home.entity.HomeBean;

public interface HomeContainerView extends MVPBaseView {

    void onGetArticleListSuccess(HomeBean data);

    void onGetArticleListFail(String content);

}
