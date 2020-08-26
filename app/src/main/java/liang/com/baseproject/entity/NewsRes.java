package liang.com.baseproject.entity;

import com.liang.module_core_java.base.AdapterBean;

import java.util.List;

/**
 * 创建日期：2019/2/12 on 14:02
 * 描述: 新闻头条
 * 作者: liangyang
 */
public class NewsRes {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"uniquekey":"6c4caa0c3ba6e05e2a272892af43c00e","title":"杨幂的发际线再也回不去了么？网友吐槽像半秃","date":"2017-01-05 11:03","category":"yule","author_name":"腾讯娱乐","url":"http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg"},"..."]}
     */

    private String reason;
    private ResultBean result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * data : [{"uniquekey":"6c4caa0c3ba6e05e2a272892af43c00e","title":"杨幂的发际线再也回不去了么？网友吐槽像半秃","date":"2017-01-05 11:03","category":"yule","author_name":"腾讯娱乐","url":"http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg"},"..."]
         */

        private String stat;
        private List<DataBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean extends AdapterBean {
            /**
             * uniquekey : 6c4caa0c3ba6e05e2a272892af43c00e
             * title : 杨幂的发际线再也回不去了么？网友吐槽像半秃
             * date : 2017-01-05 11:03
             * category : yule
             * author_name : 腾讯娱乐
             * url : http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju
             * thumbnail_pic_s : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg
             * thumbnail_pic_s02 : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg
             * thumbnail_pic_s03 : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg
             */

            private String uniquekey;
            private String title;
            private String date;
            private String category;
            private String author_name;
            private String url;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;

            public String getUniquekey() {
                return uniquekey;
            }

            public void setUniquekey(String uniquekey) {
                this.uniquekey = uniquekey;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public String getThumbnail_pic_s02() {
                return thumbnail_pic_s02;
            }

            public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }
        }
    }
}
