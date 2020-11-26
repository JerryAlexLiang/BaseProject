package com.liang.module_eyepetizer.ui.adapter.provider;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liang.module_eyepetizer.R;
import com.liang.module_eyepetizer.logic.model.EyeConstant;
import com.liang.module_eyepetizer.logic.model.Item;

import org.jetbrains.annotations.NotNull;

/**
 * 创建日期: 2020/11/25 on 1:48 PM
 * 描述:
 * 作者: 杨亮
 */
public class TopBannerProviderJava extends BaseItemProvider<Item> {

    @Override
    public int getItemViewType() {
        return EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW;
    }

    @Override
    public int getLayoutId() {
        return R.layout.eye_discovery_banner_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, Item item) {

    }
}
