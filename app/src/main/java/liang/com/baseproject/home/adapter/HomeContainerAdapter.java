package liang.com.baseproject.home.adapter;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.entity.TagsBean;
import liang.com.baseproject.home.entity.ArticleBean;
import liang.com.baseproject.widget.CollectView;

/**
 * 创建日期：2019/8/30 on 14:19
 * 描述: 首页-推荐-文章列表recyclerView适配器(基于BaseQuickAdapter) extends BaseQuickAdapter<ArticleBean, CommonViewHolder>
 * 作者: liangyang
 */
//public class HomeContainerAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
public class HomeContainerAdapter extends BaseQuickAdapter<ArticleBean, HomeContainerAdapter.MyViewHolder> {

    public HomeContainerAdapter() {
        super(R.layout.rv_article_list_item);
    }

    private OnCollectViewClickListener mOnCollectViewClickListener = null;


    public interface OnCollectViewClickListener {
        void onClick(BaseViewHolder helper, CollectView collectView, int position);
    }

    public void setmOnCollectViewClickListener(OnCollectViewClickListener mOnCollectViewClickListener) {
        this.mOnCollectViewClickListener = mOnCollectViewClickListener;
    }

    @Override
    protected void convert(@NonNull MyViewHolder helper, ArticleBean item) {

        helper.tvDesc.setVisibility(View.GONE);
        //文章Title
        helper.tvTitle.setText(item.getTitle());
        //作者
        if (!TextUtils.isEmpty(item.getAuthor()) && TextUtils.isEmpty(item.getShareUser())){
            helper.tvAuthor.setText(item.getAuthor());
        }else if (TextUtils.isEmpty(item.getAuthor()) && !TextUtils.isEmpty(item.getShareUser())){
            helper.tvAuthor.setText(item.getShareUser());
        }
        //时间
        helper.tvTime.setText(item.getNiceDate());
        //一级分类
        helper.tvSuperChapterName.setText(item.getSuperChapterName());
        //二级分类
        helper.tvChapterName.setText(item.getChapterName());
        //标志
        if (item.isFresh()) {
            helper.llNew.setVisibility(View.VISIBLE);
        } else {
            helper.llNew.setVisibility(View.GONE);
        }
        //图片显示
        String envelopePic = item.getEnvelopePic();
        if (TextUtils.isEmpty(envelopePic)) {
            helper.ivImg.setVisibility(View.GONE);
        } else {
            helper.ivImg.setVisibility(View.VISIBLE);
            Glide.with(MyApplication.getAppContext()).asBitmap().load(envelopePic).into(helper.ivImg);
        }
        //Tag
        List<TagsBean> tags = item.getTags();
        if (tags != null && tags.size() > 0) {
            if (tags.size() > 1) {
                helper.tvTag.setText(tags.get(0).getName() + "...");
            } else {
                helper.tvTag.setText(tags.get(0).getName());
            }
        } else {
            helper.tvTag.setVisibility(View.GONE);
        }
        //标记收藏
        if (item.isCollect()) {
            helper.cvCollect.setChecked(true);
        } else {
            helper.cvCollect.setChecked(false);
        }
        //标记收藏点击回调事件
        helper.cvCollect.setOnClickListener(new CollectView.OnClickListener() {
            @Override
            public void onClick(CollectView v) {
                if (mOnCollectViewClickListener != null) {
                    mOnCollectViewClickListener.onClick(helper, helper.cvCollect, helper.getAdapterPosition() - getHeaderLayoutCount());
                }
            }
        });
    }

//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, ArticleBean item) {
//
//        helper.getView(R.id.tv_desc).setVisibility(View.GONE);
////        helper.setText(R.id.tv_desc,item.getDesc());
//        //文章Title
////        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
//        helper.setText(R.id.tv_title, item.getTitle());
////        TextView tvTitle = helper.getView(R.id.tv_title);
////        tvTitle.setSingleLine(true);
//
//        //作者
//        helper.setText(R.id.tv_author, item.getAuthor());
//        //时间
//        helper.setText(R.id.tv_time, item.getNiceDate());
//        //一级分类
//        helper.setText(R.id.tv_super_chapter_name, item.getSuperChapterName());
//        //二级分类
//        helper.setText(R.id.tv_chapter_name, item.getChapterName());
//        //标志
//        LinearLayout llNew = helper.getView(R.id.ll_new);
//        if (item.isFresh()) {
//            llNew.setVisibility(View.VISIBLE);
//        } else {
//            llNew.setVisibility(View.GONE);
//        }
//        //图片显示
//        ImageView imageView = helper.getView(R.id.iv_img);
//        String envelopePic = item.getEnvelopePic();
//        if (TextUtils.isEmpty(envelopePic)) {
//            imageView.setVisibility(View.GONE);
//        } else {
//            imageView.setVisibility(View.VISIBLE);
////            ImageLoaderUtils.loadRadiusImage(MyApplication.getAppContext(), false, imageView, envelopePic,
////                    R.drawable.image_holder, R.drawable.image_holder, 3);
//            Glide.with(MyApplication.getAppContext()).asBitmap().load(envelopePic).into(imageView);
//        }
//        //Tag
//        TextView tvTag = helper.getView(R.id.tv_tag);
//        List<TagsBean> tags = item.getTags();
//        if (tags != null && tags.size() > 0) {
//            tvTag.setVisibility(View.VISIBLE);
//            if (tags.size() > 1) {
//                tvTag.setText(tags.get(0).getName() + "...");
//            } else {
//                tvTag.setText(tags.get(0).getName());
//            }
//        } else {
//            tvTag.setVisibility(View.GONE);
//        }
//        //标记收藏
//        CollectView collectView = helper.getView(R.id.cv_collect);
//        if (item.isCollect()) {
//            collectView.setChecked(true);
//        } else {
//            collectView.setChecked(false);
//        }
//        //标记收藏点击回调事件
//        collectView.setOnClickListener(new CollectView.OnClickListener() {
//            @Override
//            public void onClick(CollectView v) {
//                if (mOnCollectViewClickListener != null) {
//                    mOnCollectViewClickListener.onClick(helper, collectView, helper.getAdapterPosition() - getHeaderLayoutCount());
//                }
//            }
//        });
//    }

    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.ll_new)
        LinearLayout llNew;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_top)
        RelativeLayout rlTop;
        @BindView(R.id.iv_img)
        RoundedImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.ll_middle)
        LinearLayout llMiddle;
        @BindView(R.id.tv_super_chapter_name)
        TextView tvSuperChapterName;
        @BindView(R.id.fl_dot)
        FrameLayout flDot;
        @BindView(R.id.tv_chapter_name)
        TextView tvChapterName;
        @BindView(R.id.ll_chapter)
        LinearLayout llChapter;
        @BindView(R.id.cv_collect)
        CollectView cvCollect;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
