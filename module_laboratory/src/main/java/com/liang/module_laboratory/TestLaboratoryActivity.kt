package com.liang.module_laboratory

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.liang.model_middleware.impl.ServiceProvider
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.AnimationUtils
import com.liang.module_core.utils.FileUtil
import com.liang.module_core.utils.LogUtil
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_core.widget.slideDampingAnimationLayout.SlideEventListener
import com.liang.module_ui.adapter.FragmentViewPagerAdapter
import com.liang.module_ui.adapter.MyBannerPagerAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.lab_activity_test_laboratory_code.*
import kotlinx.android.synthetic.main.lab_layout_base_actionbar_default.*
import kotlinx.android.synthetic.main.lab_layout_view_pager_banner.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * 创建日期:2021/1/15 on 2:39 PM
 * 描述: 测试Demo实验室
 * 作者: 杨亮
 */
class TestLaboratoryActivity : MVVMBaseActivity(), ViewPager.OnPageChangeListener {

    //TabLayout标题列表
    private val stringList: MutableList<String> = ArrayList()

    //ViewPager+TabLayout包裹内容Fragment列表
    private val fragmentList: MutableList<Fragment> = ArrayList()

    //Viewpager Banner ImageView列表
    private val mImageList: MutableList<ImageView> = ArrayList()

    //Viewpager Banner ImageView中显示文字内容列表列表
    private val mBnanerDesacList: MutableList<String> = ArrayList()
    private lateinit var imageDescs: Array<String>

    //Viewpager Banner ImageView点击跳转详情页Url列表
    private val mBannerDetailUrl: MutableList<String> = ArrayList()
    private val mTimer = Timer()
    private var previousPosition = 0 // 前一个被选中的position
    private var isStop = false //是否停止自动播放
    private var currentPosition = 0 //当前位置 = 0

    //第五步: 设置自动播放,每隔3秒换一张图片
    private val mTimerTask: TimerTask = object : TimerTask() {
        override fun run() {
            if (!isStop) {
                LogUtil.d(TAG, "开始自动播放Banner")
                //播放时，主线程更新UI
                runOnUiThread { bannerViewPager!!.currentItem = bannerViewPager!!.currentItem + 1 }
            }
        }
    }

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
        return R.layout.lab_activity_test_laboratory_code
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        base_actionbar_left_icon!!.visibility = View.VISIBLE
        base_actionbar_title!!.visibility = View.VISIBLE
        base_actionbar_title!!.text = "实验室"

        scanSlideSwipeBackLayout!!.setSlideListener(object : SlideEventListener {
            override fun leftEvent() {
                finish()
            }

            override fun rightEvent() {}
        })

//        initBanner()

        savePicture()

        //String.format("%1$#9x", -21474xxxxx)  十进制转十六进制
        val format = String.format("%1$#9x", -2147418113)
        println("kkkkkk1   $format")

        initAnimatorOpen()

        selectImage()

