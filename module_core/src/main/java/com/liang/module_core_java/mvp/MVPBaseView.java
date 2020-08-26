package com.liang.module_core_java.mvp;

public interface MVPBaseView<T> {

    void onShowToast(String content);

    void onShowProgress();

    void onHideProgress();

    void onRequestError(String content);
}