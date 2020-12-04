package com.liang.module_core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * 创建日期:2020/12/4 on 4:46 PM
 * 描述: 表名
 * 作者: 杨亮
 */
@Entity(tableName = "cache")
class Cachec : Serializable {
    //PrimaryKey 必须要有,且不为空,autoGenerate 主键的值是否由Room自动生成,默认false
    @PrimaryKey(autoGenerate = false)
    var key: String? = null
    lateinit var data: ByteArray
}