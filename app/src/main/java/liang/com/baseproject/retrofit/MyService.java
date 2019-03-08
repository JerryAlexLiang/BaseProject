package liang.com.baseproject.retrofit;

import io.reactivex.Observable;
import liang.com.baseproject.entity.NewsRes;
import liang.com.baseproject.entity.NiceGankRes;
import liang.com.baseproject.entity.ZhihuLastNewsRes;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 创建日期：2018/12/24 on 11:13
 * 描述: 定义retrofit2请求的接口
 * 作者: liangyang
 */
public interface MyService {

    /**
     * 新闻头条
     * http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
     * 类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    @GET("/toutiao/index")
    Observable<NewsRes> getNews(@Query("type") String type, @Query("key") String key);

    /**
     * 干货API - 颜如玉(福利) http://gank.io/api/data/福利/10/1
     * http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/2
     */
//    @GET("/data/福利/10/{page}")
    @GET("data/%E7%A6%8F%E5%88%A9/10/{page}")
    Observable<NiceGankRes> getNiceGankData(@Path("page") int page);

    /**
     * 知乎API - 获取最新信息
     * https://news-at.zhihu.com/api/4/news/latest
     */
    @GET("news/latest")
    Observable<ZhihuLastNewsRes> getZhihuLatestData();

    /**
     * 知乎API - 请求过往数据
     */
    @GET("news/before/{time}")
    Observable<ZhihuLastNewsRes> getZhihuBeforeData(@Path("time") String time);


}
