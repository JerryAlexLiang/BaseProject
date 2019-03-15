package com.yl.recyclerview.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yl.recyclerview.R;
import com.yl.recyclerview.bean.VideoBeanRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VideoBeanRes.ResultsBean> resultsBeanList;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 底部布局
    private final int TYPE_FOOTER = 2;

    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;

    public NetLoadMoreAdapter(List<VideoBeanRes.ResultsBean> resultsBeanList) {
        this.resultsBeanList = resultsBeanList;
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个Item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //通过判断显示类型，来创建不同的View
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_list, parent, false);
            return new MyViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_footer, parent, false);
            return new FooterViewHolder(footerView);
        }
        return null;
    }

    //@Override
    //    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    //        if (holder instanceof RecyclerViewHolder) {
    //            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
    //            recyclerViewHolder.tvItem.setText(dataList.get(position));
    //
    //        } else if (holder instanceof FootViewHolder) {
    //            FootViewHolder footViewHolder = (FootViewHolder) holder;
    //            switch (loadState) {
    //                case LOADING: // 正在加载
    //                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
    //                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
    //                    footViewHolder.llEnd.setVisibility(View.GONE);
    //                    break;
    //
    //                case LOADING_COMPLETE: // 加载完成
    //                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
    //                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
    //                    footViewHolder.llEnd.setVisibility(View.GONE);
    //                    break;
    //
    //                case LOADING_END: // 加载到底
    //                    footViewHolder.pbLoading.setVisibility(View.GONE);
    //                    footViewHolder.tvLoading.setVisibility(View.GONE);
    //                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
    //                    break;
    //
    //                default:
    //                    break;
    //            }
    //        }
    //    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return resultsBeanList.size() + 1;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_who)
        TextView tvWho;
        @BindView(R.id.iv_type_bg)
        ImageView ivTypeBg;
        @BindView(R.id.card_gank)
        CardView cardGank;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindItem(List<VideoBeanRes.ResultsBean> resultsBeans){

        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pb_loading)
        ProgressBar pbLoading;
        @BindView(R.id.tv_loading)
        TextView tvLoading;
        @BindView(R.id.ll_end)
        LinearLayout llEnd;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
