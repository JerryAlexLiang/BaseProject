package liang.com.baseproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.SinglePictureActivity;
import liang.com.baseproject.entity.ZhihuLastNewsRes;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ScreenUtil;

/**
 * 创建日期：2019/3/8 on 9:36
 * 描述: 知乎日报RecyclerView适配器   extends RecyclerView.Adapter<RecyclerView.ViewHolder>
 * 作者: liangyang
 */
public class ZhihuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
//        if (viewType == TYPE_TOP) {
//            View headerView = View.inflate(parent.getContext(), R.layout.layout_view_pager_banner, null);
//            return new HeaderViewHolder(headerView);
//        } else
            if (viewType == TYPE_FOOTER) {
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
            storiesViewHolder.bindItem(zhihuLastNewsRes.getStories().get(position));
//            storiesViewHolder.bindItem(zhihuLastNewsRes.getStories().get(position - 1)); //添加头部时
        } else
            if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        }
//        else if (holder instanceof HeaderViewHolder) {
//            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
//        }
    }

    @Override
    public int getItemCount() {
//        return zhihuLastNewsRes.getStories().size() + 2;
        return zhihuLastNewsRes.getStories().size() + 1;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

        }
    }

    /**
     * 头部shitu
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder{

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

    /**
     * 清空数据
     */
    public void clearData(){
        zhihuLastNewsRes.getStories().clear();
        notifyDataSetChanged();
    }

}
