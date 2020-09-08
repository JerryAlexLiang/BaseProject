package liang.com.baseproject.home.view;

import com.liang.module_core.mvp.MVPBaseView;

import java.util.List;

import liang.com.baseproject.home.entity.ArticleHomeBannerBean;
import liang.com.baseproject.home.entity.HomeBean;

public interface HomeContainerView extends MVPBaseView {

    void onGetArticleListSuccess(HomeBean data);

    void onGetArticleListFail(String content);

    void getArticleHomeBannerSuccess(List<ArticleHomeBannerBean> data);

    void getArticleHomeBannerFail(String content);

}
