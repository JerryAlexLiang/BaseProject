package liang.com.baseproject.View;

import java.util.List;

import liang.com.baseproject.entity.NewsRes;

public interface NewsView {

    void showProgress();

    void hideProgress();

    void setToastShow(String str);

    void getNewsSuccess(List<NewsRes.ResultBean.DataBean> data);
}
