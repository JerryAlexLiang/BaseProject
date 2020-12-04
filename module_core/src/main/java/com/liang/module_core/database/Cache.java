package com.liang.module_core.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * 创建日期:2020/12/4 on 4:47 PM
 * 描述: 缓存表使用java kotlin会有主键冲突问题，后面解决改为Kotlin
 * 作者: 杨亮
 */
//表名
@Entity(tableName = "cache")
public class Cache implements Serializable {
    //PrimaryKey 必须要有,且不为空,autoGenerate 主键的值是否由Room自动生成,默认false
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String key;

    public byte[] data;

}

