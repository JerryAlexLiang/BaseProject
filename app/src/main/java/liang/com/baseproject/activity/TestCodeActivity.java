package liang.com.baseproject.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.liang.model_middleware.impl.ServiceProvider;
import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.utils.AnimationUtils;
import com.liang.module_core.utils.FileUtil;
import com.liang.module_core.utils.LogUtil;
import com.liang.module_core.widget.slideDampingAnimationLayout.SlideDampingAnimationLayout;
import com.liang.module_core.widget.slideDampingAnimationLayout.SlideEventListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.adapter.MyBannerPagerAdapter;
import liang.com.baseproject.fragment.JuheNewsTabFragment;
import liang.com.baseproject.testlaboratory.FiltrateActivity;
import liang.com.baseproject.testlaboratory.MapTestActivity;

public class TestCodeActivity extends MVPBaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = TestCodeActivity.class.getSimpleName();
    @BindView(R.id.base_actionbar_left_icon)
    ImageView ivBaseLeftIcon;
    @BindView(R.id.base_actionbar_title)
    TextView tvBaseTitle;
    @BindView(R.id.scan_tab_layout)
    TabLayout scanTabLayout;
    @BindView(R.id.scan_view_pager)
    ViewPager scanViewPager;
    @BindView(R.id.banner_view_pager)
    ViewPager bannerViewPager;
    @BindView(R.id.tv_image_desc)
    TextView tvImageDesc;
    @BindView(R.id.ll_indicator_dot)
    LinearLayout llIndicatorDot;
    @BindView(R.id.scan_slide_swipe_back_layout)
    SlideDampingAnimationLayout scanSlideSwipeBackLayout;
    @BindView(R.id.scroll_View)
    ScrollView scrollView;
    @BindView(R.id.btn_select_image)
    Button btnSelectImage;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.btn_animator_open)
    Button btnAnimatorOpen;
    @BindView(R.id.btn_animator_hide)
    Button btnAnimatorHide;
    @BindView(R.id.btn_filtrate_jingdong)
    Button btnFiltrateJingdong;
    @BindView(R.id.btn_map_view)
    Button btnMapView;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_aidl)
    Button btnServiceAidl;
    @BindView(R.id.btn_modularization_router)
    Button btnModularizationRouter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TestCodeActivity.class);
        context.startActivity(intent);
    }

    //TabLayout标题列表
    private final List<String> stringList = new ArrayList<>();
    //ViewPager+TabLayout包裹内容Fragment列表
    private final List<Fragment> fragmentList = new ArrayList<>();

    //Viewpager Banner ImageView列表
    private final List<ImageView> mImageList = new ArrayList<>();
    //Viewpager Banner ImageView中显示文字内容列表列表
    private final List<String> mBnanerDesacList = new ArrayList<>();
    private String[] imageDescs;

    //Viewpager Banner ImageView点击跳转详情页Url列表
    private final List<String> mBannerDetailUrl = new ArrayList<>();

    private final Timer mTimer = new Timer();
    private int previousPosition = 0; // 前一个被选中的position
    private boolean isStop = false;   //是否停止自动播放
    private int currentPosition; //当前位置

    //第五步: 设置自动播放,每隔3秒换一张图片
    private final TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (!isStop) {
                LogUtil.d(TAG, "开始自动播放Banner");
                //播放时，主线程更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                    }
                });
            }
        }
    };
    private String imagePath;

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_test_laboratory_code;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_laboratory_code);
        //初始化路由ARouter
        ARouter.getInstance().inject(this);

