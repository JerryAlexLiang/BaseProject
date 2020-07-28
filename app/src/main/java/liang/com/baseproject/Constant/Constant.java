package liang.com.baseproject.Constant;

import com.amap.api.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import liang.com.baseproject.R;

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
     * 应用启动随机
     */
    public static final int[] SPLASH_IMGS = {
            R.drawable.welcomimg1, R.drawable.welcomimg2,
            R.drawable.welcomimg3, R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7, R.drawable.welcomimg8,
            R.drawable.welcomimg9, R.drawable.welcomimg10,
            R.drawable.welcomimg11, R.drawable.welcomimg12};
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

    /**
     * 离线地图
     */
    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
    public static final LatLng BOZHOU = new LatLng(33.869338, 115.782939);//亳州经纬度
    public static final LatLng FUYANG = new LatLng(32.896969, 115.819729);//阜阳经纬度
    public static final LatLng HANZGHOU = new LatLng(30.291115, 120.114108);//杭州经纬度
    public static final LatLng NINGBO = new LatLng(29.859515, 121.6216);//宁波经纬度
    public static final LatLng ZIBO = new LatLng(36.832651, 117.98299);//淄博经纬度

    //图片Url及对应经纬度
    //费启鸣
    public static final String FEIQIMING = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573104184485&di=004da23722bad904fc35b6de5a3e0ab6&imgtype=0&src=http%3A%2F%2Fimage.uc.cn%2Fs%2Fwemedia%2Fs%2Fupload%2F2019%2F9fb79e14b91bdecc57173c409b7e2777.jpg";
    public static final LatLng FEIQIMING_LATLNG = new LatLng(30.289406, 120.114317);
    //马天宇
    public static final String MATIANYU = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570685761527&di=242567a8fbb3b2d1b82233f5b4ef25ea&imgtype=0&src=http%3A%2F%2F00imgmini.eastday.com%2Fmobile%2F20190907%2F20190907172442_7db7e5407ac69b339ae40b3090632250_1.jpeg";
    public static final LatLng MATIANYU_LATLNG = new LatLng(30.291722, 120.116297);
    //马嘉琪
    public static final String MAJIAQI = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570685798443&di=a7e17871d4c5a659e3cfb907030da273&imgtype=0&src=http%3A%2F%2Fp.store.itangyuan.com%2Fp%2Fbook%2Fcover%2F4B6v4gbueA%2FEg6TEBES4BjwEtMwEtMsETuReHemJhyVhA.jpg";
    public static final LatLng MAJIAQI_LATLNG = new LatLng(30.288146, 120.119666);
    //肖战
    public static final String XIAOZHAN = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570685823607&di=68c8e5c4d1f467f742b782b09532bb17&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F18%2F20180618235127_yglui.jpg";
    public static final LatLng XIAOZHAN_LATLNG = new LatLng(30.285847, 120.12133);
    //丁程鑫
    public static final String DINGCHENGXIN = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570685871918&di=51e080ce2ccd92e9ea958d173273f9d1&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F21%2F20180821212620_hwmnx.thumb.700_0.jpg";
    public static final LatLng DINGCHENGXIN_LATLNG = new LatLng(30.285811, 120.116812);
    //姚景元
    public static final String YAOJINGYUAN = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570685489375&di=4ec083facefe99c5df83326e6fb3a169&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn12%2F300%2Fw1620h1080%2F20180816%2Ffab8-hhvciiv7482028.jpg";
    public static final LatLng YAOJINGYUAN_LATLNG = new LatLng(30.290999, 120.121313);
}
