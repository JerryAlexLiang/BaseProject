package liang.com.baseproject.entity;

import java.io.Serializable;

/**
 * 创建日期：2019/8/21 on 14:40
 * 描述: 首页Banner
 * 作者: liangyang
 */
public class BannerBean implements Serializable {

    /**
     * desc : Android程序员简历这么写，面试邀约20+
     * id : 27
     * imagePath : https://www.wanandroid.com/blogimgs/7010eea2-16cc-4370-a3ca-9d372170f263.png
     * isVisible : 1
     * order : 1
     * title : Android程序员简历这么写，面试邀约20+
     * type : 0
     * url : https://mp.weixin.qq.com/s/KcGOuzeUem_JdZ13EOyCGA
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
