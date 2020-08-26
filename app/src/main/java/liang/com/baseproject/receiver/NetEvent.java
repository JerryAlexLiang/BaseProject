package liang.com.baseproject.receiver;

import com.liang.module_core_java.event.BaseEvent;

/**
 * 创建日期：2018/1/12 on 下午1:45
 * 描述:用于网络的事件组件间通信
 * 作者:yangliang
 */
public class NetEvent extends BaseEvent {

    public boolean isNet;
    public int networkStatus;

    public NetEvent(boolean isNet, int networkStatus) {
        this.isNet = isNet;
        this.networkStatus = networkStatus;
    }

    public boolean isNet() {
        return isNet;
    }

    public int getNetworkStatus() {
        return networkStatus;
    }
}
