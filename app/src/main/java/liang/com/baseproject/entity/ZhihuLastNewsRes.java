package liang.com.baseproject.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2019/3/7 on 14:26
 * 描述:
 * 作者: liangyang
 */
public class ZhihuLastNewsRes implements Serializable {

    /**
     * date : 20190307
     * stories : [{"images":["https://pic4.zhimg.com/v2-469ebcf3275cf4dc565607ba5d142b5f.jpg"],"type":0,"id":9708471,"ga_prefix":"030709","title":"多国「爆发」麻疹，这个几乎绝种的病，为何会卷土重来？"},{"title":"用我们的作品为您解答：没下过笔，你也能画漫画","ga_prefix":"030707","images":["https://pic3.zhimg.com/v2-abf4636e49dc35bf011cb9e3a0e49d06.jpg"],"multipic":true,"type":0,"id":9708507},{"images":["https://pic2.zhimg.com/v2-a996402005f9f11b2aed5bd8a5b9af31.jpg"],"type":0,"id":9708574,"ga_prefix":"030706","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic2.zhimg.com/v2-be02f31edcccc5e0f8713f58fd9c0289.jpg","type":0,"id":9708571,"ga_prefix":"030618","title":"经营宁泽涛"},{"image":"https://pic4.zhimg.com/v2-c446e66e3bcf6e7646b5fc7497fba87b.jpg","type":0,"id":9708507,"ga_prefix":"030707","title":"用我们的作品为您解答：没下过笔，你也能画漫画"},{"image":"https://pic3.zhimg.com/v2-102ae00613676ed77a5c81340498aa0e.jpg","type":0,"id":9708523,"ga_prefix":"030607","title":"- 黑客为什么不搞支付宝？\r\n- 搞了，只是没有通知你"},{"image":"https://pic1.zhimg.com/v2-f5e90766ee5c751417203545c001ab14.jpg","type":0,"id":9708561,"ga_prefix":"030608","title":"他在最后的电话里聊到死亡，就像聊春游和天气一样平常"},{"image":"https://pic4.zhimg.com/v2-db3673f082f1dbc56cf05da00d209ddf.jpg","type":0,"id":9708541,"ga_prefix":"030515","title":"褚时健去世，历史、时代与命运，一切都尘埃落定了"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean implements Serializable{
        /**
         * images : ["https://pic4.zhimg.com/v2-469ebcf3275cf4dc565607ba5d142b5f.jpg"]
         * type : 0
         * id : 9708471
         * ga_prefix : 030709
         * title : 多国「爆发」麻疹，这个几乎绝种的病，为何会卷土重来？
         * multipic : true
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean implements Serializable{
        /**
         * image : https://pic2.zhimg.com/v2-be02f31edcccc5e0f8713f58fd9c0289.jpg
         * type : 0
         * id : 9708571
         * ga_prefix : 030618
         * title : 经营宁泽涛
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Override
    public String toString() {
        return "ZhihuLastNewsRes{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
