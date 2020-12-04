package com.liang.module_core.database;

import com.liang.module_core.database.CacheDatabase.Companion.get
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * 创建日期:2020/12/4 on 4:45 PM
 * 描述: 数据库管理类 数据二进制化可能更加便捷一点
 * 作者: 杨亮
 */
object CacheManager {
    //反序列,把二进制数据转换成java object对象
    private fun toObject(data: ByteArray): Any? {
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                bais?.close()
                ois?.close()
            } catch (ignore: Exception) {
                ignore.printStackTrace()
            }
        }
        return null
    }

    //序列化存储数据需要转换成二进制
    private fun <T> toByteArray(body: T): ByteArray {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                baos?.close()
                oos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ByteArray(0)
    }

    fun <T> delete(key: String?, body: T) {
        val cache = Cache()
        cache.key = key!!
        cache.data = toByteArray(body)
        get()!!.cache!!.delete(cache)
    }

    fun <T> save(key: String?, body: T) {
        val cache = Cache()
        cache.key = key!!
        cache.data = toByteArray(body)
        get()!!.cache!!.save(cache)
        getCache(key)
    }

    fun getCache(key: String?): Any? {
        val cache = get()!!.cache!!.getCache(key)
        return if (cache != null && cache.data != null) {
            toObject(cache.data)
        } else null
    }
}