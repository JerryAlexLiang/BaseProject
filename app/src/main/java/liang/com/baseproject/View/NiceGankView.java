package liang.com.baseproject.View;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 创建日期：2019/2/25 on 11:18
 * 描述: 干货集中营API- 颜如玉View （另一MVP模式写法）
 * 作者: liangyang
 */
public interface NiceGankView {

    void setToastShow(String str);

    void setDataRefresh(Boolean isRefresh);

    GridLayoutManager getGridLayoutManager();

    RecyclerView getRecyclerView();

}
