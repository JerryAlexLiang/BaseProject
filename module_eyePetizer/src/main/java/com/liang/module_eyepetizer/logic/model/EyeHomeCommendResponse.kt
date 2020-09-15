package com.liang.module_eyepetizer.logic.model

data class EyeHomeCommendResponse(
        val itemList: List<Item>,
        val count: Int, // 16
        val total: Int, // 0
        val nextPageUrl: String, // http://baobab.kaiyanapp.com/api/v5/index/tab/allRec?page=1&isTag=true&adIndex=5
        val adExist: Boolean // false
) {
    data class Item(
            val type: String, // squareCardCollection
            val `data`: Data,
            val tag: Any, // null
            val id: Int, // 0
            val adIndex: Int // -1
    ) {
        data class Data(
                val dataType: String, // ItemCollection
                val header: Header,
                val itemList: List<Item>,
                val count: Int, // 6
                val adTrack: Any, // null
                val footer: Any, // null
                val id: Int, // 0
                val type: String, // header5
                val text: String, // 喵星人
                val subTitle: Any, // null
                val actionUrl: String, // eyepetizer://tag/538/?title=%E5%96%B5%E6%98%9F%E4%BA%BA
                val follow: Any, // null
                val content: Content,
                val title: String, // 什么鬼：猫狗大战
                val description: String, // 当主人出门之后，猫和狗……必定会开始愉快地玩耍 ：）在中国热的浪潮下，喵星人和汪星人也是对中国元素（Kong Fu）爱到不行呢~ From CorridorDigital
                val library: String, // DAILY
                val tags: List<Tag>,
                val consumption: Consumption,
                val resourceType: String, // video
                val slogan: Any, // null
                val provider: Provider,
                val category: String, // 搞笑
                val author: Author,
                val cover: Cover,
                val playUrl: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=4044&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss
                val thumbPlayUrl: Any, // null
                val duration: Int, // 131
                val webUrl: WebUrl,
                val releaseTime: Long, // 1450584000000
                val playInfo: List<PlayInfo>,
                val campaign: Any, // null
                val waterMarks: Any, // null
                val ad: Boolean, // false
                val titlePgc: String, // 猫狗功夫大战
                val descriptionPgc: String, // 当主人离开家之后，猫狗会干点什么呢？
                val remark: Any, // null
                val ifLimitVideo: Boolean, // false
                val searchWeight: Int, // 0
                val brandWebsiteInfo: Any, // null
                val videoPosterBean: Any, // null
                val idx: Int, // 0
                val shareAdTrack: Any, // null
                val favoriteAdTrack: Any, // null
                val webAdTrack: Any, // null
                val date: Long, // 1450584000000
                val promotion: Any, // null
                val label: Any, // null
                val labelList: List<Any>,
                val descriptionEditor: String, // 当主人出门之后，猫和狗……必定会开始愉快地玩耍 ：）在中国热的浪潮下，喵星人和汪星人也是对中国元素（Kong Fu）爱到不行呢~ From CorridorDigital
                val collected: Boolean, // false
                val reallyCollected: Boolean, // false
                val played: Boolean, // false
                val subtitles: List<Any>,
                val lastViewTime: Any, // null
                val playlists: Any, // null
                val src: Int, // 7
                val recallSource: String,
                val recall_source: String,
                val image: String, // http://img.kaiyanapp.com/ae4c35ee33f9327ab3a68caa28f1ddf3.jpeg?imageMogr2/quality/60/format/jpg
                val shade: Boolean, // false
                val autoPlay: Boolean, // false
                val detail: Detail
        ) {
            data class Header(
                    val id: Int, // 5
                    val title: String, // 开眼编辑精选
                    val font: String, // bigBold
                    val subTitle: String, // TUESDAY, SEPTEMBER 15
                    val subTitleFont: String, // lobster
                    val textAlign: String, // left
                    val cover: Any, // null
                    val label: Any, // null
                    val actionUrl: String, // eyepetizer://feed?tabIndex=2
                    val labelList: Any, // null
                    val rightText: String, // 查看往期
                    val icon: String, // http://img.kaiyanapp.com/d271237c929fd2969abbf30504f588f7.jpeg?imageMogr2/quality/60/format/jpg
                    val iconType: String, // round
                    val description: String, // #萌宠
                    val time: Long, // 1592139493000
                    val showHateVideo: Boolean // false
            )

            data class Item(
                    val type: String, // followCard
                    val `data`: Data,
                    val tag: Any, // null
                    val id: Int, // 0
                    val adIndex: Int // -1
            ) {
                data class Data(
                        val dataType: String, // FollowCard
                        val header: Header,
                        val content: Content,
                        val adTrack: List<Any>
                ) {
                    data class Header(
                            val id: Int, // 212143
                            val title: String, // 我爱上了一个人，她的名字叫旅行
                            val font: Any, // null
                            val subTitle: Any, // null
                            val subTitleFont: Any, // null
                            val textAlign: String, // left
                            val cover: Any, // null
                            val label: Any, // null
                            val actionUrl: String, // eyepetizer://pgc/detail/1313/?title=%E6%97%85%E8%A1%8C%E8%A7%86%E9%A2%91%E7%B2%BE%E9%80%89&userType=PGC&tabIndex=1
                            val labelList: Any, // null
                            val rightText: Any, // null
                            val icon: String, // http://img.kaiyanapp.com/385d659e77af15fa4be37fe638c75917.jpeg?imageMogr2/quality/60/format/jpg
                            val iconType: String, // round
                            val description: Any, // null
                            val time: Long, // 1599062424000
                            val showHateVideo: Boolean // false
                    )

                    data class Content(
                            val type: String, // video
                            val `data`: Data,
                            val tag: Any, // null
                            val id: Int, // 0
                            val adIndex: Int // -1
                    ) {
                        data class Data(
                                val dataType: String, // VideoBeanForClient
                                val id: Int, // 212143
                                val title: String, // 我爱上了一个人，她的名字叫旅行
                                val description: String, // 旅行不止于旅行，发现美，看这个不同的世界。本片作者用一种温柔的视角，怀念了生命中极其重要的「她」，文字中充满忏悔、思念和感激。From Sam Newton Media
                                val library: String, // DAILY
                                val tags: List<Tag>,
                                val consumption: Consumption,
                                val resourceType: String, // video
                                val slogan: Any, // null
                                val provider: Provider,
                                val category: String, // 旅行
                                val author: Author,
                                val cover: Cover,
                                val playUrl: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=212143&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss
                                val thumbPlayUrl: Any, // null
                                val duration: Int, // 134
                                val webUrl: WebUrl,
                                val releaseTime: Long, // 1599062424000
                                val playInfo: List<PlayInfo>,
                                val campaign: Any, // null
                                val waterMarks: Any, // null
                                val ad: Boolean, // false
                                val adTrack: List<Any>,
                                val type: String, // NORMAL
                                val titlePgc: Any, // null
                                val descriptionPgc: Any, // null
                                val remark: String, // 我爱上了一个人，她的名字叫旅行
                                val ifLimitVideo: Boolean, // false
                                val searchWeight: Int, // 0
                                val brandWebsiteInfo: Any, // null
                                val videoPosterBean: Any, // null
                                val idx: Int, // 0
                                val shareAdTrack: Any, // null
                                val favoriteAdTrack: Any, // null
                                val webAdTrack: Any, // null
                                val date: Long, // 1600131600000
                                val promotion: Any, // null
                                val label: Any, // null
                                val labelList: List<Any>,
                                val descriptionEditor: String, // 旅行不止于旅行，发现美，看这个不同的世界。本片作者用一种温柔的视角，怀念了生命中极其重要的「她」，文字中充满忏悔、思念和感激。From Sam Newton Media
                                val collected: Boolean, // false
                                val reallyCollected: Boolean, // false
                                val played: Boolean, // false
                                val subtitles: List<Any>,
                                val lastViewTime: Any, // null
                                val playlists: Any, // null
                                val src: Any, // null
                                val recallSource: Any, // null
                                val recall_source: Any // null
                        ) {
                            data class Tag(
                                    val id: Int, // 10
                                    val name: String, // 跟着开眼看世界
                                    val actionUrl: String, // eyepetizer://tag/10/?title=%E8%B7%9F%E7%9D%80%E5%BC%80%E7%9C%BC%E7%9C%8B%E4%B8%96%E7%95%8C
                                    val adTrack: Any, // null
                                    val desc: String, // 去你想去的地方，发现世界的美
                                    val bgPicture: String, // http://img.kaiyanapp.com/7ea328a893aa1f092b9328a53494a267.png?imageMogr2/quality/60/format/jpg
                                    val headerImage: String, // http://img.kaiyanapp.com/50dab5468ecd2dbe5eb99dab5d608a0a.jpeg?imageMogr2/quality/60/format/jpg
                                    val tagRecType: String, // IMPORTANT
                                    val childTagList: Any, // null
                                    val childTagIdList: Any, // null
                                    val haveReward: Boolean, // false
                                    val ifNewest: Boolean, // false
                                    val newestEndTime: Any, // null
                                    val communityIndex: Int // 14
                            )

                            data class Consumption(
                                    val collectionCount: Int, // 814
                                    val shareCount: Int, // 427
                                    val replyCount: Int, // 15
                                    val realCollectionCount: Int // 217
                            )

                            data class Provider(
                                    val name: String, // YouTube
                                    val alias: String, // youtube
                                    val icon: String // http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                            )

                            data class Author(
                                    val id: Int, // 1313
                                    val icon: String, // http://img.kaiyanapp.com/385d659e77af15fa4be37fe638c75917.jpeg?imageMogr2/quality/60/format/jpg
                                    val name: String, // 旅行视频精选
                                    val description: String, // 在这个世界上的很多地方都有着美丽的景色，我们用相机用镜头记录下这一切。
                                    val link: String,
                                    val latestReleaseTime: Long, // 1600099231000
                                    val videoNum: Int, // 1351
                                    val adTrack: Any, // null
                                    val follow: Follow,
                                    val shield: Shield,
                                    val approvedNotReadyVideoCount: Int, // 0
                                    val ifPgc: Boolean, // true
                                    val recSort: Int, // 0
                                    val expert: Boolean // false
                            ) {
                                data class Follow(
                                        val itemType: String, // author
                                        val itemId: Int, // 1313
                                        val followed: Boolean // false
                                )

                                data class Shield(
                                        val itemType: String, // author
                                        val itemId: Int, // 1313
                                        val shielded: Boolean // false
                                )
                            }

                            data class Cover(
                                    val feed: String, // http://img.kaiyanapp.com/7c1beba97190141dd013c112d9709a48.png?imageMogr2/quality/60/format/jpg
                                    val detail: String, // http://img.kaiyanapp.com/7c1beba97190141dd013c112d9709a48.png?imageMogr2/quality/60/format/jpg
                                    val blurred: String, // http://img.kaiyanapp.com/41e01135419cfed166aec7a34908cc1f.png?imageMogr2/quality/60/format/jpg
                                    val sharing: Any, // null
                                    val homepage: String // http://img.kaiyanapp.com/7c1beba97190141dd013c112d9709a48.png?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                            )

                            data class WebUrl(
                                    val raw: String, // http://www.eyepetizer.net/detail.html?vid=212143
                                    val forWeibo: String // http://www.eyepetizer.net/detail.html?vid=212143&resourceType=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                            )

                            data class PlayInfo(
                                    val height: Int, // 720
                                    val width: Int, // 1280
                                    val urlList: List<Url>,
                                    val name: String, // 高清
                                    val type: String, // high
                                    val url: String // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=212143&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss
                            ) {
                                data class Url(
                                        val name: String, // aliyun
                                        val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=212143&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss
                                        val size: Int // 41142245
                                )
                            }
                        }
                    }
                }
            }

            data class Content(
                    val type: String, // video
                    val `data`: Data,
                    val tag: Any, // null
                    val id: Int, // 0
                    val adIndex: Int // -1
            ) {
                data class Data(
                        val dataType: String, // VideoBeanForClient
                        val id: Int, // 196905
                        val title: String, // 不知何故生气的猫
                        val description: String, // 猫为什么又又又生气了？
                        val library: String, // DEFAULT
                        val tags: List<Tag>,
                        val consumption: Consumption,
                        val resourceType: String, // video
                        val slogan: Any, // null
                        val provider: Provider,
                        val category: String, // 萌宠
                        val author: Author,
                        val cover: Cover,
                        val playUrl: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=196905&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss
                        val thumbPlayUrl: Any, // null
                        val duration: Int, // 224
                        val webUrl: WebUrl,
                        val releaseTime: Long, // 1592139493000
                        val playInfo: List<PlayInfo>,
                        val campaign: Any, // null
                        val waterMarks: Any, // null
                        val ad: Boolean, // false
                        val adTrack: List<Any>,
                        val type: String, // NORMAL
                        val titlePgc: String, // 不知何故生气的猫
                        val descriptionPgc: String, // 猫为什么又又又生气了？
                        val remark: Any, // null
                        val ifLimitVideo: Boolean, // false
                        val searchWeight: Int, // 0
                        val brandWebsiteInfo: Any, // null
                        val videoPosterBean: Any, // null
                        val idx: Int, // 0
                        val shareAdTrack: Any, // null
                        val favoriteAdTrack: Any, // null
                        val webAdTrack: Any, // null
                        val date: Long, // 1592139493000
                        val promotion: Any, // null
                        val label: Any, // null
                        val labelList: List<Any>,
                        val descriptionEditor: String,
                        val collected: Boolean, // false
                        val reallyCollected: Boolean, // false
                        val played: Boolean, // false
                        val subtitles: List<Any>,
                        val lastViewTime: Any, // null
                        val playlists: Any, // null
                        val src: Int, // 7
                        val recallSource: String,
                        val recall_source: String
                ) {
                    data class Tag(
                            val id: Int, // 32
                            val name: String, // 萌宠
                            val actionUrl: String, // eyepetizer://tag/32/?title=%E8%90%8C%E5%AE%A0
                            val adTrack: Any, // null
                            val desc: String, // 来自汪星、喵星、蠢萌星的你
                            val bgPicture: String, // http://img.kaiyanapp.com/bd689302a738a5d2156243a2b74f15d1.png?imageMogr2/quality/60/format/jpg
                            val headerImage: String, // http://img.kaiyanapp.com/1ea685a0d2757701741a4a9be0f7e269.jpeg?imageMogr2/quality/60/format/jpg
                            val tagRecType: String, // NORMAL
                            val childTagList: Any, // null
                            val childTagIdList: Any, // null
                            val haveReward: Boolean, // false
                            val ifNewest: Boolean, // false
                            val newestEndTime: Any, // null
                            val communityIndex: Int // 0
                    )

                    data class Consumption(
                            val collectionCount: Int, // 12
                            val shareCount: Int, // 0
                            val replyCount: Int, // 0
                            val realCollectionCount: Int // 6
                    )

                    data class Provider(
                            val name: String, // 定制来源
                            val alias: String, // CustomSrc
                            val icon: String
                    )

                    data class Author(
                            val id: Int, // 1561
                            val icon: String, // http://img.kaiyanapp.com/d271237c929fd2969abbf30504f588f7.jpeg?imageMogr2/quality/60/format/jpg
                            val name: String, // MAKO0MAKO0
                            val description: String, // 与我家萌萌的喵星人们一起生活。
                            val link: String,
                            val latestReleaseTime: Long, // 1592139493000
                            val videoNum: Int, // 297
                            val adTrack: Any, // null
                            val follow: Follow,
                            val shield: Shield,
                            val approvedNotReadyVideoCount: Int, // 0
                            val ifPgc: Boolean, // true
                            val recSort: Int, // 0
                            val expert: Boolean // false
                    ) {
                        data class Follow(
                                val itemType: String, // author
                                val itemId: Int, // 1561
                                val followed: Boolean // false
                        )

                        data class Shield(
                                val itemType: String, // author
                                val itemId: Int, // 1561
                                val shielded: Boolean // false
                        )
                    }

                    data class Cover(
                            val feed: String, // http://img.kaiyanapp.com/b3b5758c7cf244f7db422abcfa9f11c2.jpeg?imageMogr2/quality/60/format/jpg
                            val detail: String, // http://img.kaiyanapp.com/b3b5758c7cf244f7db422abcfa9f11c2.jpeg?imageMogr2/quality/60/format/jpg
                            val blurred: String, // http://img.kaiyanapp.com/316562ea61e955602c09c6e5335ffd7b.jpeg?imageMogr2/quality/60/format/jpg
                            val sharing: Any, // null
                            val homepage: Any // null
                    )

                    data class WebUrl(
                            val raw: String, // http://www.eyepetizer.net/detail.html?vid=196905
                            val forWeibo: String // http://www.eyepetizer.net/detail.html?vid=196905&resourceType=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                    )

                    data class PlayInfo(
                            val height: Int, // 480
                            val width: Int, // 854
                            val urlList: List<Url>,
                            val name: String, // 标清
                            val type: String, // normal
                            val url: String // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=9586&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss
                    ) {
                        data class Url(
                                val name: String, // aliyun
                                val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=9586&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss
                                val size: Int // 19835516
                        )
                    }
                }
            }

            data class Tag(
                    val id: Int, // 750
                    val name: String, // 搞笑短片
                    val actionUrl: String, // eyepetizer://tag/750/?title=%E6%90%9E%E7%AC%91%E7%9F%AD%E7%89%87
                    val adTrack: Any, // null
                    val desc: Any, // null
                    val bgPicture: String, // http://img.kaiyanapp.com/9f3c1d7ff7e62119d6f7a21e2c367571.jpeg?imageMogr2/quality/60/format/jpg
                    val headerImage: String, // http://img.kaiyanapp.com/9f3c1d7ff7e62119d6f7a21e2c367571.jpeg?imageMogr2/quality/60/format/jpg
                    val tagRecType: String, // NORMAL
                    val childTagList: Any, // null
                    val childTagIdList: Any, // null
                    val haveReward: Boolean, // false
                    val ifNewest: Boolean, // false
                    val newestEndTime: Any, // null
                    val communityIndex: Int // 0
            )

            data class Consumption(
                    val collectionCount: Int, // 1739
                    val shareCount: Int, // 3291
                    val replyCount: Int, // 17
                    val realCollectionCount: Int // 12
            )

            data class Provider(
                    val name: String, // 定制来源
                    val alias: String, // CustomSrc
                    val icon: String
            )

            data class Author(
                    val id: Int, // 759
                    val icon: String, // http://img.kaiyanapp.com/20842603c726400ecbc7967897a3fab8.jpeg?imageMogr2/quality/60/format/jpg
                    val name: String, // Corridor
                    val description: String, // 由 Sam 和 Niko 以及他们强大的团队创造的超棒视频，只有你想不到，没有他们做不到。
                    val link: String,
                    val latestReleaseTime: Long, // 1595898664000
                    val videoNum: Int, // 61
                    val adTrack: Any, // null
                    val follow: Follow,
                    val shield: Shield,
                    val approvedNotReadyVideoCount: Int, // 0
                    val ifPgc: Boolean, // true
                    val recSort: Int, // 0
                    val expert: Boolean // false
            ) {
                data class Follow(
                        val itemType: String, // author
                        val itemId: Int, // 759
                        val followed: Boolean // false
                )

                data class Shield(
                        val itemType: String, // author
                        val itemId: Int, // 759
                        val shielded: Boolean // false
                )
            }

            data class Cover(
                    val feed: String, // http://img.kaiyanapp.com/20a6e3967c10f2dde4663df8121fd566.jpeg?imageMogr2/quality/100
                    val detail: String, // http://img.kaiyanapp.com/20a6e3967c10f2dde4663df8121fd566.jpeg?imageMogr2/quality/100
                    val blurred: String, // http://img.kaiyanapp.com/fa6bb30859fb34aa51a0350e4381cd8d.jpeg?imageMogr2/quality/100
                    val sharing: Any, // null
                    val homepage: Any // null
            )

            data class WebUrl(
                    val raw: String, // http://www.eyepetizer.net/detail.html?vid=4044
                    val forWeibo: String // http://wandou.im/11itc6
            )

            data class PlayInfo(
                    val height: Int, // 480
                    val width: Int, // 848
                    val urlList: List<Url>,
                    val name: String, // 标清
                    val type: String, // normal
                    val url: String // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=4044&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss
            ) {
                data class Url(
                        val name: String, // aliyun
                        val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=4044&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss
                        val size: Int // 16968475
                )
            }

            data class Detail(
                    val id: Int, // 601
                    val icon: String, // http://img.kaiyanapp.com/112f230532c703ddeab27d6bde12dcb4.jpeg?imageMogr2/quality/60/format/jpg
                    val title: String, // 修护原力 强韧新生
                    val description: String, // 海蓝之谜浓缩修护精华全新上市
                    val url: String, // https://baobab.kaiyanapp.com/api/v1/playUrl/1599639578164_91ea5d86.mp4?vid=-1&source=aliyun
                    val adaptiveUrls: String,
                    val actionUrl: String, // https://market.m.taobao.com/app/tb-source-app/shopact/pages/index?wh_weex=true&pathInfo=shop/activity&userId=2424298091&shopId=116845644&pageId=256579071&alisite=true&mm_sycmid=1_93505_9fafb823c89e9fe32eda8cfbd86acab2
                    val iosActionUrl: String, // https://market.m.taobao.com/app/tb-source-app/shopact/pages/index?wh_weex=true&pathInfo=shop/activity&userId=2424298091&shopId=116845644&pageId=256579071&alisite=true&mm_sycmid=1_93505_9fafb823c89e9fe32eda8cfbd86acab2
                    val imageUrl: String, // http://img.kaiyanapp.com/5acfff342c6bbec54f31a7177653767f.jpeg?imageMogr2/quality/60/format/jpg
                    val adaptiveImageUrls: String,
                    val displayTimeDuration: Int, // 0
                    val displayCount: Int, // 10000
                    val showImage: Boolean, // false
                    val showImageTime: Int, // 0
                    val loadingMode: Int, // 1
                    val countdown: Boolean, // false
                    val canSkip: Boolean, // false
                    val timeBeforeSkip: Int, // 0
                    val showActionButton: Boolean, // false
                    val videoType: String, // NORMAL
                    val videoAdType: String, // TAB_PAGE
                    val categoryId: Int, // -2
                    val position: Int, // 2
                    val adTrack: List<AdTrack>,
                    val openSound: Boolean, // false
                    val ifLinkage: Boolean, // false
                    val linkageAdId: Int, // 0
                    val cycleCount: Int // 0
            ) {
                data class AdTrack(
                        val id: Int, // 632
                        val organization: String, // miaozhen
                        val viewUrl: String,
                        val clickUrl: String, // http://e.cn.miaozhen.com/r/k=2181186&p=7c9Sw&dx=__IPDX__&rt=2&pro=n&ns=__IP__&ni=__IESID__&v=__LOC__&xa=__ADPLATFORM__&tr=__REQUESTID__&mo=__OS__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&m11=__OAID__&mn=__ANAME__&o=
                        val playUrl: String,
                        val monitorPositions: String,
                        val needExtraParams: List<String>
                )
            }
        }
    }
}