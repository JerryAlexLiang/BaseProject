package liang.com.baseproject.home.view;

import java.util.List;

import liang.com.baseproject.base.MVPBaseView;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.login.entity.UserBean;

public interface HomeContainerView extends MVPBaseView {

    void onGetArticleListSuccess(HomeBean data);

    void onGetArticleListFail(String content);

    void onGetLocalMarkerDataSuccess(List<UserBean> localMarkerDataList);

    void onGetLocalMarkerDataFail(String content);


}
