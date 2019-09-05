package liang.com.baseproject.home.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.home.entity.ArticleBean;
import liang.com.baseproject.utils.ImageLoaderUtils;
import liang.com.baseproject.widget.CollectView;

/**
 * 创建日期：2019/8/30 on 14:19
 * 描述: 首页-推荐-文章列表recyclerView适配器(基于BaseQuickAdapter) extends BaseQuickAdapter<ArticleBean, CommonViewHolder>
 * 作者: liangyang
 */
public class HomeContainerAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

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
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean item) {
        //文章Title
//        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_title, item.getTitle());
        //作者
        helper.setText(R.id.tv_author, item.getAuthor());
        //时间
        helper.setText(R.id.tv_time, item.getNiceDate());
        //一级分类
        helper.setText(R.id.tv_super_chapter_name, item.getSuperChapterName());
        //二级分类
        helper.setText(R.id.tv_chapter_name, item.getChapterName());
        //标志
        LinearLayout llNew = helper.getView(R.id.ll_new);
        if (item.isFresh()) {
            llNew.setVisibility(View.VISIBLE);
        } else {
            llNew.setVisibility(View.GONE);
        }
        //图片显示
        ImageView imageView = helper.getView(R.id.iv_img);
        String envelopePic = item.getEnvelopePic();
        if (TextUtils.isEmpty(envelopePic)) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            ImageLoaderUtils.loadRadiusImage(MyApplication.getAppContext(), false, imageView, envelopePic,
                    R.drawable.image_holder, R.drawable.image_holder, 0);
        }
        //Tag
        TextView tvTag = helper.getView(R.id.tv_tag);
        List<ArticleBean.TagsBean> tags = item.getTags();
        if (tags != null && tags.size() > 0) {
            tvTag.setVisibility(View.VISIBLE);
            if (tags.size() > 1) {
                tvTag.setText(tags.get(0).getName() + "...");
            } else {
                tvTag.setText(tags.get(0).getName());
            }
        } else {
            tvTag.setVisibility(View.GONE);
        }
        //标记收藏
        CollectView collectView = helper.getView(R.id.cv_collect);
        if (item.isCollect()) {
            collectView.setChecked(true);
        } else {
            collectView.setChecked(false);
        }
        //标记收藏点击回调事件
        collectView.setOnClickListener(new CollectView.OnClickListener() {
            @Override
            public void onClick(CollectView v) {
                if (mOnCollectViewClickListener != null) {
                    mOnCollectViewClickListener.onClick(helper, collectView, helper.getAdapterPosition() - getHeaderLayoutCount());
                }
            }
        });
    }

}