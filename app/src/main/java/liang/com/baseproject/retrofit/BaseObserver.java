package liang.com.baseproject.retrofit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建日期：2018/12/24 on 11:13
 * 描述: 网络请求基类
 * 作者: liangyang
 */
public abstract class BaseObserver<T> implements Observer<T> {

    protected Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
