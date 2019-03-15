package com.yl.recyclerview.bean;

import java.util.List;

public class VideoBeanRes {

    /**
     * error : false
     * results : [{"_id":"5c6a4b2e9d212226806fd419","createdAt":"2019-02-18T06:05:34.265Z","desc":"#抖音，记录美好生活# 回首掏！😄😆","publishedAt":"2019-02-18T06:05:46.338Z","source":"web","type":"休息视频","url":"https://v.douyin.com/YDmdxx/ ","used":true,"who":"lijinshanmx"},{"_id":"5c638c0b9d2122132f83cfbf","createdAt":"2019-02-13T03:16:27.705Z","desc":"#感谢家人一直在我身后为我打气 我会更努力","publishedAt":"2019-02-13T03:16:39.534Z","source":"web","type":"休息视频","url":"https://v.douyin.com/YN4GQp/","used":true,"who":"lijinshanmx"},{"_id":"5c4578ae9d212264ce006f4b","createdAt":"2019-01-21T07:45:50.59Z","desc":"#大早上的你是想笑死我吗 ","publishedAt":"2019-01-21T00:00:00.0Z","source":"web","type":"休息视频","url":"https://v.douyin.com/NwdsVy/","used":true,"who":"lijinshanmx"},{"_id":"5c2dabf89d21226e00cb7699","createdAt":"2019-01-03T06:30:16.909Z","desc":"##冬日恋歌 期盼着下雪❄️期盼着你 ","publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"休息视频","url":"https://v.douyin.com/LYVFNT/","used":true,"who":"lijinshanmx"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5c6a4b2e9d212226806fd419
         * createdAt : 2019-02-18T06:05:34.265Z
         * desc : #抖音，记录美好生活# 回首掏！😄😆
         * publishedAt : 2019-02-18T06:05:46.338Z
         * source : web
         * type : 休息视频
         * url : https://v.douyin.com/YDmdxx/
         * used : true
         * who : lijinshanmx
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }

    @Override
    public String toString() {
        return "VideoBeanRes{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
