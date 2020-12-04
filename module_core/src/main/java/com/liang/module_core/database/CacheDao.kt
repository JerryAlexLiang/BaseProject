package com.liang.module_core.database;
import androidx.room.*

/**
 * 创建日期:2020/12/4 on 4:46 PM
 * 描述:
 * 作者: 杨亮
 */
@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cache: Cache?): Long

    /**
     * 注意，冒号后面必须紧跟参数名，中间不能有空格。大于小于号和冒号中间是有空格的。
     * select *from cache where【表中列名】 =:【参数名】------>等于
     * where 【表中列名】 < :【参数名】 小于
     * where 【表中列名】 between :【参数名1】 and :【参数2】------->这个区间
     * where 【表中列名】like :参数名----->模糊查询
     * where 【表中列名】 in (:【参数名集合】)---->查询符合集合内指定字段值的记录
     */
    //如果是一对多,这里可以写List<Cache>
    @Query("select *from cache where `key`=:key")
    fun getCache(key: String?): Cache?

    //只能传递对象昂,删除时根据Cache中的主键 来比对的
    @Delete
    fun delete(cache: Cache?): Int

    //只能传递对象昂,删除时根据Cache中的主键 来比对的
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache?): Int
}