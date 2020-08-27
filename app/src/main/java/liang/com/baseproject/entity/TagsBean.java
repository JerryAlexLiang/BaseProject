package liang.com.baseproject.entity;

import java.io.Serializable;

import com.liang.module_core.mvp.MVPBaseBean;
/**
 * 创建日期：2019/9/9 on 16:09
 * 描述: 玩Android - API - 返回数据 tags 数据
 * 作者: liangyang
 */
public class TagsBean extends MVPBaseBean implements Serializable {

    /**
     * name : 公众号
     * url : /wxarticle/list/410/1
     */
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
