package com.liang.module_core.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.liang.module_core.R
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.LogUtil
import kotlinx.android.synthetic.main.core_activity_single_picture.baseActionBarWidget
import kotlinx.android.synthetic.main.core_activity_view_pager_picture.*
import java.io.Serializable


/**
 * 创建日期:2021/7/27 on 3:46 PM
 * 描述: ViewPager-多张大图显示器
 * 作者: 杨亮
 */
@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING", "UNCHECKED_CAST")
class ViewPagerPictureActivity : MVVMBaseActivity() {

    companion object {

        @JvmStatic
        fun actionStart(context: Context, imageUrlList: List<String?>?) {
            val intent = Intent(context, ViewPagerPictureActivity::class.java)
            intent.putExtra("imageUrlList", imageUrlList as Serializable?)
            context.startActivity(intent)
        }

        @JvmStatic
        fun actionStart(context: Context, imageUrlList: List<String?>?, descList: List<String?>?) {
            val intent = Intent(context, ViewPagerPictureActivity::class.java)
            intent.putExtra("imageUrlList", imageUrlList as Serializable?)
            intent.putExtra("descList", descList as Serializable?)
            context.startActivity(intent)
        }

        private val TAG = ViewPagerPictureActivity::class.java.simpleName
    }

    private var imageUrlList: List<String?>? = null
    private var descList: List<String?>? = null

    private var currentPage = 0

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
        return R.layout.core_activity_view_pager_picture
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        //获取传递过来的数据
        parseIntent()
        //初始化监听事件
        initListener()
        //初始化数据
        initData()
    }

    private fun initData() {
        //初始化图片容器ViewPager的适配器
        //初始化图片容器ViewPager的适配器
        val imageViewPagerAdapter = ImageViewPagerAdapter(imageUrlList as List<String>?)
        image_view_pager.adapter = imageViewPagerAdapter
        //设置当前显示位置
        //设置当前显示位置
        image_view_pager.currentItem = imageUrlList!!.indexOf(0)
    }

    private fun initListener() {
        image_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //图片指示器
                tv_current_page.text = String.format("%s/" + imageUrlList!!.size, position + 1)
                currentPage = position
                LogUtil.d(TAG, "当前图片索引是:  $currentPage")

                tvDesc.text = descList?.get(position) ?: "颜如玉"
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun parseIntent() {
        if (intent.getSerializableExtra("descList") != null) {
            descList = intent.getSerializableExtra("descList") as List<String?>
        }

        if (intent.getSerializableExtra("imageUrlList") != null) {
            imageUrlList = intent.getSerializableExtra("imageUrlList") as List<String?>
        }

        if (imageUrlList != null) {
            if (imageUrlList!!.size > 1) {
                tv_current_page.visibility = View.VISIBLE
            } else {
                tv_current_page.visibility = View.GONE
            }
        } else {
            tv_current_page.visibility = View.GONE
        }

        LogUtil.d(TAG, "imageList: ${imageUrlList.toString()}")
        LogUtil.d(TAG, "descList: ${descList.toString()}")
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
}