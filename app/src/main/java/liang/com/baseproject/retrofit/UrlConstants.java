package liang.com.baseproject.retrofit;

/**
 * 创建日期：2018/12/24 on 11:12
 * 描述: 网络地址常量类
 * 作者: liangyang
 */
public interface UrlConstants {

    public static final boolean DEBUG = true;

    public static final long HTTP_TIMEOUT = 5000;

    public static final int SUCCESS = 0;

    public static final int FAILED_NOT_LOGIN = -1001; //请先登录

    public static final String WAN_ANDROID_BASE_URL = "https://www.wanandroid.com/";

    //聚合数据新闻
    String NEWS_URL = "http://v.juhe.cn";

    //枝干-干货API
    String GANK_BASE_URL = "http://gank.io/api/";

    //知乎日报API
    String ZHIHU_BASE_URL = "http://news-at.zhihu.com/api/4/";

    //图片的前缀
    String IMG_FRONT_URL = "/iface/downloadfile?file=";
}
