package com.liang.module_core.widget.pageMenuLayout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 创建日期: 2020/12/1 on 2:08 PM
 * 描述: 分页菜单项列表适配器
 * 作者: 杨亮
 */
public class PageSlideRvAdapter<T> extends RecyclerView.Adapter<PageSlideRvHolder> {

    private List<T> mData;

    /**
     * 页数下标,从0开始(通俗讲第几页)
     */
    private int mIndex;

    /**
     * 每页显示最大条目个数
     */
    private int mPageSize;
    private PageSlideMenuViewHolderCreator mPageSlideMenuViewHolderCreator;

    public PageSlideRvAdapter(PageSlideMenuViewHolderCreator holderCreator, List<T> data, int index, int pageSize) {
        this.mData = data;
        this.mPageSize = pageSize;
        this.mIndex = index;
        this.mPageSlideMenuViewHolderCreator = holderCreator;
    }

    @Override
    public int getItemCount() {
        return mData.size() > (mIndex + 1) * mPageSize ? mPageSize : (mData.size() - mIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }


    @NonNull
    @Override
    public PageSlideRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mPageSlideMenuViewHolderCreator.getLayoutId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return mPageSlideMenuViewHolderCreator.createHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PageSlideRvHolder holder, int position) {
        final int pos = position + mIndex * mPageSize;
        holder.bindView(holder,mData.get(pos),pos);
    }

}
