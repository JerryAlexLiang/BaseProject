package liang.com.baseproject.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.entity.RefreshHeaderBean;

/**
 * 创建日期: 2021/4/8 on 3:28 PM
 * 描述: RefreshHeader全局设置页面
 * 作者: 杨亮
 */
public class RefreshHeaderChangeRvAdapter extends BaseQuickAdapter<RefreshHeaderBean, RefreshHeaderChangeRvAdapter.MyViewHolder> {

    public RefreshHeaderChangeRvAdapter() {
        super(R.layout.rv_refresh_header_change_item);
    }

    @Override
    protected void convert(@NotNull MyViewHolder myViewHolder, RefreshHeaderBean refreshHeaderBean) {
        myViewHolder.tvName.setText(refreshHeaderBean.getName());
        Glide.with(getContext()).asGif().load(refreshHeaderBean.getIconId()).into(myViewHolder.ivImage);
    }

    static class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;

        public MyViewHolder(@NotNull View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
} 
