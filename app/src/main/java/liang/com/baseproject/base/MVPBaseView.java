package liang.com.baseproject.base;

public interface MVPBaseView<T> {

    void onShowToast(String content);

    void onShowProgress();

    void onHideProgress();

    void onRequestError();
}