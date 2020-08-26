package liang.com.baseproject.map;

import java.util.List;

import com.liang.module_core_java.mvp.MVPBasePresenter;
import com.liang.module_core_java.mvp.MVPRetrofitListener;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.home.interactor.HomeContainerInteractor;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.map.Interactor.LocalMarkerIntercator;
import liang.com.baseproject.map.listener.LocalMarkerListener;

public class MapLocationPresenter extends MVPBasePresenter<MapLocationView> {

    private HomeContainerInteractor homeContainerInteractor;
    private LocalMarkerIntercator localMarkerIntercator;

    public MapLocationPresenter() {
        this.homeContainerInteractor = new HomeContainerInteractor();
        this.localMarkerIntercator = new LocalMarkerIntercator();
    }

    /**
     * 获取首页文章列表
     */
    public void getArticleList(int page) {
        homeContainerInteractor.getArticleList(page, new MVPRetrofitListener<HomeBean>() {
            @Override
            public void onRequestStart() {

            }

            @Override
            public void onRequestSuccess(HomeBean data) {
                if (isViewAttached()) {
                    getView().onGetArticleListSuccess(data);
                }
            }

            @Override
            public void onRequestFail(String content) {
                if (isViewAttached()) {
                    getView().onGetArticleListFail(content);
                }
            }

            @Override
            public void onRequestError(String content) {
                if (isViewAttached()) {
                    getView().onHideProgress();
                    getView().onRequestError(content);
                }
            }
        });
    }

    /**
     * 获取本地Marker数据
     */
    public void getLocalMarkerData() {
        localMarkerIntercator.getLocalMarkerData(new LocalMarkerListener<UserBean>() {
            @Override
            public void onGetLocalMarkerDataSuccess(List<UserBean> localMarkerDataList) {
                if (isViewAttached()) {
                    getView().onGetLocalMarkerDataSuccess(localMarkerDataList);
                }
            }

            @Override
            public void onGetLocalMarkerDataFail(String content) {
                if (isViewAttached()) {
                    getView().onGetLocalMarkerDataFail(content);
                }
            }

            @Override
            public void onRequestStart() {

            }

            @Override
            public void onRequestSuccess(UserBean data) {

            }

            @Override
            public void onRequestFail(String content) {

            }

            @Override
            public void onRequestError(String content) {

            }
        });
    }

}
