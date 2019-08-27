package liang.com.baseproject.base;

import android.content.Context;

public interface MVPBaseView {

    Context getContext();

    void onShowToast(String content);

    void onShowProgress();

    void onHideProgress();
}


//public interface RequestListener<E> {
//    void onStart();
//    void onSuccess(int code, E data);
//    void onFailed(int code, String msg);
//    void onError(ExceptionHandle handle);
//    void onFinish();
//}