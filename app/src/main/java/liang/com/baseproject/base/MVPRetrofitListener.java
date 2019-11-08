package liang.com.baseproject.base;

public interface MVPRetrofitListener<T> {

    void onRequestStart();

    void onRequestSuccess(T data);

    void onRequestFail(String content);

    void onRequestError(String content);

}

