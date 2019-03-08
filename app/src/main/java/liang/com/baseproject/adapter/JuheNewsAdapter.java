package liang.com.baseproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.WebViewDetailActivity;
import liang.com.baseproject.activity.SinglePictureActivity;
import liang.com.baseproject.activity.ViewPagerPictureActivity;
import liang.com.baseproject.entity.NewsRes;
import liang.com.baseproject.utils.ImageLoaderUtils;

public class JuheNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NewsRes.ResultBean.DataBean> dataList;

    public JuheNewsAdapter(Context context, List<NewsRes.ResultBean.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_rv_news, null);
        return new JuheNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof JuheNewsViewHolder) {
            JuheNewsViewHolder juheNewsViewHolder = (JuheNewsViewHolder) viewHolder;
            juheNewsViewHolder.bindItem(dataList.get(position), position);
        } else if (viewHolder instanceof EmptyViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * type = Empty
     */
    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class JuheNewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_news_title)
        TextView tvNewsTitle;
        @BindView(R.id.iv_news_author)
        ImageView ivNewsAuthor;
        @BindView(R.id.tv_news_author)
        TextView tvNewsAuthor;
        @BindView(R.id.iv_news_time)
        ImageView ivNewsTime;
        @BindView(R.id.tv_news_time)
        TextView tvNewsTime;
        @BindView(R.id.iv_news_img)
        ImageView ivNewsImg;
        @BindView(R.id.card_stories)
        CardView cardStories;

        public JuheNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(NewsRes.ResultBean.DataBean dataBean, int position) {
            tvNewsTitle.setText(dataBean.getTitle());
            tvNewsTime.setText(dataBean.getDate());
            tvNewsAuthor.setText(dataBean.getAuthor_name());
            Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(ivNewsImg);
            ImageLoaderUtils.loadImage(context, true, ivNewsImg, dataBean.getThumbnail_pic_s(), 0, 0, 10);


//            //点击图片跳转图片Activity -> SinglePictureActivity  a%2 != 0
            ivNewsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (position % 2 != 0) {
//                        //奇数Item
//                        SinglePictureActivity.actionStart(context, dataBean.getThumbnail_pic_s(), dataBean.getDate());
//                    } else {
//                        //偶数Item
//                        List<String> imageUrlList = new ArrayList<>();
//                        String thumbnail_pic_s = dataBean.getThumbnail_pic_s();
//                        String thumbnail_pic_s02 = dataBean.getThumbnail_pic_s02();
//                        String thumbnail_pic_s03 = dataBean.getThumbnail_pic_s03();
//                        imageUrlList.add(thumbnail_pic_s);
//                        imageUrlList.add(thumbnail_pic_s02);
//                        imageUrlList.add(thumbnail_pic_s03);
//                        ViewPagerPictureActivity.actionStart(context, imageUrlList);
//                    }

                    String thumbnail_pic_s = dataBean.getThumbnail_pic_s();
                    String thumbnail_pic_s02 = dataBean.getThumbnail_pic_s02();
                    String thumbnail_pic_s03 = dataBean.getThumbnail_pic_s03();
                    if (thumbnail_pic_s != null && thumbnail_pic_s02 != null && thumbnail_pic_s03 != null) {
                        //跳转到ViewPager多张大图显示界面
                        List<String> imageUrlList = new ArrayList<>();
                        imageUrlList.add(thumbnail_pic_s);
                        imageUrlList.add(thumbnail_pic_s02);
                        imageUrlList.add(thumbnail_pic_s03);
                        ViewPagerPictureActivity.actionStart(context, imageUrlList);
                    } else {
                        //不是全部有数据的时候跳转到单张大图显示界面
                        SinglePictureActivity.actionStart(context, dataBean.getThumbnail_pic_s(), dataBean.getDate());
                    }
                    //渐变式切换Activity效果
                    ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            //点击CardView跳转到WebView新闻详情页
            cardStories.setOnClickListener(v -> WebViewDetailActivity.actionStart(context, dataBean.getTitle(), dataBean.getUrl(), dataBean.getThumbnail_pic_s()));
        }
    }

//public class JuheNewsAdapter extends BaseRecycleAdapter<NewsRes.ResultBean.DataBean> {

//    private Context mContext;
//    private BaseViewHolder.onItemBaseClickListener baseClickListener;
//
//    public JuheNewsAdapter(Context context, List<NewsRes.ResultBean.DataBean> dataList) {
//        super(context, dataList, R.layout.item_rv_news);
//        this.mContext = context;
//    }
//
//    @Override
//    public void bindData(BaseViewHolder holder, NewsRes.ResultBean.DataBean data) {
//        super.bindData(holder, data);
//        if (data != null) {
//            holder.setText(R.id.tv_news_title, data.getTitle())
//                    .setText(R.id.tv_news_author, data.getAuthor_name())
//                    .setText(R.id.tv_news_time, data.getDate())
//                    .setImageUrl(mContext, R.id.iv_news_img, data.getThumbnail_pic_s())
//                    .setCommonClickListener(baseClickListener);
//
//            //点击事件
//            holder.setCommonClickListener(new BaseViewHolder.onItemBaseClickListener() {
//                @Override
//                public void onItemClickListener(int position) {
////                    WebViewDetailActivity.actionStart(mContext, data.getTitle(), data.getUrl());
//                    Intent intent = new Intent(mContext, WebViewDetailActivity.class);
//                    intent.putExtra("title", data.getTitle());
//                    intent.putExtra("url", data.getUrl());
//                    mContext.getApplicationContext().startActivity(intent);
//                    LogUtil.d("JuheNewsAdapter", "点击JuheNewsAdapter");
//                    System.out.println("JuheNewsAdapter" + "点击JuheNewsAdapter");
//                }
//
//                @Override
//                public void onItemLongClickListener(int position) {
//
//                }
//            });
//        }
//    }

}