        initListener()
    }

    private fun initListener() {
        setOnClickListener(btnFiltrateJingdong, btnMapView, btnCamera, btnAidl, btnModularizationRouter, base_actionbar_left_icon) {
            when (this) {
                btnFiltrateJingdong -> {
                    //FiltrateActivity.actionStart(this@TestCodeActivity)
                }

                btnMapView -> {
                    //MapTestActivity.actionStart(this@TestCodeActivity)
                }

                btnCamera -> {
                    //startActivity(Intent(this@TestCodeActivity, CameraActivity::class.java))
                }

                btnAidl -> {
                    //ServiceActivity.actionStart(this@TestCodeActivity)
                }

                btnModularizationRouter -> {
                    ServiceProvider.getDatePickerService().startDatePickerDemoActivity(this@TestLaboratoryActivity)
                }

                base_actionbar_left_icon -> {
                    finish()
                }

                else -> {
                }

            }
        }
    }

    private fun selectImage() {
        btnSelectImage!!.setOnClickListener {
            //                PictureSelectorUtils.openGallery(TestCodeActivity.this, Constant.REQUEST_CODE_SELECT_USER_ICON, MULTIPLE, TYPE_IMAGE,
//                        true, false, true, 9);
            PictureSelector.create(this@TestLaboratoryActivity)
                    .openGallery(PictureMimeType.ofImage())
                    // .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                    .compressQuality(60)
                    .isCompress(true)
                    .selectionMode(PictureConfig.SINGLE) // 多选 or 单选
                    .maxSelectNum(1) // 最大图片选择数量
                    .minSelectNum(1) // 最小选择数量
                    .isPreviewImage(true) // 是否可预览图片
                    .isCamera(true) // 是否显示拍照按钮
                    .isEnableCrop(false) // 是否裁剪
                    //.circleDimmedLayer(true)// 是否圆形裁剪
                    //.freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    //.showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    //.showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .minimumCompressSize(100) // 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST)
        }
    }

    private fun initAnimatorOpen() {
        btnAnimatorOpen.setOnClickListener { AnimationUtils.pageShowScaleAnimator(this@TestLaboratoryActivity, scrollView) }
        btnAnimatorHide.setOnClickListener { AnimationUtils.pageHideScaleAnimator(scrollView) }
    }

    private fun savePicture() {
        val url1 = "http://192.168.137.1:8080/iface/downloadfile?file=C:%5CUsers%5CDESKTOP-20190220%5CDocuments%5CArcvideo+iFaceMini%5CAlarm%5C2019%5C03%5C08%5Cid%280%29_20190308-121053625.jpg"
        val url2 = "http://192.168.137.1:8080/iface/downloadfile?file=C:%5CUsers%5CDESKTOP-20190220%5CDocuments%5CArcvideo+iFaceMini%5CAlarm%5C2019%5C03%5C08%5Cid%280%29_20190308-121041511.jpg"
        Thread(Runnable {
            val millisStart = System.currentTimeMillis()
            val bitmap = FileUtil.saveImageByUrl(url1)
            val bitmap1 = FileUtil.saveImageByUrl(url2)
            runOnUiThread {
                val millisEnd = System.currentTimeMillis()
                println("返回结果0：  " + "开始时间：  " + millisStart + "结束时间:  " + millisEnd)
                println("返回结果1：  $bitmap")
                println("返回结果2：  $bitmap1")
            }
        }).start()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBanner() {
        //初始化资源
        initData()
        //初始化适配器
        val fragmentViewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager, fragmentList, stringList)
        //设置至少9个fragment，防止重复创建和销毁，造成内存溢出
        scanViewPager!!.offscreenPageLimit = 9
        //绑定适配器
        scanViewPager!!.adapter = fragmentViewPagerAdapter
        //TabLayout同步fragment
        scanTabLayout!!.setupWithViewPager(scanViewPager)

        //Banner ViewPager的Adapter
        //第二步：设置viewpager适配器
        val bannerPagerAdapter = MyBannerPagerAdapter(mImageList, bannerViewPager)
        bannerViewPager!!.adapter = bannerPagerAdapter
        //第三步：给viewpager设置轮播监听器
        bannerViewPager!!.addOnPageChangeListener(this)
        //第四步：设置刚打开app时显示的图片和文字
        setFirstLocation()
        //        //第五步: 设置自动播放,每隔3秒换一张图片
//        autoPlayView();
        mTimer.schedule(mTimerTask, 3000, 3000)
        //第七步：设置ViewPager的触摸事件，触摸停止自动播放Banner
        bannerViewPager!!.setOnTouchListener { _, event ->
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                //按下或移动手指,停止自动播放
                isStop = true
                LogUtil.d(TAG, "触摸状态，停止自动播放Banner")
            } else if (action == MotionEvent.ACTION_UP) {
                //抬起触摸，开启自动播放
                isStop = false
                LogUtil.d(TAG, "触摸抬起，开启自动播放Banner")
            }
            false
        }

        //第九步：ViewPager Banner的点击事件
        //回调相当于把对象new MyBannerPagerAdapter.ViewPagerClickInterFace()对象传给MyBannerPagerAdapter中viewPagerClickInterFace对象
        bannerPagerAdapter.setViewPagerClickInterFace {
//            WebViewDetailActivity.actionStart(this@TestCodeActivity, mBnanerDesacList[it], mBannerDetailUrl[it], "https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg")
        }
    }

    /**
     * 第三步：设置刚打开app时显示的图片和文字
     */
    private fun setFirstLocation() {
//        tvImageDesc.setText(imageDescs[previousPosition]);
        tvImageDesc!!.text = mBnanerDesacList[previousPosition]
        // *把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
        val m = Int.MAX_VALUE / 2 % mImageList.size
        currentPosition = Int.MAX_VALUE / 2 - m
        //设置ViewPager图片当前位置
        bannerViewPager!!.currentItem = currentPosition
    }

    private fun initData() {
        stringList.add("头条")
        stringList.add("国内")
        stringList.add("国际")
        stringList.add("视频")
        stringList.add("时尚")
        stringList.add("明星")
        stringList.add("科技")
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))
//        fragmentList.add(JuheNewsTabFragment.newInstance(null))

        //第一步：初始化数据
        val imageResIDs = intArrayOf(
                R.drawable.ui_picture_banner_one,
                R.drawable.ui_picture_banner_two,
                R.drawable.ui_picture_banner_three,
                R.drawable.ui_picture_banner_four,
                R.drawable.ui_picture_banner_five)
        var iv: ImageView
        for (i in imageResIDs.indices) {
            iv = ImageView(this)
            iv.setBackgroundResource(imageResIDs[i])
            mImageList.add(iv)

//            //初始化显示每张图片下面显示的文字
//            imageDescs = new String[]{
//                    "1  今年二十七八岁，我最喜欢的事就是睡觉",
//                    "2  懒虫，起床了，要上班了",
//                    "3  我翻了个身，想着如果今天是周末多好",
//                    "4  终于爬起来，坐在沙发上一脸懵逼",
//                    "5  一个人早餐就随便吃点"
//            };
        }

        //初始化显示每张图片下面显示的文字
        imageDescs = arrayOf( //                "1  今年二十七八岁，我最喜欢的事就是睡觉",
                "1今年",
                "2  懒虫，起床了，要上班了",
                "3  我翻了个身，想着如果今天是周末多好",
                "4  终于爬起来，坐在沙发上一脸懵逼",
                "5  一个人早餐就随便吃点"
        )
        mBnanerDesacList.addAll(Arrays.asList(*imageDescs))

        //mBannerDetailUrl
        //Viewpager Banner ImageView点击跳转详情页Url列表
        mBannerDetailUrl.add("https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247484939&idx=1&sn=d1871b09de55ca681da6ec2432a63ee1&chksm=96cda746a1ba2e501605dbb64b32f82b2cb44ad9c6f9ba2308a9321231b2782e6ba679ef31a6&mpshare=1&scene=23&srcid=0614bARC7g4dwQ3qbIVEJ29b#rd")
        mBannerDetailUrl.add("https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90")
        mBannerDetailUrl.add("https://www.jianshu.com/p/dd3799363318")
        mBannerDetailUrl.add("https://www.jianshu.com/p/e7dc17fcbbc6")
        mBannerDetailUrl.add("https://blog.csdn.net/a_yue10/article/details/78691288")
    }

    /**
     * 定义banner的滚动点
     */
    private fun initDots() {
        val dots = arrayOfNulls<ImageView>(llIndicatorDot!!.childCount)
        for (i in dots.indices) {
            dots[i] = llIndicatorDot!!.getChildAt(i) as ImageView
            //让ImageView有效
            dots[i]!!.isEnabled = true
            dots[i]!!.tag = i
            dots[i]!!.setOnClickListener { v ->
                val position = v.tag as Int
                currentPosition = position
                dots[currentPosition]!!.isEnabled = false
                //设置当前页面
                bannerViewPager!!.currentItem = currentPosition
            }
        }
        dots[0]!!.isEnabled = false
    }

    /**
     * 第五步: 设置自动播放,每隔3秒换一张图片
     */
    private fun autoPlayView() {
        //开启子线程模拟休眠耗时操作，自动播放图片
        Thread(Runnable {
            while (!isStop) {
                LogUtil.d(TAG, "开始自动播放Banner")
                //播放时，主线程更新UI
                runOnUiThread { bannerViewPager!!.currentItem = bannerViewPager!!.currentItem + 1 }
                SystemClock.sleep(3000)
            }
        }).start()
    }


    /**
     * 第三步：给viewpager设置轮播监听器
     * viewpager的监听器
     * 当ViewPager页面被选中时, 触发此方法.
     *
     * 当前被选中的页面的索引
     */
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        //伪无限循环，滑到最后一张图片又从新进入第一张图片
        val newPosition = position % mImageList.size

        //把当前选中的点给切换了, 还有描述信息也切换
        //图片下面设置显示文本
