package liang.com.baseproject.mine.entity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

import liang.com.baseproject.base.MVPBaseBean;
import liang.com.baseproject.entity.TagsBean;
import liang.com.baseproject.entity.TagsBeanListConvert;

/**
 * 创建日期：2019/8/29 on 16:42
 * 描述: 稍后阅读Bean
 * 作者: liangyang
 */
@Entity
public class ReadLaterBean extends MVPBaseBean {

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
    private String desc;
    //这里用到了Convert注解，表明它们的转换类，这样就可以转换成String保存到数据库中了
    @Convert(converter = TagsBeanListConvert.class,columnType = String.class)
    private List<TagsBean> tagsBeanList;

    @Generated(hash = 1501492820)
    public ReadLaterBean(String title, String author, String link, long time,
            String chapterName, String superChapterName, String envelopePic,
            String desc, List<TagsBean> tagsBeanList) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.time = time;
        this.chapterName = chapterName;
        this.superChapterName = superChapterName;
        this.envelopePic = envelopePic;
        this.desc = desc;
        this.tagsBeanList = tagsBeanList;
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
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public List<TagsBean> getTagsBeanList() {
        return this.tagsBeanList;
    }
    public void setTagsBeanList(List<TagsBean> tagsBeanList) {
        this.tagsBeanList = tagsBeanList;
    }

}
