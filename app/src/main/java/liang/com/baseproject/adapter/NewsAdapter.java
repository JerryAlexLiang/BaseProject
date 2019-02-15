package liang.com.baseproject.adapter;

import android.content.Context;

import java.util.List;

import liang.com.baseproject.R;
import liang.com.baseproject.base.BaseRecycleAdapter;
import liang.com.baseproject.base.BaseViewHolder;
import liang.com.baseproject.entity.NewsRes;

public class NewsAdapter extends BaseRecycleAdapter<NewsRes.ResultBean.DataBean> {

    private Context mContext;

    public NewsAdapter(Context context, List<NewsRes.ResultBean.DataBean> dataList) {
        super(context, dataList, R.layout.rv_item_news);
        this.mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder holder, NewsRes.ResultBean.DataBean data) {
        super.bindData(holder, data);
        if (data != null) {
            holder.setText(R.id.tv_news_title,data.getTitle())
                    .setText(R.id.tv_news_author,data.getAuthor_name())
                    .setText(R.id.tv_news_time,data.getDate())
                    .setImageUrl(mContext,R.id.iv_news_img,data.getThumbnail_pic_s());
        }
    }
}
