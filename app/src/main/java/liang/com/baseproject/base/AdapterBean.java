package liang.com.baseproject.base;

public class AdapterBean {

    public static final int TYPE_TITLE = 1;
    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_HEADER = 3;

    protected int showType;

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
