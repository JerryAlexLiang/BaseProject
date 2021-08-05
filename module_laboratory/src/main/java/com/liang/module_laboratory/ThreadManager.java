package com.liang.module_laboratory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建日期: 2021/7/9 on 11:27 AM
 * 描述: 线程池工具类封装
 * 作者: 杨亮
 */
public class ThreadManager {

    private static ThreadManager threadManager;

    //线程池核心数量
    private final int corePoolSize;

    //最大线程池数量，表示当缓冲队列满的时候能够继续容纳的等待任务的数量
    private final int maximumPoolSize;

    //存活时间
    private final long keepAliveTime = 1;
    private final TimeUnit unit = TimeUnit.HOURS;

    //线程池执行者
    private ThreadPoolExecutor executor;

    public static final String DefaultThreadFactory = "DefaultThreadFactory";

    /**
     * 懒汉多线程单例
     */
    public static ThreadManager getInstance() {
        if (threadManager == null) {
            synchronized (ThreadManager.class) {
                if (threadManager == null) {
                    threadManager = new ThreadManager();
                }
            }
        }
        return threadManager;
    }

    private ThreadManager() {
        //给corePoolSize赋值：当前设备可用处理器核心数 * 2 + 1，这样能够让CPU的效率得到最大程度执行（有研究论证）
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        //虽然maximumPoolSize用户到，但是需要赋值，否则会报错
        maximumPoolSize = corePoolSize;

        executor = new ThreadPoolExecutor(
                //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                corePoolSize,
                //先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                maximumPoolSize,
                //表示的是maximumPoolSize当中等待任务的存活时间
                keepAliveTime,
                unit,
                //缓冲队列，用于存放等待任务，Linked的先进先出
                new LinkedBlockingQueue<Runnable>(),
                //缓冲队列，用于存放等待任务，Linked的先进先出
//                Executors.defaultThreadFactory(),
                new DefaultThreadFactory(Thread.NORM_PRIORITY, DefaultThreadFactory),
                //用来对超出maximumPoolSize的任务的处理策略
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 执行任务
     * 线程池执行者
     * 参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
     */
    public void execute(Runnable runnable) {
        if (executor == null) {
            //线程池执行者
            executor = new ThreadPoolExecutor(
                    //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                    corePoolSize,
                    //先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                    maximumPoolSize,
                    //表示的是maximumPoolSize当中等待任务的存活时间
                    keepAliveTime,
                    unit,
                    //缓冲队列，用于存放等待任务，Linked的先进先出
                    new LinkedBlockingQueue<Runnable>(),
                    //缓冲队列，用于存放等待任务，Linked的先进先出
//                    Executors.defaultThreadFactory(),
                    new DefaultThreadFactory(Thread.NORM_PRIORITY, DefaultThreadFactory),
                    //用来对超出maximumPoolSize的任务的处理策略
                    new ThreadPoolExecutor.AbortPolicy());
        }
        if (runnable != null) {
            executor.execute(runnable);
        }
    }

    /**
     * 移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable != null) {
            executor.remove(runnable);
        }
    }

    /**
     * 创建线程池的工厂，设置线程的优先级，group以及命名
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        /**
         * 线程池的计数
         */
        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        /**
         * 线程的计数
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Runnable target;
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            thread.setPriority(threadPriority);
            return thread;
        }
    }

//    /**
//     * 线程池工具类的使用
//     */
//    ThreadManager.getInstance().execute(new Runnable(){
//       @Override
//       public void run(){
//
//        }
//    });

} 
