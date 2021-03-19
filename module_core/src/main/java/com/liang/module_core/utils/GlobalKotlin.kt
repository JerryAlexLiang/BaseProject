package com.liang.module_core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.text.format.DateFormat
import android.widget.Toast
import java.io.ByteArrayOutputStream

object GlobalKotlin {
    private var context: Context? = null
    private var mScreenWidth = 0
    private var mScreenHeight = 0
    private var mDensity = 0f
    fun init(context: Context) {
        GlobalKotlin.context = context
        val dm = context.resources.displayMetrics
        mScreenWidth = dm.widthPixels
        mScreenHeight = dm.heightPixels
        mDensity = dm.density
    }

    /** 单位转换dp转px  */
    fun dp2px(dp: Int): Int {
        return (dp * mDensity + 0.5f).toInt()
    }

    fun showToast(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 读取assets目录下的文件
     * @param fileName 文件名
     * @return
     */
    fun readAssets(fileName: String?): String {
        var str = ""
        try {
            val stream = context!!.resources.assets.open(fileName!!)
            val buffer = ByteArray(1024)
            var len = -1
            val baos = ByteArrayOutputStream()
            while (stream.read(buffer).also { len = it } != -1) {
                // 读取数据到内存中
                baos.write(buffer, 0, len)
            }
            str = baos.toString("utf-8")
            stream.close()
            baos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    fun formatDate(time: Long): String {
        return DateFormat.format("yyyy-MM-dd kk:mm:ss", time).toString()
    }

    /**
     * 根据图片名称获取资源id
     * 例如：传入"avatar_01", 表示获取整数值R.drawable.avatar_01
     * @param context
     * @param drawableName
     * @return
     */
    fun getResId(context: Context, drawableName: String?): Int {
        return context.resources.getIdentifier(
                drawableName, "drawable", context.packageName)
    }// 左边距：   10dp
    // 右边距：   10dp
    // 图片宫格的宽度 = (屏幕宽度-左边距-右边距) / 3

    /** 获取宫格图片的宽高  */
    val gridWidth: Int
        get() =// 左边距：   10dp
        // 右边距：   10dp
                // 图片宫格的宽度 = (屏幕宽度-左边距-右边距) / 3
//            (mScreenWidth - dp2px(10 + 10)) / 3
            (mScreenWidth - dp2px(10 + 10)) / 2

    /**
     * 根据一张原图生成一张指定宽高的图片
     *
     * @param bitmap 原图
     * @param width 要生成图片的宽高
     * @return
     */
    fun createBitmap(bitmap: Bitmap, width: Int): Bitmap {
        val scaleX = width.toFloat() / bitmap.width
        val scaleY = width.toFloat() / bitmap.height
        val matrix = Matrix()
        matrix.postScale(scaleX, scaleY)
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.width, bitmap.height, matrix, true)
    }
}