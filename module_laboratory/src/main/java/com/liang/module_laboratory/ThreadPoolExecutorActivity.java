package com.liang.module_laboratory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liang.module_core.utils.LogUtil;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorActivity extends AppCompatActivity {

    private static final String TAG = ThreadPoolExecutorActivity.class.getSimpleName();
    private TextView tvLog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ThreadPoolExecutorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);
        Button btnStart = findViewById(R.id.btnStartThreadTask);
        Button btnStart2 = findViewById(R.id.btnStartThreadTask2);
        Button btnStart3 = findViewById(R.id.btnStartThreadTask3);
        Button btnStart4 = findViewById(R.id.btnStartThreadTask4);
        Button btnStart5 = findViewById(R.id.btnStartThreadTask5);
        tvLog = findViewById(R.id.tvLog);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        //指定线程数量为4的定时任务线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        btnStart.setOnClickListener(v -> {
            tvLog.setText("");
            for (int i = 0; i < 10; i++) {
                MyRunnable myRunnable = new MyRunnable(ThreadPoolExecutorActivity.this, i);
                //创建了一个固定数量为3的线程池FixedThreadPool，因此虽然向线程池提交了10个任务，但是这10个任务只会被3个线程分配执行
                fixedThreadPool.execute(myRunnable);

            }
        });

        btnStart2.setOnClickListener(v -> {
            tvLog.setText("");
            for (int i = 0; i < 10; i++) {
                MyRunnable myRunnable = new MyRunnable(ThreadPoolExecutorActivity.this, i);
                //1、缓存线程池CachedThreadPool会创建新的线程来执行任务
                //2、如果在提交任务之前休眠1秒，打印日志结果会与SingleThreadPool一样，因为提交的任务只需要500毫秒即可执行完毕，休眠1秒导致在新的任务提交之前，
                //线程'pool-9-thread-1'已经处于空闲状态，可以被复用执行任务
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cachedThreadPool.execute(myRunnable);
            }
        });

        btnStart3.setOnClickListener(v -> {
            tvLog.setText("");
            //创建了一个线程数量为 2 的定时任务线程池，通过 scheduleAtFixedRate 方法，
            //指定每隔 500 毫秒执行一次任务，并且在 5 秒钟之后通过 shutdown 方法关闭定时任务
            scheduledThreadPool.scheduleAtFixedRate(() -> {
                Date date = new Date();
                String s = "线程: " + Thread.currentThread().getName() + "  报时: " + date;
                LogUtil.d(TAG, s);
                tvLog.append(s + "\n");
            }, 500, 500, TimeUnit.MILLISECONDS);

            try {
                Thread.sleep(5000);
                //使用shutdown()关闭定时任务
                scheduledThreadPool.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        btnStart4.setOnClickListener(v -> {
            tvLog.setText("");
            for (int i = 0; i < 5; i++) {
                MyRunnable myRunnable = new MyRunnable(ThreadPoolExecutorActivity.this, i);
                //向线程池中提交任务
                singleThreadExecutor.execute(myRunnable);
                //可以看出所有的task始终是在同一个线程中被执行的
            }
        });

        btnStart5.setOnClickListener(v -> {
            tvLog.setText("");
            for (int i = 0; i < 10; i++) {
                MyRunnable myRunnable = new MyRunnable(ThreadPoolExecutorActivity.this, i);
                //线程池工具类的使用
                ThreadManager.getInstance().execute(myRunnable);
            }
        });
    }

    /**
     * 模拟耗时任务
     */
    static class MyRunnable implements Runnable {

        private final WeakReference<ThreadPoolExecutorActivity> mWeakReference;

        int threadNum;

        public MyRunnable(ThreadPoolExecutorActivity activity, int threadNum) {
            this.mWeakReference = new WeakReference<>(activity);
            this.threadNum = threadNum;
        }

        @Override
        public void run() {
            try {
                String s = "线程: " + Thread.currentThread().getName() + "  正在执行的task : " + threadNum;
                LogUtil.d(TAG, s);
                ThreadPoolExecutorActivity activity = mWeakReference.get();
                if (activity != null) {
                    activity.runOnUiThread(() -> activity.tvLog.append(s + "\n"));
                }

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}