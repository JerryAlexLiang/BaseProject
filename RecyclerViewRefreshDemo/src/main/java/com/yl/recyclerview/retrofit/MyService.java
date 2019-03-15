package com.yl.recyclerview.retrofit;

import com.yl.recyclerview.bean.VideoBeanRes;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 创建日期：2018/12/24 on 11:13
 * 描述: 定义retrofit2请求的接口
 * 作者: liangyang
 */
public interface MyService {

    //干货API-休息视频
    //http://gank.io/api/data/%E4%BC%91%E6%81%AF%E8%A7%86%E9%A2%91/4/1
    //http://gank.io/api/data/休息视频/4/1
    @GET("data/%E4%BC%91%E6%81%AF%E8%A7%86%E9%A2%91/4/{page}")
    Observable<VideoBeanRes> getVideoData(@Path("page") int page);
}
