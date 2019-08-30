package liang.com.baseproject.mine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 创建日期：2019/8/29 on 16:42
 * 描述: 稍后阅读Bean
 * 作者: liangyang
 */
@Entity
public class ReadLaterBean {

    @Id
    private String id;//如果是本地文件，那么id为所在的地址
    private String title;
    private String author;
    private String link;
    private long time;
    private String chapterName;
    @Generated(hash = 929022022)
    public ReadLaterBean(String id, String title, String author, String link,
            long time, String chapterName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.link = link;
        this.time = time;
        this.chapterName = chapterName;
    }
    @Generated(hash = 1586004853)
    public ReadLaterBean() {
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
