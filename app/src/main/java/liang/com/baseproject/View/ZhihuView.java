package liang.com.baseproject.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 创建日期：2019/3/7 on 14:17
 * 描述: 知乎日报
 * 作者: liangyang
 */
public interface ZhihuView {

    void setToastShow(String content);

    void setDataRefresh(Boolean isRefresh);

    RecyclerView getRecyclerView();

    LinearLayoutManager getLinearLayoutManager();

}
