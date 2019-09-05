package liang.com.baseproject.mine.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 创建日期：2019/8/29 on 16:42
 * 描述: 稍后阅读Bean
 * 作者: liangyang
 */
@Entity
public class ReadLaterBean {

    //@Unique 添加唯一约束
    @Id
    @Unique
    private String title;
    private String author;
    private String link;
    private long time;
    private String chapterName;
    private String superChapterName;
    private String envelopePic;
    @Generated(hash = 1276097332)
    public ReadLaterBean(String title, String author, String link, long time,
            String chapterName, String superChapterName, String envelopePic) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.time = time;
        this.chapterName = chapterName;
        this.superChapterName = superChapterName;
        this.envelopePic = envelopePic;
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
    public String getSuperChapterName() {
        return this.superChapterName;
    }
    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }
    public String getEnvelopePic() {
        return this.envelopePic;
    }
    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }


}
