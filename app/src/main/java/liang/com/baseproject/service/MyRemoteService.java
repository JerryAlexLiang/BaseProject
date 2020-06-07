package liang.com.baseproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import liang.com.baseproject.MyAIDLService;

/**
 * 创建日期：2020/6/3 on 2:42 PM
 * 描述: 远程服务示例
 * 任何一个Service在整个应用程序范围内都是通用的，
 * 即MyService不仅可以和MainActivity建立关联，
 * 还可以和任何一个Activity建立关联，
 * 而且在建立关联时它们都可以获取到相同的MyBinder实例。
 * 作者: liangyang
 */
public class MyRemoteService extends Service {

    public static final String TAG = "MyRemoteService";

    public MyRemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁
        Log.d(TAG, "onDestroy() executed");
    }

    /**
     * 在onBind()方法中将MyAIDLService.Stub的实现返回。
     * 这里为什么可以这样写呢？
     * 因为Stub其实就是Binder的子类，所以在onBind()方法中可以直接返回Stub的实现。
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return mBinder;
    }

    /**
     * 先是对MyAIDLService.Stub进行了实现，重写里了toUpperCase()和plus()这两个方法。
     * 这两个方法的作用分别是将一个字符串全部转换成大写格式，以及将两个传入的整数进行相加。
     * 然后在onBind()方法中将MyAIDLService.Stub的实现返回。
     */
    MyAIDLService.Stub mBinder = new MyAIDLService.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            if (str != null) {
                return str.toUpperCase();
            }
            return null;
        }
    };
}
