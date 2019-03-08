package liang.com.baseproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.ScanCodeActivity;
import liang.com.baseproject.activity.SinglePictureActivity;
import liang.com.baseproject.activity.WebViewDetailActivity;
import liang.com.baseproject.base.BaseRVAdapter;
import liang.com.baseproject.entity.ZhihuLastNewsRes;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ScreenUtil;

/**
 * 创建日期：2019/3/8 on 9:36
 * 描述: 知乎日报RecyclerView适配器   extends RecyclerView.Adapter<RecyclerView.ViewHolder>
 * 作者: liangyang
 */
public class ZhihuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Viewpager Banner ImageView列表
    private List<ImageView> mImageList = new ArrayList<>();
    //Viewpager Banner ImageView中显示文字内容列表列表
    private List<String> mBnanerDesacList = new ArrayList<>();
    //Viewpager Banner ImageView点击跳转详情页Url列表
    private List<Integer> mBannerDetailUrlList = new ArrayList<>();

    private Timer mTimer = new Timer();
    private int previousPosition = 0; // 前一个被选中的position
    private boolean isStop = false;   //是否停止自动播放
    private int currentPosition; //当前位置

    private Context context;
    private ZhihuLastNewsRes zhihuLastNewsRes;
    private int status = 1; //底部FooterView状态

    public static final int LOAD_MORE = 0;    //底部视图刷新方式-加载更多
    public static final int LOAD_PULL_TO = 1; //底部视图刷新方式-下拉刷新
    public static final int LOAD_NONE = 2;    //底部视图刷新方式-无
    public static final int LOAD_END = 3;

    private static final int TYPE_TOP = -1;
    private static final int TYPE_FOOTER = -2;

    public ZhihuListAdapter(Context context, ZhihuLastNewsRes zhihuLastNewsRes) {
        this.context = context;
        this.zhihuLastNewsRes = zhihuLastNewsRes;
    }

    @Override
    public int getItemViewType(int position) {
        if (zhihuLastNewsRes.getTop_stories() != null) {
            //有头部布局
            if (position == 0) {
                //头部布局
                return TYPE_TOP;
            } else if (position + 1 == getItemCount()) {
                //底部布局
                return TYPE_FOOTER;
            } else {
                return position;
            }
        } else if (position + 1 == getItemCount()) {
            //没有头部布局，底部布局
            return TYPE_FOOTER;
        } else {
            //没有头部布局
            return position;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            View headerView = View.inflate(parent.getContext(), R.layout.layout_view_pager_banner, null);
            return new HeaderViewHolder(headerView);
        } else if (viewType == TYPE_FOOTER) {
            View footerView = View.inflate(parent.getContext(), R.layout.item_recycler_view_footer, null);
            return new FooterViewHolder(footerView);
        } else {
            View rootView = View.inflate(parent.getContext(), R.layout.item_rv_zhihu_stories, null);
            return new StoriesViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StoriesViewHolder) {
            StoriesViewHolder storiesViewHolder = (StoriesViewHolder) holder;
//            storiesViewHolder.bindItem(zhihuLastNewsRes.getStories().get(position));
            storiesViewHolder.bindItem(zhihuLastNewsRes.getStories().get(position - 1)); //添加头部时
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof BaseRVAdapter.HeaderViewHolder) {
            BaseRVAdapter.HeaderViewHolder headerViewHolder = (BaseRVAdapter.HeaderViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return zhihuLastNewsRes.getStories().size() + 2;
    }

//    class TopStoriesViewHolder extends RecyclerView.ViewHolder {
//
//            @Bind(R.id.vp_top_stories)
//            TopStoriesViewPager vp_top_stories;
//            @Bind(R.id.tv_top_title)
//            TextView tv_top_title;
//
//            public TopStoriesViewHolder(View itemView) {
//                super(itemView);
//                ButterKnife.bind(this, itemView);
//
//            }
//
//            public void bindItem(List<TopStories> topList) {
//                vp_top_stories.init(topList, tv_top_title, item -> {
//                    context.startActivity(ZhihuWebActivity.newIntent(context, item.getId()));
//                });
//            }
//        }

    /**
     * 头部shitu
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder implements ViewPager.OnPageChangeListener {

        @BindView(R.id.banner_view_pager)
        ViewPager bannerViewPager;
        @BindView(R.id.ll_indicator_dot)
        LinearLayout llIndicatorDot;
        @BindView(R.id.tv_image_desc)
        TextView tvImageDesc;
        @BindView(R.id.ll_banner_image_desc)
        LinearLayout llBannerImageDesc;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("ClickableViewAccessibility")
        public void bindItem(List<ZhihuLastNewsRes.TopStoriesBean> topStoriesBeanList) {
            //Banner ViewPager的Adapter
            //第一步：初始化数据
            ImageView iv;
            for (int i = 0; i < topStoriesBeanList.size(); i++) {
                iv = new ImageView(context);
                Glide.with(context).load(topStoriesBeanList.get(i).getImage()).into(iv);
                mImageList.add(iv);
                //初始化显示每张图片下面显示的文字
                mBnanerDesacList.add(topStoriesBeanList.get(i).getTitle());
                mBannerDetailUrlList.add(topStoriesBeanList.get(i).getId());
            }

            //第二步：设置viewpager适配器
            MyBannerPagerAdapter bannerPagerAdapter = new MyBannerPagerAdapter(mImageList, bannerViewPager);
            bannerViewPager.setAdapter(bannerPagerAdapter);
            //第三步：给viewpager设置轮播监听器
            bannerViewPager.addOnPageChangeListener(this);
            //第四步：设置刚打开app时显示的图片和文字
            setFirstLocation();
            //第五步: 设置自动播放,每隔3秒换一张图片
            mTimer.schedule(mTimerTask, 3000, 3000);
            //第七步：设置ViewPager的触摸事件，触摸停止自动播放Banner
            bannerViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                        //按下或移动手指,停止自动播放
                        isStop = true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        //抬起触摸，开启自动播放
                        isStop = false;
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
//                Toast.makeText(ScanCodeActivity.this, "点击第 " + (position + 1) + " 个广告栏   当前内容为： " + imageDescs[position], Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "点击第 " + (position + 1) +
                            " 个广告栏   当前内容为： " + mBnanerDesacList.get(position)
                            + "跳转Url: " + mBannerDetailUrlList.get(position), Toast.LENGTH_SHORT).show();
                    WebViewDetailActivity.actionStart(context, mBnanerDesacList.get(position), "", "https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg");
                }
            });
        }

        //第五步: 设置自动播放,每隔3秒换一张图片
        private TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (!isStop) {
                    //播放时，主线程更新UI
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
//                        }
//                    });
                    new Handler().post(() -> bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1));
                }
            }
        };


        private void setFirstLocation() {
            tvImageDesc.setText(mBnanerDesacList.get(previousPosition));
            // *把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
            int m = (Integer.MAX_VALUE / 2) % mImageList.size();
            currentPosition = Integer.MAX_VALUE / 2 - m;
            //设置ViewPager图片当前位置
            bannerViewPager.setCurrentItem(currentPosition);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //伪无限循环，滑到最后一张图片又从新进入第一张图片
            int newPosition = position % mImageList.size();
            //把当前选中的点给切换了, 还有描述信息也切换
            //图片下面设置显示文本
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
        public void onPageScrollStateChanged(int i) {

        }
    }

    /**
     * footer view
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress)
        ProgressBar progress;
        @BindView(R.id.tv_load_prompt)
        TextView tvLoadPrompt;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //视图居中
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.instance(context).dip2px(40));
            itemView.setLayoutParams(params);
        }

        public void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    //加载更多中
                    progress.setVisibility(View.VISIBLE);
                    tvLoadPrompt.setText("正在加载中...");
                    itemView.setVisibility(View.VISIBLE);
                    break;

                case LOAD_PULL_TO:
                    //上拉加载更多状态(向上划)
                    progress.setVisibility(View.GONE);
                    tvLoadPrompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;

                case LOAD_NONE:
                    //加载到最底部
                    LogUtil.d("知乎日报列表: ", "加载完全部数据");
                    progress.setVisibility(View.GONE);
                    tvLoadPrompt.setText("我也是有底线的...");
                    break;

                case LOAD_END:
                    //加载完成
                    itemView.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 日报列表
     */
    class StoriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_stories_title)
        TextView tvStoriesTitle;
        @BindView(R.id.iv_stories_img)
        ImageView ivStoriesImg;
        @BindView(R.id.card_stories)
        CardView cardStories;

        public StoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //ScreenUtil screenUtil = ScreenUtil.instance(context);
            //int screenWidth = screenUtil.getScreenWidth();
            //cardStories.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        public void bindItem(ZhihuLastNewsRes.StoriesBean storiesBean) {
            tvStoriesTitle.setText(storiesBean.getTitle());
            List<String> images = storiesBean.getImages();
//            ImageLoaderUtils.loadImage(context, false, ivStoriesImg, images.get(0), 0, 0, 10);
            Glide.with(context).load(images.get(0)).into(ivStoriesImg);

            //点击图片跳转图片Activity -> SinglePictureActivity
            ivStoriesImg.setOnClickListener(v -> {
                SinglePictureActivity.actionStart(context, images.get(0), storiesBean.getTitle());
            });

            //点击CardView跳转到WebView新闻详情页
            //card_stories.setOnClickListener(v -> context.startActivity(ZhihuWebActivity.newIntent(context,stories.getId())));
        }
    }

    //更新适配器
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

}
