package liang.com.baseproject.map.listener;

import java.util.List;

import com.liang.module_core_java.mvp.MVPRetrofitListener;

public interface LocalMarkerListener<T> extends MVPRetrofitListener<T> {

    void onGetLocalMarkerDataSuccess(List<T> localMarkerDataList);

    void onGetLocalMarkerDataFail(String content);
}
