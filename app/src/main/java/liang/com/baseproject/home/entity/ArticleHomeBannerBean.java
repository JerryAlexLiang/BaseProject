package liang.com.baseproject.home.entity;

import com.liang.module_core.mvp.MVPBaseBean;

/**
 * 创建日期: 2020/9/8 on 2:15 PM
 * 描述: 首页Banner
 * 作者: 杨亮
 */
public class ArticleHomeBannerBean extends MVPBaseBean {

    /**
     * desc : 享学~
     * id : 29
     * imagePath : https://www.wanandroid.com/blogimgs/4036e152-22df-476b-bc28-c8c13488af91.jpeg
     * isVisible : 1
     * order : 0
     * title : 超高质量Flutter+Kotlin笔记！技术与实战篇！
     * type : 0
     * url : https://mp.weixin.qq.com/s/J12nwUlH4EuM-lYBTSHydg
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