//        tvImageDesc.setText(imageDescs[newPosition]);
        tvImageDesc!!.text = mBnanerDesacList[newPosition]

        //第八步：定义banner的滚动点
        val dots = arrayOfNulls<ImageView>(llIndicatorDot!!.childCount)
        for (i in dots.indices) {
            dots[i] = llIndicatorDot!!.getChildAt(i) as ImageView
            //让ImageView有效
            dots[i]!!.isEnabled = true
            dots[i]!!.tag = i
            dots[i]!!.setOnClickListener { v ->
                val position = v.tag as Int
                currentPosition = position
                //设置当前页面
                bannerViewPager!!.currentItem = currentPosition
            }
        }
        dots[newPosition]!!.isEnabled = false

        //把当前的索引赋值给前一个索引变量，方便下一次再切换
        previousPosition = newPosition
    }

    /**
     * 第六步: 当Activity销毁时取消图片自动播放
     */
    override fun onDestroy() {
        super.onDestroy()
        //停止自动播放
        isStop = true
        mTimer.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.isNotEmpty()) {
                        val path: String
                        val localMedia = selectList[0]
                        path = if (localMedia.isCut && !localMedia.isCompressed) {
                            // 裁剪过
                            localMedia.cutPath
                        } else if (localMedia.isCompressed || localMedia.isCut && localMedia.isCompressed) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            localMedia.compressPath
                        } else {
                            // 原图  localMedia.getPath()
                            FileUtil.getRealPath(this@TestLaboratoryActivity, localMedia.path)
                            //                            path = getRealPath(TestCodeActivity.this, localMedia.getPath());
                        }
                        //UI
                        if (path.isNotEmpty()) {
                            Glide.with(this@TestLaboratoryActivity).asBitmap().load(path).into(ivIcon!!)
                            val options = RequestOptions()
                                    .transform(BlurTransformation(50))
                            Glide.with(this@TestLaboratoryActivity).asBitmap().apply(options).load(path).into(ivBg!!)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "TestLaboratoryActivity"

        fun actionStart(context: Context) {
            val intent = Intent(context, TestLaboratoryActivity::class.java)
            context.startActivity(intent)
        }

        // 存在沙盒中的path不能直接使用new File(path)形式生成文件 ，可以通过以下方式转换下
        fun getRealPath(context: Context, path: String?): String? {
            var path = path
            if (PictureMimeType.isContent(path)) {
                val uri = Uri.parse(path)
                try {
                    val imgFile = context.getExternalFilesDir("image")
                    if (!imgFile!!.exists()) {
                        imgFile.mkdir()
                    }
                    try {
                        val file = File(imgFile.absolutePath + File.separator +
                                System.currentTimeMillis() + ".jpg")
                        // 使用openInputStream(uri)方法获取字节输入流
                        val fileInputStream = context.contentResolver.openInputStream(uri)
                        val fileOutputStream = FileOutputStream(file)
                        val buffer = ByteArray(1024)
                        var byteRead: Int
                        while (-1 != fileInputStream!!.read(buffer).also { byteRead = it }) {
                            fileOutputStream.write(buffer, 0, byteRead)
                        }
                        fileInputStream.close()
                        fileOutputStream.flush()
                        fileOutputStream.close()
                        // 文件可用新路径 file.getAbsolutePath()
                        path = file.absolutePath
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return path
        }
    }
}