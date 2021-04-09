package liang.com.baseproject.entity;

import java.io.Serializable;

/**
 * 创建日期: 2021/4/8 on 5:07 PM
 * 描述:
 * 作者: 杨亮
 */
public class RefreshHeaderBean implements Serializable {

    private int id;
    private int iconId;
    private String name;
    private String animationFilename;

    public RefreshHeaderBean() {
    }

    public RefreshHeaderBean(int id, int iconId, String name) {
        this.id = id;
        this.iconId = iconId;
        this.name = name;
    }

    public RefreshHeaderBean(int id, int iconId, String name, String animationFilename) {
        this.id = id;
        this.iconId = iconId;
        this.name = name;
        this.animationFilename = animationFilename;
    }

    public int getId() {
        return id;
    }

    public RefreshHeaderBean setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RefreshHeaderBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getIconId() {
        return iconId;
    }

    public RefreshHeaderBean setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }

    public String getAnimationFilename() {
        return animationFilename;
    }

    public RefreshHeaderBean setAnimationFilename(String animationFilename) {
        this.animationFilename = animationFilename;
        return this;
    }
}
