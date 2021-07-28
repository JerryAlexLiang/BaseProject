package com.liang.module_core.widget

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bigkoo.alertview.AlertView
import com.liang.module_core.R
import com.liang.module_core.utils.FileUtil
import com.liang.module_core.utils.ImageLoaderUtils
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * 创建日期:2021/7/27 on 3:53 PM
 * 描述: 多张图片显示器-ViewPager的适配器
 * 作者: 杨亮
 */
class ImageViewPagerAdapter(private val imageUrlList: List<String>?) : PagerAdapter() {

    override fun getCount(): Int {
        return imageUrlList?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(container.context)
        ImageLoaderUtils.loadRadiusImage(container.context, true, photoView, imageUrlList!![position],
                R.drawable.core_default_pic_content_image_loading_light, R.drawable.core_default_pic_content_image_download_light, 0)
        photoView.setZoomable(true)
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        photoView.setOnLongClickListener { v: View? ->
//            val builder = AlertDialog.Builder(container.context)
//            builder.setTitle("保存图片")
//                    .setMessage("图片将保存到手机内存中，会占用内存哦~")
//                    .setNegativeButton("取消") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
//                    .setPositiveButton("下载") { dialog: DialogInterface, which: Int ->
//                        FileUtil.saveImage(container.context, photoView, System.currentTimeMillis().toString())
//                        dialog.dismiss()
//                    }
//                    .show()

            AlertView.Builder()
                    .setContext(container.context)
                    .setTitle("保存图片")
                    .setMessage("下载图片到手机内存中，会占用内存哦")
                    .setDestructive("取消", "下载")
                    .setOthers(null)
                    .setStyle(AlertView.Style.Alert)
                    .setOnItemClickListener { o, position ->
                        when (position) {
                            1 -> {
                                FileUtil.saveImage(container.context, photoView, System.currentTimeMillis().toString())
                            }
                        }
                    }
                    .build()
                    .show()
            false
        }

        //photoView的单击点击事件- 仿微博单击后退出大图界面
        photoView.onPhotoTapListener = PhotoViewAttacher.OnPhotoTapListener { view: View?, x: Float, y: Float -> (container.context as Activity).finish() }
        return photoView
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}