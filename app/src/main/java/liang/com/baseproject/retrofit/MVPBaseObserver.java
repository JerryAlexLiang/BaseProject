package liang.com.baseproject.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.base.MVPBaseResponse;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ToastUtil;
import retrofit2.HttpException;

import static liang.com.baseproject.retrofit.UrlConstants.FAILED_NOT_LOGIN;
import static liang.com.baseproject.retrofit.UrlConstants.SUCCESS;

/**
 * 创建日期：2018/12/24 on 11:13
 * 描述: 网络请求基类
 * 作者: liangyang
 */
public abstract class MVPBaseObserver<T> implements Observer<MVPBaseResponse<T>> {

    private static final String TAG = "RxHttpUtils";
    protected Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onStart();
    }

    @Override
    public void onNext(MVPBaseResponse<T> data) {
        switch (data.getErrorCode()) {
            case SUCCESS:
                onSuccess(data.getData());
                onFinish();
                LogUtil.e(TAG, "onSuccess():  " + new Gson().toJson(data));
                break;

            case FAILED_NOT_LOGIN:
                /*
                ToastUtils.showToast(data.getMsg());
                Intent intent = new Intent(WYApplication.getAppContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                WYApplication.getAppContext().startActivity(intent);
                 */
                break;

            default:
                onFail(data.getErrorMsg());
                onFinish();
                LogUtil.e(TAG, "onFail():  " + new Gson().toJson(data));
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        onFinish();
//        onError(e.getMessage());
        LogUtil.e(TAG, "onError():  " + e.toString());

        if (e instanceof HttpException) {
            //HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            // 连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            //未知错误
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    /**
     * 请求异常
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                //连接错误
                ToastUtil.showShortToast(MyApplication.getAppContext().getResources().getString(R.string.connect_error));
                break;

            case CONNECT_TIMEOUT:
                //连接超时
                ToastUtil.showShortToast(MyApplication.getAppContext().getResources().getString(R.string.connect_timeout));
                break;

            case BAD_NETWORK:
                //网络错误
                ToastUtil.showShortToast(MyApplication.getAppContext().getResources().getString(R.string.bad_netword));
                break;

            case PARSE_ERROR:
                //解析数据失败
                ToastUtil.showShortToast(MyApplication.getAppContext().getResources().getString(R.string.parse_error));
                break;

            case UNKNOWN_ERROR:
                //未知错误
                ToastUtil.showShortToast(MyApplication.getAppContext().getResources().getString(R.string.unknown_error));
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        //解析数据失败
        PARSE_ERROR,
        //网络错误
        BAD_NETWORK,
        //连接错误
        CONNECT_ERROR,
        //连接超时
        CONNECT_TIMEOUT,
        //未知错误
        UNKNOWN_ERROR
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        LogUtil.e(TAG, "onComplete()");
    }

    /**
     * 请求结束回调
     */
    protected abstract void onStart();

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onFail(String errorMsg);

    //    /**
//     * 异常回调
//     *
//     * @param errorMsg 错误信息
//     */
//    protected abstract void onError(String errorMsg);

    /**
     * 请求结束回调
     */
    protected abstract void onFinish();
}
