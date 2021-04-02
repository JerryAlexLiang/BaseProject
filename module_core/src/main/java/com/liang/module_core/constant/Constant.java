package com.liang.module_core.constant;

import java.util.HashMap;
import java.util.Map;


/**
 * 创建日期：2019/1/28 on 10:46
 * 描述: 全局常量
 * 作者: liangyang
 */
public class Constant {

    // ==================== SP保存传递数据的KEY =====================
    public static final String KEY_START_IMG_PATH = "KEY_START_IMG_PATH";
    public static final String KEY_START_IMG_TEXT = "KEY_START_IMG_TEXT";
    public static final String KEY_TODAY = "KEY_TODAY";
    public static final String KEY_HAS_OPEN_APP = "KEY_HAS_OPEN_APP";
    public static final String KEY_CURRENT_DATE = "KEY_CURRENT_DATE";
    public static final String KEY_NIGHT = "KEY_NIGHT";
    public static final String KEY_BIG_FONT = "KEY_BIG_FONT";
    public static final String KEY_NO_LOAD_IMAGE = "KEY_NO_LOAD_IMAGE";
    public static final String KEY_HAS_CACHE = "KEY_HAS_CACHE";

    // ==================== Intent传递数据的KEY =====================
    public static final String STORY_ID = "STORY_ID";
    public static final String THEME = "THEME";
    public static final String STORY = "STORY";
    public static final String COMMENT_COUNT = "COMMENT_COUNT";
    public static final String LONG_COMMENT_COUNT = "LONG_COMMENT_COUNT";
    public static final String SHORT_COMMENT_COUNT = "SHORT_COMMENT_COUNT";
    public static final String EDITOR = "EDITOR";
    public static final String IMG_URL = "IMG_URL";
    public static final String IMG_URL_LIST = "IMG_URL_LIST";

    // ==================== 数据库相关 =====================
    public static final String DB_NAME = "BiHu.db";
    public static final String TABLE_STORY = "t_story";
    public static final String TABLE_READER = "t_reader";
    public static final String TABLE_STAR = "t_star";
    public static final String TABLE_LIKE = "t_like";

    public static final String UID = "uid";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String IMAGE = "image";
    public static final String DATE = "date";
    public static final String MULTI_PIC = "multi_pic";
    public static final String TOP = "top";
    public static final String READ = "read";

    // ==================== fragment相关 =====================

    public static final String TAG_MAIN = "main";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TAG = "tag";

    //标题栏颜色设置
    public static final String ACTIONBAR_COLOR_THEME = "actionbar_color_theme";
    public static final int ACTIONBAR_COLOR_BLUE = 0;
    public static final int ACTIONBAR_COLOR_RED = 1;
    public static final int ACTIONBAR_COLOR_BLACK = 2;
    public static final int ACTIONBAR_COLOR_WHITE = 3;
    public static final int ACTIONBAR_COLOR_TRANSLATE = 4;
    public static final int ACTIONBAR_COLOR_GREEN = 5;

    //SmartRefreshLayout MyRefreshLottieHeader样式
    public static final String REFRESH_HEADER_STYLE = "refresh_header_style";
    public static final String REFRESH_HEADER_34115_ROCKET_LUNCH = "34115_rocket_lunch";
    public static final String REFRESH_HEADER_28402_TEMPLO = "28402_templo";

    //SmartRefreshLayout自带头部刷新动画效果
    public static final String MaterialHeader = "MaterialHeader";    //经典Swip
    public static final String WaterDropHeader = "WaterDropHeader";  //弹性水滴效果
    public static final String WaveSwipeHeader = "WaveSwipeHeader";  //下坠水滴效果
    public static final String BezierCircleHeader = "BezierCircleHeader";  //水滴下拉
    public static final String PhoenixHeader = "PhoenixHeader";  //大楼动画
    public static final String TaurusHeader = "TaurusHeader";  //飞机滑翔动画效果
    public static final String ClassicsHeader = "ClassicsHeader";  //经典带时间的刷新
    public static final String BezierRadarHeader = "BezierRadarHeader";  //雷达动画
    public static final String DeliveryHeader = "DeliveryHeader";  //快递交付动画
    public static final String DropBoxHeader = "DropBoxHeader";  //礼物盒子动画效果
    public static final String FalsifyHeader = "FalsifyHeader";  //无动画效果
    public static final String FlyRefreshHeader = "FlyRefreshHeader";  //飞机效果2
    public static final String FunGameBattleCityHeader = "FunGameBattleCityHeader";  //子弹游戏效果
    public static final String FunGameHitBlockHeader = "FunGameHitBlockHeader";  //碰球游戏效果
    public static final String StoreHouseHeader = "StoreHouseHeader";  //StoreHouse文字渐变效果
    public static final String TwoLevelHeader = "TwoLevelHeader";  //二楼



    //没有连接网络
    public static final int NETWORK_NONE = -1;
    //移动网络
    public static final int NETWORK_MOBILE = 0;
    //无线网络
    public static final int NETWORK_WIFI = 1;

    //聚合数据-新闻头条APPKEY
    public static final String NEWS_APPKEY = "c0aa201cdbd21714e8e3fbea9bdf621d";


    public static final Map map = new HashMap() {{

        put("key1", "value1");

        put("key2", "value2");

    }};

    //登录JSON
    public static final String KEY_LOGIN_JSON = "KEY_LOGIN_JSON";

    //头像本地持久化
    public static final String KEY_ICON = "KEY_ICON";

    //主页背景持久化
    public static final String KEY_BG = "KEY_BG";

    //主题模式本地持久化
    public static final String KEY_DARK_THEME = "KEY_DARK_THEME";

    //DB_NAME
    public static final String APP_DB_NAME = "BaseProject.db";

    /**
     * 友盟统计Key
     */
    public static final String YOUMENG_KEY = "5a4b01c9a40fa320590000b2";
    /**
     * 启动应用动画时间
     */
    public static final int ANIM_TIME = 3000;
    /**
     * 启动页面页面SCALE
     */
    public static final float SCALE_END = 1.15F;

    /**
     * 语言设置
     */
    public static final String[] SYSTEM_LANGUAGES = {"SYSTEM", "CHINESE", "ENGLISH"};

    /**
     * 手势CODE
     * 1:删除密码
     * 2:修改密码
     * 3:解锁成功
     */
    public static final int[] GESTURE_FLG_CODE = {1, 2, 3};

    /**
     * 图片拍照张数
     */
    public static final int MAX_PICTURE_SIZE = 9;// 最多拍照张数

    /**
     * 第一次启动应用
     */
    public static final String IS_FIRST_FLAG = "is_first_flag";

    /**
     * 判断是否设置有手势密码
     */
    public static final String IS_GESTURE_FLAG = "is_gesture_flag";

    /**
     * 存储指纹flag
     */
    public static final String IS_FINGEER_FLAG = "is_finger_flag";

    /**
     * 手势密码输入错误超过5次时间
     */
    public static final String IS_GESTURE_TIME = "is_gesture_time";
    /**
     * 应用语言设置FLAG
     */
    public static final String SYSTEM_LANGUAGE_KEY = "system_language_key";

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    public static final int REQUEST_CODE_SELECT_USER_ICON = 1;
    public static final int REQUEST_CODE_SELECT_BG = 2;

}
