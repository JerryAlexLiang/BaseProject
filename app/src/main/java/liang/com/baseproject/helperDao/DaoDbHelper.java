package liang.com.baseproject.helperDao;

import android.database.sqlite.SQLiteDatabase;

import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.gen.DaoMaster;
import liang.com.baseproject.gen.DaoSession;

import static liang.com.baseproject.Constant.Constant.APP_DB_NAME;

/**
 * 创建日期：2019/8/29 on 16:55
 * 描述: GreenDao - 数据库Base工具类
 * 作者: liangyang
 */

public class DaoDbHelper {

    public static final String DB_NAME = APP_DB_NAME;
    private static volatile DaoDbHelper sInstance;
    private final SQLiteDatabase db;
    private final DaoMaster daoMaster;
    private final DaoSession daoSession;

    public static DaoDbHelper getInstance() {
        if (sInstance == null) {
            synchronized (DaoDbHelper.class) {
                if (sInstance == null) {
                    sInstance = new DaoDbHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 这里建议使用DaoMaster.OpenHelper ,不要使用DaoMaster.DevOpenHelper,
     * 因为使用DaoMaster.DevOpenHelper每次升级数据库都会把表删除重建，推荐开发时用。
     * 正式使用时还是用DaoMaster.OpenHelper。
     */
    private DaoDbHelper() {
        //封装数据库的创建、更新、删除
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApplication.getAppContext(), DB_NAME, null);
        //获取可写数据库
        db = openHelper.getWritableDatabase();
        //获取数据库对象
        //合起来就是对数据库的操作
        daoMaster = new DaoMaster(db);
        //获取Dao对象管理者(对表操作的对象),可以认为是对数据的操作
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DaoSession getNewSession() {
        return daoMaster.newSession();
    }
}
