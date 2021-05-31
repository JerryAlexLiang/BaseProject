package com.liang.module_laboratory.doubleRecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liang.module_laboratory.R;

import org.jetbrains.annotations.NotNull;

/**
 * 创建日期: 2021/5/25 on 4:10 PM
 * 描述:
 * 作者: 杨亮
 */
public class RecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RecyclerViewAdapter() {
        super(R.layout.layout_item_text);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.textView,s);
    }
}
