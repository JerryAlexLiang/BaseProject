package liang.com.baseproject.View;

import java.util.List;

import liang.com.baseproject.entity.BannerBean;

public interface HomeView {

    void showToast(String content);

    void getBannerSuccess(List<BannerBean> data);

    void getBannerError(String errorMsg);
}