//        //视图绑定
//        binding = ActivityTestLaboratoryCodeBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        ButterKnife.bind(this);

        ivBaseLeftIcon.setVisibility(View.VISIBLE);
        tvBaseTitle.setVisibility(View.VISIBLE);
        tvBaseTitle.setText("实验室");

        scanSlideSwipeBackLayout.setSlideListener(new SlideEventListener() {
            @Override
            public void leftEvent() {
                finish();
            }

            @Override
            public void rightEvent() {

            }
        });

        //baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        //        baseToolbarLeftIcon.setImageResource(R.drawable.icon_drawer_menu);
        //        baseToolbarTitle.setVisibility(View.VISIBLE);
        //        baseToolbarRightIcon.setVisibility(View.VISIBLE);


        initBanner();


        savePicture();

        //String.format("%1$#9x", -21474xxxxx)  十进制转十六进制
        String format = String.format("%1$#9x", -2147418113);
        System.out.println("kkkkkk1   " + format);

        initAnimatorOpen();

        selectImage();
    }

    private void selectImage() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PictureSelectorUtils.openGallery(TestCodeActivity.this, Constant.REQUEST_CODE_SELECT_USER_ICON, MULTIPLE, TYPE_IMAGE,
//                        true, false, true, 9);
                PictureSelector.create(TestCodeActivity.this)
                        .openGallery(PictureMimeType.ofImage())
//                        .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .compressQuality(60)
                        .isCompress(true)
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .isPreviewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isEnableCrop(false)// 是否裁剪
//                .circleDimmedLayer(true)// 是否圆形裁剪
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
    }

    private void savePicture() {
        String url1 = "http://192.168.137.1:8080/iface/downloadfile?file=C:%5CUsers%5CDESKTOP-20190220%5CDocuments%5CArcvideo+iFaceMini%5CAlarm%5C2019%5C03%5C08%5Cid%280%29_20190308-121053625.jpg";
        String url2 = "http://192.168.137.1:8080/iface/downloadfile?file=C:%5CUsers%5CDESKTOP-20190220%5CDocuments%5CArcvideo+iFaceMini%5CAlarm%5C2019%5C03%5C08%5Cid%280%29_20190308-121041511.jpg";

        new Thread(new Runnable() {
            @Override
            public void run() {
                long millisStart = System.currentTimeMillis();
                Bitmap bitmap = FileUtil.saveImageByUrl(url1);
                Bitmap bitmap1 = FileUtil.saveImageByUrl(url2);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long millisEnd = System.currentTimeMillis();
                        System.out.println("返回结果0：  " + "开始时间：  " + millisStart + "结束时间:  " + millisEnd);
                        System.out.println("返回结果1：  " + bitmap);
                        System.out.println("返回结果2：  " + bitmap1);
                    }
                });
            }
        }).start();
    }

    private void initAnimatorOpen() {
        Button buttonOpen = findViewById(R.id.btn_animator_open);
        Button buttonHide = findViewById(R.id.btn_animator_hide);
        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.pageShowScaleAnimator(TestCodeActivity.this, scrollView);
            }
        });
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.pageHideScaleAnimator(scrollView);
            }
        });
    }

    private void initBanner() {
        //初始化资源
        initData();
        //初始化适配器
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList, stringList);
        //设置至少9个fragment，防止重复创建和销毁，造成内存溢出
        scanViewPager.setOffscreenPageLimit(9);
        //绑定适配器
        scanViewPager.setAdapter(fragmentViewPagerAdapter);
        //TabLayout同步fragment
        scanTabLayout.setupWithViewPager(scanViewPager);

        //Banner ViewPager的Adapter
        //第二步：设置viewpager适配器
        MyBannerPagerAdapter bannerPagerAdapter = new MyBannerPagerAdapter(mImageList, bannerViewPager);
        bannerViewPager.setAdapter(bannerPagerAdapter);
        //第三步：给viewpager设置轮播监听器
        bannerViewPager.addOnPageChangeListener(this);
        //第四步：设置刚打开app时显示的图片和文字
        setFirstLocation();
