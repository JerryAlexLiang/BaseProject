package com.liang.module_laboratory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.liang.module_core.utils.LogUtil;

import java.lang.ref.WeakReference;

/**
 * 创建日期:2021/7/9 on 2:30 PM
 * 描述: HandlerThread示例
 * 作者: 杨亮
 */
public class HandlerThreadActivity extends AppCompatActivity {

    private HandlerThread handlerThread;
    private Handler myHandler;
    private Handler uiHandler;
    private TextView tvContent;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HandlerThreadActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG = HandlerThreadActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        tvContent = findViewById(R.id.tvContent);

        //创建一个线程，线程标记'handler-thread'
        handlerThread = new HandlerThread("handler-thread");
        //开启一个线程
        handlerThread.start();

        uiHandler = new UIHandler(getMainLooper(), HandlerThreadActivity.this);

        //在HandlerThread线程中创建一个Handler对象
        myHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //这个方法是运行在创建Handler所在的Looper所在的线程中的，在此例子中这个方法是运行在handler-thread线程中的，可以执行耗时操作
                LogUtil.d(TAG, "消息: " + msg.what + "  当前所在线程:  " + Thread.currentThread().getName());

                //在工作线程，可以耗时操作
                try {
                    Thread.sleep(3000);
                    if (msg.what == 2) {
                        uiHandler.sendEmptyMessage(3);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //在主线程给myHandler发送消息
        myHandler.sendEmptyMessage(1);

        new Thread(() -> {
            //在子线程给myHandler发送数据
            myHandler.sendEmptyMessage(2);
        }).start();
    }

    static class UIHandler extends Handler {

        private final WeakReference<HandlerThreadActivity> mWeakReference;

        public UIHandler(@NonNull Looper looper, HandlerThreadActivity activity) {
            super(looper);
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            HandlerThreadActivity activity = mWeakReference.get();
            if (activity != null) {
                activity.tvContent.setText("消息: " + msg.what + "\n" + "当前所在线程: " + Thread.currentThread().getName());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        handlerThread.quit();
    }
}