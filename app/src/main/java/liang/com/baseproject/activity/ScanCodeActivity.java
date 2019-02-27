package liang.com.baseproject.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.adapter.MyBannerPagerAdapter;
import liang.com.baseproject.fragment.OneFragment;

public class ScanCodeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

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

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private List<ImageView> mImageList = new ArrayList<>();
    private String[] imageDescs;
    private int previousPosition = 0; // 前一个被选中的position
    private boolean isStop = false;   //是否停止自动播放
    private int currentPosition; //当前位置


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
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

        //MyPagerAdapter mAdapter = new MyPagerAdapter(mImageList, mViewPager);
        //        mViewPager.setAdapter(mAdapter);
        //        mViewPager.addOnPageChangeListener(this);
        //        setFirstLocation();
        //Banner ViewPager的Adapter
        //第二步：设置viewpager适配器
        MyBannerPagerAdapter bannerPagerAdapter = new MyBannerPagerAdapter(mImageList, bannerViewPager);
        bannerViewPager.setAdapter(bannerPagerAdapter);
        //第三步：给viewpager设置轮播监听器
        bannerViewPager.addOnPageChangeListener(this);
        //第四步：设置刚打开app时显示的图片和文字
        setFirstLocation();
        //第五步: 设置自动播放,每隔3秒换一张图片
        autoPlayView();
        //第七部：定义banner的滚动点
        initDots();


    }

    /**
     * 定义banner的滚动点
     */
    private void initDots() {
        //llIndicatorDot

        //dots = new ImageView[mLinearLayout.getChildCount()];
        //        for (int i = 0; i < dots.length; i++) {
        //            dots[i] = (ImageView) mLinearLayout.getChildAt(i);
        //            //让ImageView有效
        //            dots[i].setEnabled(true);
        //            dots[i].setTag(i);
        //            dots[i].setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    int position = (int) v.getTag();
        //                    index = position;
        //
        //                    setDotsEnable(position);
        //
        //                    //设置当前页面
        //                    mViewPager.setCurrentItem(position);
        //                }
        //            });
        //        }
        //        dots[0].setEnabled(false);
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



                }
            });
        }
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
                    //停止播放时，主线程更新UI
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
        tvImageDesc.setText(imageDescs[previousPosition]);
        // *把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) % mImageList.size();
        currentPosition = Integer.MAX_VALUE / 2 - m;
        //设置ViewPager图片当前位置
        bannerViewPager.setCurrentItem(currentPosition - 1);
    }

    private void initData() {
        stringList.add("头条");
        stringList.add("国内");
        stringList.add("国际");
        stringList.add("视频");
        stringList.add("时尚");
        stringList.add("明星");
        stringList.add("科技");

        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));

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

            //初始化显示每张图片下面现实的文字
            imageDescs = new String[]{
                    "1  今年二十七八岁，我最喜欢的事就是睡觉",
                    "2  懒虫，起床了，要上班了",
                    "3  我翻了个身，想着如果今天是周末多好",
                    "4  终于爬起来，坐在沙发上一脸懵逼",
                    "5  一个人早餐就随便吃点"
            };
        }


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
        ////图片下面设置显示文本
        tvImageDesc.setText(imageDescs[newPosition]);
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
    }
}
