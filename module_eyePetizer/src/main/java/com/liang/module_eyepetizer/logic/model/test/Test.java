package com.liang.module_eyepetizer.logic.model.test;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期: 2020/11/30 on 10:49 AM
 * 描述:
 * 作者: 杨亮
 */
public class Test {
    /**
     * articlePage : {"items":[{"items":[{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"秋冬换季=皮肤干燥？原因可能在你自己身上","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"}],"title":"精选"},{"items":[{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"}],"title":"健康养生"}],"linkUrl":"jk://view/home","moreText":"查看更多","type":"H5"}
     * doctorRecommend : {"items":[{"deptName":"妇科","doctors":[{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"覃莎","doctorTitle":"主治医师","hospitalName":"上海儿童医学中心","linkUrl":"jk://view/home","type":"H5"},{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"叶江枫","doctorTitle":"主治医师","hospitalName":"上海儿童医学中心","linkUrl":"jk://view/home","type":"H5"},{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"王大锤","doctorTitle":"主治医师","hospitalName":"北京儿童医学中心","linkUrl":"jk://view/home","type":"H5"}]}],"linkUrl":"jk://view/home","moreText":"查看更多","title":"专家推荐","type":"H5"}
     * inquiry : [{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","type":"H5"}]
     * multiCard : {"banner":[{"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}],"rightBottomItem":{"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"},"rightTopItem":{"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}}
     * singleCardOne : {"iconUrl":"www.baidu.com跳转路径","linkUrl":"route跳转路由","type":"H5"}
     * singleCardTwo : {"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}
     * special : {"desc":"顶级专家，守护您的健康","items":[{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"听力","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"慢病","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"医美","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"肿瘤","type":"H5"}],"title":"特色专区"}
     */

//    private ArticlePageBean articlePage;
//    private DoctorRecommendBean doctorRecommend;
    private MultiCardBean multiCard;
    private SingleCardOneBean singleCardOne;
    private SingleCardTwoBean singleCardTwo;
    private SpecialBean special;
//    private List<InquiryBean> inquiry;

//    public ArticlePageBean getArticlePage() {
//        return articlePage;
//    }
//
//    public void setArticlePage(ArticlePageBean articlePage) {
//        this.articlePage = articlePage;
//    }
//
//    public DoctorRecommendBean getDoctorRecommend() {
//        return doctorRecommend;
//    }
//
//    public void setDoctorRecommend(DoctorRecommendBean doctorRecommend) {
//        this.doctorRecommend = doctorRecommend;
//    }

    public MultiCardBean getMultiCard() {
        return multiCard;
    }

    public void setMultiCard(MultiCardBean multiCard) {
        this.multiCard = multiCard;
    }

    public SingleCardOneBean getSingleCardOne() {
        return singleCardOne;
    }

    public void setSingleCardOne(SingleCardOneBean singleCardOne) {
        this.singleCardOne = singleCardOne;
    }

    public SingleCardTwoBean getSingleCardTwo() {
        return singleCardTwo;
    }

    public void setSingleCardTwo(SingleCardTwoBean singleCardTwo) {
        this.singleCardTwo = singleCardTwo;
    }

    public SpecialBean getSpecial() {
        return special;
    }

    public void setSpecial(SpecialBean special) {
        this.special = special;
    }

//    public List<InquiryBean> getInquiry() {
//        return inquiry;
//    }
//
//    public void setInquiry(List<InquiryBean> inquiry) {
//        this.inquiry = inquiry;
//    }

    public static class ArticlePageBean extends BaseCustomViewModel implements Serializable{
        /**
         * items : [{"items":[{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"秋冬换季=皮肤干燥？原因可能在你自己身上","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"}],"title":"精选"},{"items":[{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"}],"title":"健康养生"}]
         * linkUrl : jk://view/home
         * moreText : 查看更多
         * type : H5
         */

        private String linkUrl;
        private String moreText;
        private String type;
        private List<ItemsBeanX> items;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getMoreText() {
            return moreText;
        }

        public void setMoreText(String moreText) {
            this.moreText = moreText;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ItemsBeanX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanX> items) {
            this.items = items;
        }

