package com.liang.module_laboratory.select_list;

import android.graphics.Color;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liang.module_core.adapter.BaseSelectListAdapter;
import com.liang.module_laboratory.R;

import org.jetbrains.annotations.NotNull;

/**
 * 创建日期: 2021/1/28 on 3:07 PM
 * 描述: 基于封装的单多选列表适配器的单选模式列表Adapter
 * 作者: 杨亮
 */
public class CommonSingleSelectListAdapter extends BaseSelectListAdapter<BookBean> {

    public CommonSingleSelectListAdapter() {
        super(R.layout.lab_rv_select_list_item);
    }

    @Override
    public boolean isMultipleSelectModel() {
        return false;
    }

    @Override
    public void doSelectModelBindViewHolder(@NotNull BaseViewHolder holder, BookBean item) {
        holder.setText(R.id.tvName, item.getName());
    }

    @Override
    public void onItemViewSelected(@NotNull BaseViewHolder holder) {
        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_checked_multi);
        holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.darkgray));
    }

    @Override
    public void onItemViewUnSelected(@NotNull BaseViewHolder holder) {
        holder.setImageResource(R.id.ivSelectContainer, R.drawable.ui_common_uncheck);
        holder.itemView.setBackgroundColor(Color.WHITE);
    }
}
