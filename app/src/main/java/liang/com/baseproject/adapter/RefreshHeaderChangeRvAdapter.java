package liang.com.baseproject.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.entity.RefreshHeaderBean;

/**
 * 创建日期: 2021/4/8 on 3:28 PM
 * 描述: RefreshHeader全局设置页面 extends BaseSelectListAdapter<BookBean> {
 * 作者: 杨亮
 */
public class RefreshHeaderChangeRvAdapter extends BaseQuickAdapter<RefreshHeaderBean, RefreshHeaderChangeRvAdapter.MyViewHolder> {

    private final List<RefreshHeaderBean> selectDataList = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(RefreshHeaderBean bean);
    }

    public void setOnMyItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RefreshHeaderChangeRvAdapter() {
        super(R.layout.rv_refresh_header_change_item);
    }

    @Override
    protected void convert(@NotNull MyViewHolder myViewHolder, RefreshHeaderBean refreshHeaderBean) {
        myViewHolder.tvName.setText(refreshHeaderBean.getName());
        Glide.with(getContext()).asGif().load(refreshHeaderBean.getIconId()).into(myViewHolder.ivImage);

        myViewHolder.itemView.setOnClickListener(v -> {
            if (!selectDataList.contains(refreshHeaderBean)) {
                selectDataList.clear();
                selectDataList.add(refreshHeaderBean);
            }
            notifyDataSetChanged();

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(refreshHeaderBean);
            }

        });

        if (selectDataList.contains(refreshHeaderBean)) {
            myViewHolder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_checked_multi);
        } else {
            myViewHolder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_uncheck);
        }
    }

    static class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;

        public MyViewHolder(@NotNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setDefaultSelectList(List<RefreshHeaderBean> defaultSelectList) {
        //单选
        selectDataList.clear();
        selectDataList.addAll(defaultSelectList);
        notifyDataSetChanged();
    }

    public List<RefreshHeaderBean> getSelectedList() {
        return selectDataList;
    }
}

//public class RefreshHeaderChangeRvAdapter extends BaseSelectListAdapter<RefreshHeaderBean>{
//
//    public RefreshHeaderChangeRvAdapter() {
//        super(R.layout.rv_refresh_header_change_item);
//    }
//
//    @Override
//    public boolean isMultipleSelectModel() {
//        return false;
//    }
//
//    @Override
//    public void onItemViewSelected(@NotNull BaseViewHolder holder) {
//        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_checked_multi);
//    }
//
//    @Override
//    public void onItemViewUnSelected(@NotNull BaseViewHolder holder) {
//        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_uncheck);
//    }
//
//    @Override
//    public void doBindViewHolder(@NotNull BaseViewHolder holder, RefreshHeaderBean item) {
//        holder.setText(R.id.tv_name,item.getName());
//        ImageView ivImage = holder.getView(R.id.iv_image);
//        Glide.with(getContext()).asGif().load(item.getIconId()).into(ivImage);
//
//        //ivSelectContainer
//    }
//}
