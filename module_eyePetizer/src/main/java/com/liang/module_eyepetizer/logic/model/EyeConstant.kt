package com.liang.module_eyepetizer.logic.model

/**
 * 创建日期: 2020/11/24 on 4:25 PM
 * 描述:
 * 作者: 杨亮
 */
interface EyeConstant {

    interface IDisCoverItemType {

        companion object {

            /**
             * 未知类型，使用EmptyViewHolder容错处理
             */
            const val UNKNOWN = 0

            /**
             * 顶部banner
             */
            const val TOP_BANNER_VIEW = 1

            /**
             * 热门分类
             */
            const val CATEGORY_CARD_VIEW = 2

            /**
             * 专题策划
             */
            const val SUBJECT_CARD_VIEW = 3

            /**
             * 标题栏
             */
            const val TITLE_VIEW = 4

            /**
             * 内容banner
             */
            const val CONTENT_BANNER_VIEW = 5

            /**
             * 本周视频榜单
             */
            const val VIDEO_CARD_VIEW = 6

            /**
             * 推荐主题
             */
            const val THEME_CARD_VIEW = 7


        }

    }

    interface IDisCoverItemReturnType {

        companion object {
            /**
             * 顶部banner
             */
            const val horizontalScrollCard = "horizontalScrollCard"

            /**
             * 热门分类
             */
            const val specialSquareCardCollection = "specialSquareCardCollection"

            /**
             * 专题策划
             */
            const val columnCardList = "columnCardList"

            /**
             * 标题栏
             */
            const val textCard = "textCard"

            /**
             * 内容banner
             */
            const val banner = "banner"

            /**
             * 本周视频榜单
             */
            const val videoSmallCard = "videoSmallCard"

            /**
             * 推荐主题
             */
            const val briefCard = "briefCard"

        }
    }
}