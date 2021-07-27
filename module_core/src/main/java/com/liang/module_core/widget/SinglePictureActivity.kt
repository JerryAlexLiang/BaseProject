package com.liang.module_core.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bigkoo.alertview.AlertView
import com.liang.module_core.R
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.FileUtil
import com.liang.module_core.utils.ImageLoaderUtils
import com.liang.module_core.utils.setOnClickListener
import kotlinx.android.synthetic.main.core_activity_single_picture.*
import kotlinx.android.synthetic.main.core_layout_base_actionbar_default.*

/**
 * 创建日期:2021/7/27 on 2:40 PM
 * 描述: 单张大图显示器
 * 作者: 杨亮
 */
class SinglePictureActivity : MVVMBaseActivity(), View.OnLongClickListener {

    companion object {
        val IMG_URL = "img_url"
        val IMG_DESC = "img_desc"
        val TRANSIT_PIC = "picture"

        @JvmStatic
        fun actionStart(context: Context, imageUrl: String?, imageDesc: String?) {
            val intent = Intent(context, SinglePictureActivity::class.java)
            intent.putExtra(SinglePictureActivity.IMG_URL, imageUrl)
            intent.putExtra(SinglePictureActivity.IMG_DESC, imageDesc)
            context.startActivity(intent)
        }

    }

    private var imgUrl: String? = null
    private var imgDesc: String? = null

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun provideContentViewId(): Int {
        return R.layout.core_activity_single_picture
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initActionBar()
        //获取传递过来的数据
        parseIntent()
        //赋值
        ImageLoaderUtils.loadRadiusImage(this, true, iv_meizhi_zoom_pic, imgUrl, 0, 0, 0)
        //点击事件
        initListener()
    }

    private fun initListener() {
        setOnClickListener(base_actionbar_right_icon, save_img) {
            when (this) {
                base_actionbar_right_icon -> {
                    savePictureLocal()
                }

                save_img -> {
                    savePictureLocal();
                }
            }
        }

        //ivMeizhiZoomPic的长按点击事件
        iv_meizhi_zoom_pic.setOnLongClickListener(this);

        //ivMeizhiZoomPic的单击点击事件- 仿微博单击后退出大图界面   ((Activity)context).finish();
        iv_meizhi_zoom_pic.setOnPhotoTapListener { _, _, _ ->
            finish()
        }
    }

    private fun savePictureLocal() {
        if (imgUrl!!.startsWith("http://") || imgUrl!!.startsWith("https://")) {
            savePicByUrl()
        } else {
            FileUtil.saveImage(this@SinglePictureActivity, iv_meizhi_zoom_pic, imgDesc)
        }
    }

    private fun savePicByUrl() {
        Thread {
            val bitmap = FileUtil.saveImageByUrl(imgUrl)
            runOnUiThread { FileUtil.translateBitmapToFile(mActivity, bitmap, imgDesc) }
        }.start()
    }

    private fun parseIntent() {
        imgUrl = intent.getStringExtra(IMG_URL);
        imgDesc = intent.getStringExtra(IMG_DESC);
    }

    private fun initActionBar() {
        baseActionBarWidget.setActionBarHeight(100)
        baseActionBarWidget.initViewsVisible(true, false, false,
                true, false, true, false)
        baseActionBarWidget.setActionBarTitle(this, "Picture", R.color.colorWhite)
        baseActionBarWidget.setRightButtonIcon(R.drawable.core_icon_file_download)
//                baseActionBarWidget.setActionBarBackgroundResource(R.drawable.core_icon_bg_header)
        baseActionBarWidget.setOnLeftButtonClickListener {
            finish()
        }
    }

    override fun onLongClick(v: View?): Boolean {
//        val builder = AlertDialog.Builder(mActivity)
//        builder.setTitle("保存图片")
//                .setMessage("图片将保存到手机内存中，会占用内存哦~")
//                .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
//                .setPositiveButton("下载") { dialog, _ ->
//                    savePicByUrl()
//                    dialog.dismiss()
//                }
//                .show()

        AlertView.Builder()
                .setContext(this)
                .setTitle("保存图片")
                .setMessage("图片将保存到手机内存中，会占用内存哦~")
                .setDestructive("取消", "确定")
                .setOthers(null)
                .setStyle(AlertView.Style.Alert)
                .setOnItemClickListener { o, position ->
                    when (position) {
                        1 -> {
                            savePicByUrl()
                        }
                    }
                }
                .build()
                .show()


        return false
    }
}