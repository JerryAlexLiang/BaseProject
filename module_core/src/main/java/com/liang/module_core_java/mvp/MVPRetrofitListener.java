package com.liang.module_core_java.mvp;

public interface MVPRetrofitListener<T> {

    void onRequestStart();

    void onRequestSuccess(T data);

    void onRequestFail(String content);

    void onRequestError(String content);

}

