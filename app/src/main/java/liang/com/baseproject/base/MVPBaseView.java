package liang.com.baseproject.base;

import android.content.Context;

public interface MVPBaseView {

    Context getContext();

    void onShowToast(String content);

    void onShowProgress();

    void onHideProgress();
}
