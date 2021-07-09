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
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.liang.module_laboratory.ThreadManager;

import liang.com.baseproject.R;
import liang.com.baseproject.activity.ServiceActivity;

/**
 * 创建日期:2021/7/9 on 5:33 PM
 * 描述: Android服务之混合方式开启服务
 * Start方式可以让服务在后台运行；
 * bind方式能够调用到服务中的方法。
 * 在实际的开发工作中，有很多需求是：既要在后台能够长期运行，又要在服务中操作业务。那么就需要两种方式结合在一起，才能做到我们想要的结果。
 * 创建一个服务：模拟后台播放音乐（以控制台打印Log代替）开启APP启动并显示Notification
 * <p>
 * 创建一个Service，定义具体的操作。并创建一个中间人对象（中间帮助类），由中间对象调用Service内的方法（内部类的使用）。
 * 创建Service连接对象，当Service绑定成功后，接收中间人对象。
 * 在Activity中开启服务，获取中间人对象，通过中间人对象执行Service内的方法
 * 先以 start方式开启服务：。目的：让服务可以在后台运行。
 * 再以 bind方式开启服务。目的：能够调用到服务中的方法。
 * unbind 解绑服务。（解绑后切记把ServiceConnection对象置null，避免运行异常）
 * stopService终止服务。（用户主动关闭服务执行该操作）
 * <p>
 * 作者: 杨亮
 */
public class MusicPlayService extends Service {

    public static final String TAG = "MusicPlayService";

    //音乐播放状态（播放:true; 暂停:false）
    private boolean running = false;

    public MusicPlayService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_app_service", "前台Service通知", NotificationManager.IMPORTANCE_DEFAULT);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "my_app_service")
                .setContentTitle("致一科技")
                .setContentText("BaseProject正在后台运行...")
                .setSmallIcon(R.mipmap.icon_new_launcher)
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

    /**
     * 服务中的方法:播放音乐
     */
    public void player() {
        ThreadManager.getInstance().execute(() -> {
            running = true;
            while (true) {
                if (running) {
                    SystemClock.sleep(500);
                    Log.d(TAG, "BaseProject在后台运行中... 播放ing...");
                }
            }
        });
    }

    /**
     * 服务中的方法：继续播放
     */
    public void rePlayer() {
        Log.d(TAG, "重新播放...");
        player();
    }

    /**
     * 服务中的方法：暂停播放
     */
    public void pausePlayer() {
        running = false;
        Log.d(TAG, "暂停播放...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return new MusicBindImpl();
    }

    /**
     * 创建中间帮助类对象
     */
    public class MusicBindImpl extends Binder implements IMusicService {

        @Override
        public void callPlayer() {
            player();
        }

        @Override
        public void callRePlayer() {
            rePlayer();
        }

        @Override
        public void callPausePlayer() {
            pausePlayer();
        }
    }
}