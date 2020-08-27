package liang.com.baseproject.event;

import com.liang.module_core.event.BaseEvent;

public class ReadLaterEvent extends BaseEvent {

    private boolean readLater;
    private String title;

    public ReadLaterEvent(boolean readLater, String title) {
        this.readLater = readLater;
        this.title = title;
    }

    public boolean isReadLater() {
        return readLater;
    }

    public void setReadLater(boolean readLater) {
        this.readLater = readLater;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static void postReadLaterWithTitle(String title) {
        new ReadLaterEvent(true, title).post();
    }

    public static void postUnReadLaterWithTitle(String title) {
        new ReadLaterEvent(false, title).post();
    }
}
