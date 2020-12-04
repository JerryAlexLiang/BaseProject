package com.liang.module_core.database;

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liang.module_core.app.BaseApplication

/**
 * 创建日期:2020/12/4 on 4:45 PM
 * 描述:
 * 作者: 杨亮
 */
@Database(entities = [Cache::class], version = 1) //数据读取、存储时数据转换器,比如将写入时将Date转换成Long存储，读取时把Long转换Date返回
//@TypeConverters(DateConverter.class)
abstract class CacheDatabase : RoomDatabase() {
    companion object {
        private var database: CacheDatabase? = null

        @JvmStatic
        fun get(): CacheDatabase? {
            return database
        }

        init {
            //创建一个内存数据库
            //但是这种数据库的数据只存在于内存中，也就是进程被杀之后，数据随之丢失
            //Room.inMemoryDatabaseBuilder()
            database = Room.databaseBuilder(BaseApplication.getAppContext(), CacheDatabase::class.java, "fuson_cache") //是否允许在主线程进行查询
                    .allowMainThreadQueries()
                    .build()
        }
    }

    abstract val cache: CacheDao?
}