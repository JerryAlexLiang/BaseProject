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
     * multiCard : {"sort":1,"banner":[{"iconUrl":"http://img.kaiyanapp.com/11eb1c75f892c9fea38e3ebd8ca03932.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"},{"iconUrl":"http://img.kaiyanapp.com/96ba32e48697d4eeb1423c52c28f2c03.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"},{"iconUrl":"http://img.kaiyanapp.com/a76500238a061b314669021fe9e2ece0.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"}],"rightBottomItem":{"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"},"rightTopItem":{"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}}
     * singleCardOne : {"sort":3,"iconUrl":"http://img.kaiyanapp.com/5232f4b636b9a8840d0046aa8d143be2.png?imageMogr2/quality/60/format/jpg","linkUrl":"route跳转路由","type":"H5"}
     * singleCardTwo : {"sort":2,"iconUrl":"http://img.kaiyanapp.com/9c3d53ae1af82132c3368ab2d652310d.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"}
     */

    private MultiCardBean multiCard;
    private SingleCardOneBean singleCardOne;
    private SingleCardTwoBean singleCardTwo;

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

    public static class MultiCardBean extends BaseCustomViewModel implements Serializable {
        /**
         * sort : 1
         * banner : [{"iconUrl":"http://img.kaiyanapp.com/11eb1c75f892c9fea38e3ebd8ca03932.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"},{"iconUrl":"http://img.kaiyanapp.com/96ba32e48697d4eeb1423c52c28f2c03.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"},{"iconUrl":"http://img.kaiyanapp.com/a76500238a061b314669021fe9e2ece0.jpeg?imageMogr2/quality/60/format/jpg","linkUrl":"route","type":"H5"}]
         * rightBottomItem : {"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}
         * rightTopItem : {"iconUrl":"www.baidu.com","linkUrl":"route","type":"H5"}
         */

        private int sort;
        private RightBottomItemBean rightBottomItem;
        private RightTopItemBean rightTopItem;
        private List<BannerBean> banner;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

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
             * iconUrl : http://img.kaiyanapp.com/11eb1c75f892c9fea38e3ebd8ca03932.jpeg?imageMogr2/quality/60/format/jpg
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
         * sort : 3
         * iconUrl : http://img.kaiyanapp.com/5232f4b636b9a8840d0046aa8d143be2.png?imageMogr2/quality/60/format/jpg
         * linkUrl : route跳转路由
         * type : H5
         */

        private int sort;
        private String iconUrl;
        private String linkUrl;
        private String type;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

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
         * sort : 2
         * iconUrl : http://img.kaiyanapp.com/9c3d53ae1af82132c3368ab2d652310d.jpeg?imageMogr2/quality/60/format/jpg
         * linkUrl : route
         * type : H5
         */

        private int sort;
        private String iconUrl;
        private String linkUrl;
        private String type;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

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
