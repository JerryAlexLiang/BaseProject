package com.liang.model_middleware.event;

/**
 * 创建日期: 2021/7/28 on 6:12 PM
 * 描述:
 * 作者: 杨亮
 */
public class MainHomeBarClickEvent extends BaseEvent {

    private boolean isRightClick;

    public MainHomeBarClickEvent() {
    }

    public MainHomeBarClickEvent(boolean isRightClick) {
        this.isRightClick = isRightClick;
    }

    public boolean isRightClick() {
        return isRightClick;
    }

    public MainHomeBarClickEvent setRightClick(boolean rightClick) {
        isRightClick = rightClick;
        return this;
    }

    public static void postRightClick() {
        new MainHomeBarClickEvent(true);
    }
}