        public static class ItemsBeanX {
            /**
             * items : [{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"秋冬换季=皮肤干燥？原因可能在你自己身上","type":"H5","videoId":"1243535"},{"acticleType":"acticle","articleId":"14354656","authorHeadImage":"www.baidu.com","authorName":"复星健康","duration":"01:27","iconUrl":"www.baiduc.om","imageUrl":"www.baiduc,om","linkUrl":"www.baidu.com","pv":450,"title":"牙根有条小黑缝，小心这个病","type":"H5","videoId":"1243535"}]
             * title : 精选
             */

            private String title;
            private List<ItemsBean> items;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * acticleType : acticle
                 * articleId : 14354656
                 * authorHeadImage : www.baidu.com
                 * authorName : 复星健康
                 * duration : 01:27
                 * iconUrl : www.baiduc.om
                 * imageUrl : www.baiduc,om
                 * linkUrl : www.baidu.com
                 * pv : 450
                 * title : 牙根有条小黑缝，小心这个病
                 * type : H5
                 * videoId : 1243535
                 */

                private String acticleType;
                private String articleId;
                private String authorHeadImage;
                private String authorName;
                private String duration;
                private String iconUrl;
                private String imageUrl;
                private String linkUrl;
                private int pv;
                private String title;
                private String type;
                private String videoId;

                public String getActicleType() {
                    return acticleType;
                }

                public void setActicleType(String acticleType) {
                    this.acticleType = acticleType;
                }

                public String getArticleId() {
                    return articleId;
                }

                public void setArticleId(String articleId) {
                    this.articleId = articleId;
                }

                public String getAuthorHeadImage() {
                    return authorHeadImage;
                }

                public void setAuthorHeadImage(String authorHeadImage) {
                    this.authorHeadImage = authorHeadImage;
                }

                public String getAuthorName() {
                    return authorName;
                }

                public void setAuthorName(String authorName) {
                    this.authorName = authorName;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getLinkUrl() {
                    return linkUrl;
                }

                public void setLinkUrl(String linkUrl) {
                    this.linkUrl = linkUrl;
                }

                public int getPv() {
                    return pv;
                }

                public void setPv(int pv) {
                    this.pv = pv;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getVideoId() {
                    return videoId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }
            }
        }
    }

    public static class DoctorRecommendBean extends BaseCustomViewModel implements Serializable{
        /**
         * items : [{"deptName":"妇科","doctors":[{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"覃莎","doctorTitle":"主治医师","hospitalName":"上海儿童医学中心","linkUrl":"jk://view/home","type":"H5"},{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"叶江枫","doctorTitle":"主治医师","hospitalName":"上海儿童医学中心","linkUrl":"jk://view/home","type":"H5"},{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"王大锤","doctorTitle":"主治医师","hospitalName":"北京儿童医学中心","linkUrl":"jk://view/home","type":"H5"}]}]
         * linkUrl : jk://view/home
         * moreText : 查看更多
         * title : 专家推荐
         * type : H5
         */

        private String linkUrl;
        private String moreText;
        private String title;
        private String type;
        private List<ItemsBeanXX> items;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getMoreText() {
            return moreText;
        }

        public void setMoreText(String moreText) {
            this.moreText = moreText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ItemsBeanXX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanXX> items) {
            this.items = items;
        }

        public static class ItemsBeanXX {
            /**
             * deptName : 妇科
             * doctors : [{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"覃莎","doctorTitle":"主治医师","hospitalName":"上海儿童医学中心","linkUrl":"jk://view/home","type":"H5"},{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"叶江枫","doctorTitle":"主治医师","hospitalName":"上海儿童医学中心","linkUrl":"jk://view/home","type":"H5"},{"doctorHeadImg":"www.baidu.com","doctorId":"1434354","doctorName":"王大锤","doctorTitle":"主治医师","hospitalName":"北京儿童医学中心","linkUrl":"jk://view/home","type":"H5"}]
             */

            private String deptName;
            private List<DoctorsBean> doctors;

