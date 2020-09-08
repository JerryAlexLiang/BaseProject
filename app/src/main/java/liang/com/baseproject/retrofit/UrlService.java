package liang.com.baseproject.retrofit;

import java.util.List;

import io.reactivex.Observable;
import com.liang.module_core.mvp.MVPBaseBean;
import com.liang.module_core.mvp.MVPBaseResponse;
import liang.com.baseproject.entity.BannerBean;
import liang.com.baseproject.entity.NewsRes;
import liang.com.baseproject.entity.NiceGankRes;
import liang.com.baseproject.entity.ZhihuLastNewsRes;
import liang.com.baseproject.home.entity.ArticleHomeBannerBean;
import liang.com.baseproject.home.entity.HomeBean;
import liang.com.baseproject.login.entity.UserBean;
import liang.com.baseproject.main.entity.CollectOutsideArticleBean;
import liang.com.baseproject.main.entity.CollectionLinkBean;
import liang.com.baseproject.mine.CollectionArticleBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 创建日期：2018/12/24 on 11:13
 * 描述: 定义retrofit2请求的接口
 * 作者: liangyang
 */
public interface UrlService {

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
    @GET("data/%E7%A6%8F%E5%88%A9/6/{page}")
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

    /**
     * 首页banner
     */
    @GET("banner/json")
    Observable<MVPBaseResponse<List<BannerBean>>> getBanner();

    /**
     * 登陆 https://www.wanandroid.com/user/login
     * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证
     */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<MVPBaseResponse<UserBean>> goToLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 注册 https://www.wanandroid.com/user/register
     */
    @FormUrlEncoded
    @POST("/user/register")
    Observable<MVPBaseResponse<UserBean>> goToRegister(@Field("username") String username, @Field("password") String password,
                                                       @Field("repassword") String repassword);

    /**
     * 退出登录 https://www.wanandroid.com/user/logout/json
     * 访问了logout 后，服务端会让客户端清除 Cookie（即cookie max-Age=0），
     * 如果客户端 Cookie 实现合理，可以实现自动清理，如果本地做了用户账号密码和保存，及时清理。
     */
    @GET("/user/logout/json")
    Observable<MVPBaseResponse<String>> goToLogout();

    /**
     * 收藏文章列表  https://www.wanandroid.com/lg/collect/list/0/json
     * 方法：GET
     * 参数： 页码：拼接在链接中，从0开始。
     */
    @GET("lg/collect/list/{page}/json")
    Observable<MVPBaseResponse<CollectionArticleBean>> getCollectArticleList(@Path("page") int page);

    /**
     * 首页文章列表  https://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    Observable<MVPBaseResponse<HomeBean>> getArticleList(@Path("page") int page);

    /**
     * 首页Banner
     */
    @GET("banner/json")
    Observable<MVPBaseResponse<List<ArticleHomeBannerBean>>> getArticleHomeBanner();

    /**
     * 收藏站内文章
     * 方法：POST
     * 参数： 文章id，拼接在链接中  {"data":null,"errorCode":0,"errorMsg":""}
     */
    @POST("lg/collect/{id}/json")
    Observable<MVPBaseResponse<MVPBaseBean>> goToCollectInsideArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     * 方法：POST
     * 参数：
     * title，author，link
     */
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<MVPBaseResponse<CollectOutsideArticleBean>> goToCollectOutsideArticle(@Field("title") String title,
                                                                                     @Field("author") String author,
                                                                                     @Field("link") String link);

    /**
     * 收藏网址
     * 方法：POST
     * 参数：
     * name,link
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    Observable<MVPBaseResponse<CollectionLinkBean>> goToCollectLink(@Field("name") String name, @Field("link") String link);
}
