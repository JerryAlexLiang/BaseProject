package liang.com.baseproject.map.listener;

import java.util.List;

import liang.com.baseproject.base.MVPRetrofitListener;

public interface LocalMarkerListener<T> extends MVPRetrofitListener<T> {

    void onGetLocalMarkerDataSuccess(List<T> localMarkerDataList);

    void onGetLocalMarkerDataFail(String content);
}
