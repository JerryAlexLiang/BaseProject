package liang.com.baseproject.home.entity;

import java.util.List;

import com.liang.module_core.mvp.MVPBaseBean;

/**
 * 创建日期：2019/8/29 on 19:43
 * 描述: 首页推荐返回JSON
 * 作者: liangyang
 */
public class HomeBean extends MVPBaseBean {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"却把清梅嗅","chapterId":94,"chapterName":"事件分发","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9066,"link":"https://juejin.im/post/5d66565cf265da03e71b0672","niceDate":"19小时前","origin":"","prefix":"","projectLink":"","publishTime":1567008167000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"反思|Android 事件分发机制的设计与实现","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"逐梦々少年","chapterId":200,"chapterName":"https","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9065,"link":"https://www.jianshu.com/p/f3c0827b9be6","niceDate":"19小时前","origin":"","prefix":"","projectLink":"","publishTime":1567008110000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"从设计HTTPS开始分析HTTPS协议","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"逐梦々少年 ","chapterId":67,"chapterName":"网络基础","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9064,"link":"https://www.jianshu.com/p/b5c1e02f3113","niceDate":"19小时前","origin":"","prefix":"","projectLink":"","publishTime":1567008093000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"HTTP协议详解","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"逐梦々少年","chapterId":67,"chapterName":"网络基础","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9063,"link":"https://www.jianshu.com/p/e7884bd5329b","niceDate":"19小时前","origin":"","prefix":"","projectLink":"","publishTime":1567008070000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"Java远程网络通讯协议之TCP/IP","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"MxsQ","chapterId":484,"chapterName":"okhttp","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9062,"link":"https://www.jianshu.com/p/2fff6fe403dd","niceDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1566927026000,"superChapterId":461,"superChapterName":"常见开源库源码解析","tags":[],"title":"Okio好在哪","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"风风风筝","chapterId":173,"chapterName":"Choreographer","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9061,"link":"https://www.jianshu.com/p/dd32ec35db1d","niceDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1566927004000,"superChapterId":173,"superChapterName":"framework","tags":[],"title":"Choreographer 解析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"锐心凌志","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9060,"link":"https://www.jianshu.com/p/dcc357ac92c7","niceDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1566926979000,"superChapterId":191,"superChapterName":"热门专题","tags":[],"title":"Android性能优化之APK瘦身详解(瘦身73%)","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":" 邹阿涛涛涛涛涛涛","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9059,"link":"https://juejin.im/post/5d63cdf7f265da03ed195f68","niceDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1566926861000,"superChapterId":191,"superChapterName":"热门专题","tags":[],"title":"Android - 一种新奇的冷启动速度优化思路(Fragment极度懒加载 + Layout子线程预加载)","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Liwei_Goging","chapterId":313,"chapterName":"字节码","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9058,"link":"https://juejin.im/post/5d5fadf56fb9a06b19734262","niceDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1566926839000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"一文看懂Java字节码","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Ruffian-痞子","chapterId":490,"chapterName":"Eventbus","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9057,"link":"https://blog.csdn.net/u014702653/article/details/100087264","niceDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1566923334000,"superChapterId":461,"superChapterName":"常见开源库源码解析","tags":[],"title":"EventBus从入门到装逼，源码分析，手撸框架","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"灯不利多","chapterId":93,"chapterName":"基础知识","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9019,"link":"https://juejin.im/post/5d61514df265da03d60f0ab6","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566838885000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"探索 Android 自定义控件：基础图形","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"phoenixsky","chapterId":402,"chapterName":"跨平台应用","collect":false,"courseId":13,"desc":"还在为Provider的使用烦躁么? 还在为页面状态的封装焦虑么? FunAndroid给你解惑.为你带来Provider的最佳实践","envelopePic":"https://www.wanandroid.com/blogimgs/d1ea5881-46ab-4bb4-b87c-d47c94442ffd.png","fresh":false,"id":9015,"link":"http://www.wanandroid.com/blog/show/2660","niceDate":"2天前","origin":"","prefix":"","projectLink":"https://github.com/phoenixsky/fun_android_flutter","publishTime":1566838881000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=402"}],"title":"产品级Flutter开源项目，FunAndroid","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"请叫我大苏","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9020,"link":"https://www.jianshu.com/p/718b4927f425","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566838540000,"superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"读书笔记--Android Gradle权威指南（上）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"PandaQ","chapterId":489,"chapterName":"RxJava","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9018,"link":"https://juejin.im/post/5d63d1c26fb9a06b0a2782c9","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566838494000,"superChapterId":461,"superChapterName":"常见开源库源码解析","tags":[],"title":"聊一聊 RxJava2 中的异常及处理方式","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"GitCode8","chapterId":76,"chapterName":"项目架构","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9017,"link":"https://juejin.im/post/5d5eaaac6fb9a06afd660c4c","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566838457000,"superChapterId":191,"superChapterName":"热门专题","tags":[],"title":"为什么还要在Activity中写业务代码？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"程序亦非猿","chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9030,"link":"https://mp.weixin.qq.com/s/ifJ8mQsThTJEPpQCXudSEg","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566835200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"Paging在RecyclerView中的应用，有这一篇就够了","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9038,"link":"https://mp.weixin.qq.com/s/BIfAYbqC9EOyXy7fwU1LNg","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566835200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android 值得深入思考面试问答分享 | 5","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9047,"link":"https://mp.weixin.qq.com/s/5bkjrnWsIcBOgIW-er0eOQ","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566835200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"介绍 Linux 文件系统：这些目录都是什么鬼？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9056,"link":"https://mp.weixin.qq.com/s/Spr62awE2e-nQUGVmkLyxQ","niceDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1566835200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"仿微信小程序下拉组件","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"xiaoyang","chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<ol> <li>为什么效果比Serializable高？<\/li> <li>为了效率损失了什么？<\/li> <li>一个对象可以序列化的关键，你认为是？<\/li> <\/ol>","envelopePic":"","fresh":false,"id":9002,"link":"https://www.wanandroid.com/wenda/show/9002","niceDate":"2019-08-26","origin":"","prefix":"","projectLink":"","publishTime":1566748970000,"superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问 Parcelable 为什么效率高于 Serializable ？","type":0,"userId":2,"visible":1,"zan":25}]
     * offset : 0
     * over : false
     * pageCount : 354
     * size : 20
     * total : 7062
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleBean> datas) {
        this.datas = datas;
    }
}
