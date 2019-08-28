package liang.com.baseproject.base;

public interface MVPRetrofitListener<T> {

    void onSuccess(T data);

    void onFail(String content);

//    void onError(String content);

    void onRequestFinish();

    void onRequestStart();
}

