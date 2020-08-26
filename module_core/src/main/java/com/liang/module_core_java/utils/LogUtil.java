package com.liang.module_core_java.utils;

import android.util.Log;

/**
 * 创建日期：2018/11/28 on 14:21
 * 描述:在LogUtil中先是定义了VERBOSE、DEBUG、INFO、WARN、ERROR、NOTHING这六个整型常量，
 * 并且它们对应的值都是递增的。然后又定义了一个LEVEL常量，可以将它的值指定为上面六个常量中的任意一个。
 * <p>
 * 使用方法：
 * 只需要修改LEVEL常量的值，就可以自由地控制日志的打印行为了。
 * 1、比如让LEVEL等于VERBOSE就可以把所有的日志都打印出来；
 * 2、让LEVEL等于WARN就可以只打印警告以上级别的日志（4，5，6）；
 * 3、让LEVEL等于NOTHING就可以把所有日志都屏蔽掉；
 * 4、综上所述：
 * （1）、在开发阶段将LEVEL指定成VERBOSE，打印所有日志信息；
 * （2）、当项目正式上线的时候将LEVEL指定成NOTHING就可以了。
 * 作者:yangliang
 */
public class LogUtil {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    //开发阶段      开启日志
    public static final int LEVEL = VERBOSE;//开发阶段LEVEL = VERBOSE；上线正式环境：LEVEL = NOTHING
    //上线正式环境  关闭日志
//    public static final int LEVEL = NOTHING;//开发阶段LEVEL = VERBOSE；上线正式环境：LEVEL = NOTHING

    /**
     * 接下来我们提供了 v()、d()、i()、w()、e()这五个自定义的日志方法，
     * 在其内部分别调用 了 Log.v()、Log.d()、Log.i()、Log.w()、Log.e()这五个方法来打印日志，
     * 只不过在这些自定义的方法中我们都加入了一个if判断，只有当LEVEL常量的值小于或等于对应日志级别值的时候，
     * 才会将日志打印出来。
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
