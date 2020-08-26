package liang.com.baseproject.map;

import java.util.List;

import com.liang.module_core_java.mvp.MVPBaseView;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.login.entity.UserBean;

public interface MapLocationView extends MVPBaseView {

    void onGetArticleListSuccess(HomeBean data);

    void onGetArticleListFail(String content);

    void onGetLocalMarkerDataSuccess(List<UserBean> localMarkerDataList);

    void onGetLocalMarkerDataFail(String content);
}
