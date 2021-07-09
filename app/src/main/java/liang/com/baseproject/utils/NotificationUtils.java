package liang.com.baseproject.utils;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

/**
 * 创建日期: 2021/7/9 on 5:21 PM
 * 描述: 查看Notification权限
 * 作者: 杨亮
 */
public class NotificationUtils {

    public static boolean checkNotifySetting(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        return manager.areNotificationsEnabled();
    }
}
