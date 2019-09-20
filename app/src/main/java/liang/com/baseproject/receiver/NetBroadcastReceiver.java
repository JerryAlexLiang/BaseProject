package liang.com.baseproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import org.greenrobot.eventbus.EventBus;

import java.nio.file.Path;

import liang.com.baseproject.utils.NetUtil;
import retrofit2.http.POST;

import static liang.com.baseproject.Constant.Constant.NETWORK_MOBILE;
import static liang.com.baseproject.Constant.Constant.NETWORK_NONE;
import static liang.com.baseproject.Constant.Constant.NETWORK_WIFI;

/**
 * 创建日期：2018/12/26 on 13:44
 * 描述: 网络变化时系统会发出广播。所以我们监听这个广播，利用接口回调通知activity做相应的操作
 * 作者: liangyang
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //如果相等的话就说明网络状态发生了变化
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            switch (netWorkState) {
                case 1:
                    //wifi
//                    EventBus.getDefault().post(new NetEvent(true, NETWORK_WIFI));
                    new NetEvent(true, NETWORK_WIFI).post();
                    break;

                case 0:
                    //移动网络
//                    EventBus.getDefault().post(new NetEvent(true, NETWORK_MOBILE));
                    new NetEvent(true, NETWORK_MOBILE).post();
                    break;

                case -1:
                    //没有连接网络
//                    EventBus.getDefault().post(new NetEvent(false, NETWORK_NONE));
                    new NetEvent(false, NETWORK_NONE).post();
                    break;
            }
        }
    }

}
