package liang.com.baseproject.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.ServiceActivity;

/**
 * 创建日期：2020/6/2 on 11:08
 * 描述: Service
 * 任何一个Service在整个应用程序范围内都是通用的，
 * 即MyService不仅可以和MainActivity建立关联，
 * 还可以和任何一个Activity建立关联，
 * 而且在建立关联时它们都可以获取到相同的MyBinder实例。
 * 作者: liangyang
 */
public class MyService extends Service {

    public static final String TAG = "MyService";

    private MyBinder myBinder = new MyBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_service", "前台Service通知", NotificationManager.IMPORTANCE_DEFAULT);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "my_service")
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setSmallIcon(R.drawable.icon_small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_new_launcher))
                .setContentIntent(pendingIntent)
                .build();
        //添加权限
        //<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
        startForeground(1, notification);
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

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() executed");
        return super.onUnbind(intent);
    }

    public static class MyBinder extends Binder {

        public void startDownload() {
            Log.d(TAG, "startDownload() executed");
            //执行具体的下载任务
        }

    }
}