//        //第五步: 设置自动播放,每隔3秒换一张图片
//        autoPlayView();
        mTimer.schedule(mTimerTask, 3000, 3000);
        //第七步：设置ViewPager的触摸事件，触摸停止自动播放Banner
        bannerViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    //按下或移动手指,停止自动播放
                    isStop = true;
                    LogUtil.d(TAG, "触摸状态，停止自动播放Banner");
                } else if (action == MotionEvent.ACTION_UP) {
                    //抬起触摸，开启自动播放
                    isStop = false;
                    LogUtil.d(TAG, "触摸抬起，开启自动播放Banner");
                }
                return false;
            }
        });

        //第九步：ViewPager Banner的点击事件
        //回调相当于把对象new MyBannerPagerAdapter.ViewPagerClickInterFace()对象传给MyBannerPagerAdapter中viewPagerClickInterFace对象
        //viewPagerClickInterFace.setClick(position);调的就是viewPagerClickInterFace对象调用重写后的方法
        bannerPagerAdapter.setViewPagerClickInterFace(new MyBannerPagerAdapter.ViewPagerClickInterFace() {
            @Override
            public void onClick(int position) {
                WebViewDetailActivity.actionStart(TestCodeActivity.this, mBnanerDesacList.get(position), mBannerDetailUrl.get(position), "https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
//                case Constant.REQUEST_CODE_SELECT_USER_ICON:
//                    List<LocalMedia> localMedia = PictureSelectorUtils.forResult(resultCode, data);
//                    if (localMedia != null) {
//                        LocalMedia media = localMedia.get(0);
//                        if (media.isCut() && !media.isCompressed()) {
//                            //裁剪未压缩
//                            imagePath = media.getCutPath();
//                            long cutImageSize = new File(media.getCutPath()).length() / 1024;
//                            LogUtil.d(TAG, "裁剪地址: ---> 大小:  " + cutImageSize + "k" + "   裁剪地址:" + media.getCutPath());
//                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//                            //压缩过，或者裁剪同时压缩过，以最终压缩过的图片为准
//                            imagePath = media.getCompressPath();
//                            long compressImageSize = new File(media.getCompressPath()).length() / 1024;
//                            LogUtil.d(TAG, "压缩地址: ---> 大小:  " + compressImageSize + "k" + "   压缩地址:" + media.getCutPath());
//                        } else {
//                            //原图
//                            imagePath = media.getPath();
//                            long imageSize = new File(media.getPath()).length() / 1024;
//                            LogUtil.d(TAG, "原图地址: ---> 大小:  " + imageSize + "k" + "   原图地址:" + media.getPath());
//                        }
//                        //UI
//                        if (!imagePath.isEmpty()) {
//                            Glide.with(TestCodeActivity.this).asBitmap().load(imagePath).into(ivIcon);
//
//                            RequestOptions options = new RequestOptions()
//                                    .transform(new BlurTransformation(50));
//                            Glide.with(TestCodeActivity.this).asBitmap().apply(options).load(imagePath).into(ivBg);
//                        }
//                    }
//                    break;

                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    if (!CollectionUtils.isEmpty(selectList)) {
                    if (selectList != null && selectList.size() > 0) {
                        String path;
                        LocalMedia localMedia = selectList.get(0);
                        if (localMedia.isCut() && !localMedia.isCompressed()) {
                            // 裁剪过
                            path = localMedia.getCutPath();
                        } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            path = localMedia.getCompressPath();
                        } else {
                            // 原图  localMedia.getPath()
                            path = FileUtil.getRealPath(TestCodeActivity.this, localMedia.getPath());
//                            path = getRealPath(TestCodeActivity.this, localMedia.getPath());

                        }
                        //UI
                        if (!path.isEmpty()) {
                            Glide.with(TestCodeActivity.this).asBitmap().load(path).into(ivIcon);

                            RequestOptions options = new RequestOptions()
                                    .transform(new BlurTransformation(50));
                            Glide.with(TestCodeActivity.this).asBitmap().apply(options).load(path).into(ivBg);
                        }
                        break;
                    }
            }
        }
    }

    // 存在沙盒中的path不能直接使用new File(path)形式生成文件 ，可以通过以下方式转换下
    public static String getRealPath(Context context, String path) {
        if (PictureMimeType.isContent(path)) {
            Uri uri = Uri.parse(path);
            try {
                File imgFile = context.getExternalFilesDir("image");
                if (!imgFile.exists()) {
                    imgFile.mkdir();
                }
                try {
                    File file = new File(imgFile.getAbsolutePath() + File.separator +
                            System.currentTimeMillis() + ".jpg");
                    // 使用openInputStream(uri)方法获取字节输入流
                    InputStream fileInputStream = context.getContentResolver().openInputStream(uri);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while (-1 != (byteRead = fileInputStream.read(buffer))) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    // 文件可用新路径 file.getAbsolutePath()
                    path = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 定义banner的滚动点
     */
    private void initDots() {
        ImageView[] dots = new ImageView[llIndicatorDot.getChildCount()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = (ImageView) llIndicatorDot.getChildAt(i);
            //让ImageView有效
            dots[i].setEnabled(true);
            dots[i].setTag(i);
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    currentPosition = position;
                    dots[currentPosition].setEnabled(false);
                    //设置当前页面
                    bannerViewPager.setCurrentItem(currentPosition);
                }
            });
        }
        dots[0].setEnabled(false);
    }

    /**
     * 第五步: 设置自动播放,每隔3秒换一张图片
     */
    private void autoPlayView() {
        //开启子线程模拟休眠耗时操作，自动播放图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    LogUtil.d(TAG, "开始自动播放Banner");
                    //播放时，主线程更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                        }
                    });
                    SystemClock.sleep(3000);
                }
            }
        }).start();
    }

    /**
     * 第三步：设置刚打开app时显示的图片和文字
     */
    private void setFirstLocation() {
//        tvImageDesc.setText(imageDescs[previousPosition]);
        tvImageDesc.setText(mBnanerDesacList.get(previousPosition));
        // *把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) % mImageList.size();
        currentPosition = Integer.MAX_VALUE / 2 - m;
        //设置ViewPager图片当前位置
        bannerViewPager.setCurrentItem(currentPosition);
    }

    private void initData() {
        stringList.add("头条");
        stringList.add("国内");
        stringList.add("国际");
        stringList.add("视频");
        stringList.add("时尚");
        stringList.add("明星");
        stringList.add("科技");

        fragmentList.add(JuheNewsTabFragment.newInstance(null));
        fragmentList.add(JuheNewsTabFragment.newInstance(null));
        fragmentList.add(JuheNewsTabFragment.newInstance(null));
        fragmentList.add(JuheNewsTabFragment.newInstance(null));
        fragmentList.add(JuheNewsTabFragment.newInstance(null));
        fragmentList.add(JuheNewsTabFragment.newInstance(null));
        fragmentList.add(JuheNewsTabFragment.newInstance(null));

        //第一步：初始化数据
        int[] imageResIDs = {
                R.drawable.picture_banner_one,
                R.drawable.picture_banner_two,
                R.drawable.picture_banner_three,
                R.drawable.picture_banner_four,
                R.drawable.picture_banner_five,
        };

        ImageView iv;
        for (int i = 0; i < imageResIDs.length; i++) {
            iv = new ImageView(this);
            iv.setBackgroundResource(imageResIDs[i]);
            mImageList.add(iv);

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
        imageDescs = new String[]{
//                "1  今年二十七八岁，我最喜欢的事就是睡觉",
                "1今年",
                "2  懒虫，起床了，要上班了",
                "3  我翻了个身，想着如果今天是周末多好",
                "4  终于爬起来，坐在沙发上一脸懵逼",
                "5  一个人早餐就随便吃点"
        };
        mBnanerDesacList.addAll(Arrays.asList(imageDescs));

        //mBannerDetailUrl
        //Viewpager Banner ImageView点击跳转详情页Url列表
        mBannerDetailUrl.add("https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247484939&idx=1&sn=d1871b09de55ca681da6ec2432a63ee1&chksm=96cda746a1ba2e501605dbb64b32f82b2cb44ad9c6f9ba2308a9321231b2782e6ba679ef31a6&mpshare=1&scene=23&srcid=0614bARC7g4dwQ3qbIVEJ29b#rd");
        mBannerDetailUrl.add("https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90");
        mBannerDetailUrl.add("https://www.jianshu.com/p/dd3799363318");
        mBannerDetailUrl.add("https://www.jianshu.com/p/e7dc17fcbbc6");
        mBannerDetailUrl.add("https://blog.csdn.net/a_yue10/article/details/78691288");
    }

    /**
     * 第三步：给viewpager设置轮播监听器
     * viewpager的监听器
     * 当ViewPager页面被选中时, 触发此方法.
     *
     * @param position 当前被选中的页面的索引
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //伪无限循环，滑到最后一张图片又从新进入第一张图片
        int newPosition = position % mImageList.size();

        //把当前选中的点给切换了, 还有描述信息也切换
        //图片下面设置显示文本
//        tvImageDesc.setText(imageDescs[newPosition]);
        tvImageDesc.setText(mBnanerDesacList.get(newPosition));

        //第八步：定义banner的滚动点
        ImageView[] dots = new ImageView[llIndicatorDot.getChildCount()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = (ImageView) llIndicatorDot.getChildAt(i);
            //让ImageView有效
            dots[i].setEnabled(true);
            dots[i].setTag(i);
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    currentPosition = position;
                    //设置当前页面
                    bannerViewPager.setCurrentItem(currentPosition);
                }
            });
        }
        dots[newPosition].setEnabled(false);

        //把当前的索引赋值给前一个索引变量，方便下一次再切换
        previousPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 第六步: 当Activity销毁时取消图片自动播放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止自动播放
        isStop = true;
        mTimer.cancel();
    }

    @OnClick({R.id.btn_filtrate_jingdong, R.id.btn_map_view, R.id.btn_camera, R.id.btn_aidl, R.id.btn_modularization_router, R.id.base_actionbar_left_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_filtrate_jingdong:
                FiltrateActivity.actionStart(TestCodeActivity.this);
                break;

            case R.id.btn_map_view:
                MapTestActivity.actionStart(TestCodeActivity.this);
                break;

            case R.id.btn_camera:
                startActivity(new Intent(TestCodeActivity.this, CameraActivity.class));
                break;

            case R.id.btn_aidl:
                ServiceActivity.actionStart(TestCodeActivity.this);
                break;

            case R.id.btn_modularization_router:
                ServiceProvider.getDatePickerService().startDatePickerDemoActivity(TestCodeActivity.this);
                break;

            case R.id.base_actionbar_left_icon:
                finish();
                break;

            default:
                break;
        }
    }
}
