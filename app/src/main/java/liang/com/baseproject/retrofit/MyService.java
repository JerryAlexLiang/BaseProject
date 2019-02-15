package liang.com.baseproject.retrofit;

import io.reactivex.Observable;
import liang.com.baseproject.entity.NewsRes;
import retrofit2.http.GET;
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


}