            public String getDeptName() {
                return deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public List<DoctorsBean> getDoctors() {
                return doctors;
            }

            public void setDoctors(List<DoctorsBean> doctors) {
                this.doctors = doctors;
            }

            public static class DoctorsBean {
                /**
                 * doctorHeadImg : www.baidu.com
                 * doctorId : 1434354
                 * doctorName : 覃莎
                 * doctorTitle : 主治医师
                 * hospitalName : 上海儿童医学中心
                 * linkUrl : jk://view/home
                 * type : H5
                 */

                private String doctorHeadImg;
                private String doctorId;
                private String doctorName;
                private String doctorTitle;
                private String hospitalName;
                private String linkUrl;
                private String type;

                public String getDoctorHeadImg() {
                    return doctorHeadImg;
                }

                public void setDoctorHeadImg(String doctorHeadImg) {
                    this.doctorHeadImg = doctorHeadImg;
                }

                public String getDoctorId() {
                    return doctorId;
                }

                public void setDoctorId(String doctorId) {
                    this.doctorId = doctorId;
                }

                public String getDoctorName() {
                    return doctorName;
                }

                public void setDoctorName(String doctorName) {
                    this.doctorName = doctorName;
                }

                public String getDoctorTitle() {
                    return doctorTitle;
                }

                public void setDoctorTitle(String doctorTitle) {
                    this.doctorTitle = doctorTitle;
                }

                public String getHospitalName() {
                    return hospitalName;
                }

                public void setHospitalName(String hospitalName) {
                    this.hospitalName = hospitalName;
                }

                public String getLinkUrl() {
                    return linkUrl;
                }

                public void setLinkUrl(String linkUrl) {
                    this.linkUrl = linkUrl;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }

    public static class MultiCardBean extends BaseCustomViewModel implements Serializable{
        /**
         * banner : [{"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}]
         * rightBottomItem : {"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}
         * rightTopItem : {"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}
         */

        private RightBottomItemBean rightBottomItem;
        private RightTopItemBean rightTopItem;
        private List<BannerBean> banner;

        public RightBottomItemBean getRightBottomItem() {
            return rightBottomItem;
        }

        public void setRightBottomItem(RightBottomItemBean rightBottomItem) {
            this.rightBottomItem = rightBottomItem;
        }

        public RightTopItemBean getRightTopItem() {
            return rightTopItem;
        }

        public void setRightTopItem(RightTopItemBean rightTopItem) {
            this.rightTopItem = rightTopItem;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class RightBottomItemBean {
            /**
             * iconUrl : www.baidu.com
             * linkUrl : route
             * type : H5
             */

            private String iconUrl;
            private String linkUrl;
            private String type;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class RightTopItemBean {
            /**
             * iconUrl : www.baidu.com
             * linkUrl : route
             * type : H5
             */

            private String iconUrl;
            private String linkUrl;
            private String type;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class BannerBean {
            /**
             * iconUrl : www.baidu.com
             * linkUrl : route
             * type : H5
             */

            private String iconUrl;
            private String linkUrl;
            private String type;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class SingleCardOneBean extends BaseCustomViewModel implements Serializable{
        /**
         * iconUrl : www.baidu.com跳转路径
         * linkUrl : route跳转路由
         * type : H5
         */

        private String iconUrl;
        private String linkUrl;
        private String type;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class SingleCardTwoBean extends BaseCustomViewModel implements Serializable{
        /**
         * iconUrl : www.baidu.com
         * linkUrl : route
         * type : H5
         */

        private String iconUrl;
        private String linkUrl;
        private String type;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class SpecialBean extends BaseCustomViewModel implements Serializable{
        /**
         * desc : 顶级专家，守护您的健康
         * items : [{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"听力","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"慢病","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"医美","type":"H5"},{"iconUrl":"www.baidu.com","linkUrl":"jk://view/home","title":"肿瘤","type":"H5"}]
         * title : 特色专区
         */

        private String desc;
        private String title;
        private List<ItemsBeanXXX> items;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ItemsBeanXXX> getItems() {
            return items;
        }

        public void setItems(List<ItemsBeanXXX> items) {
            this.items = items;
        }

        public static class ItemsBeanXXX {
            /**
             * iconUrl : www.baidu.com
             * linkUrl : jk://view/home
             * title : 听力
             * type : H5
             */

            private String iconUrl;
            private String linkUrl;
            private String title;
            private String type;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class InquiryBean extends BaseCustomViewModel implements Serializable {
        /**
         * iconUrl : www.baidu.com
         * linkUrl : jk://view/home
         * type : H5
         */

        private String iconUrl;
        private String linkUrl;
        private String type;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
