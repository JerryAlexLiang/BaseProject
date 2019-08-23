package liang.com.baseproject.event;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建日期：2019/8/23 on 11:16
 * 描述: EventBus - BaseEvent
 * 作者: liangyang
 */
public class BaseEvent {

    public void post(){
        EventBus.getDefault().post(this);
    }
}
