package com.liang.module_eyepetizer.ui.adapter;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.liang.module_eyepetizer.logic.model.EyeConstant;
import com.liang.module_eyepetizer.logic.model.Item;
import com.liang.module_eyepetizer.ui.adapter.provider.TopBannerProviderJava;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 创建日期: 2020/11/25 on 1:43 PM
 * 描述:
 * 作者: 杨亮
 */
public class DiscoveryAdapterJava extends BaseProviderMultiAdapter<Item> {

    public DiscoveryAdapterJava(@Nullable List<Item> data) {
        super(data);
        addItemProvider(new TopBannerProviderJava());
    }

    @Override
    protected int getItemType(@NotNull List<? extends Item> data, int position) {
        String type = data.get(position).getType();
        if (type.equals(EyeConstant.IDisCoverItemReturnType.horizontalScrollCard)){
            //顶部banner
            return EyeConstant.IDisCoverItemType.TOP_BANNER_VIEW;
        }
        return 0;
    }
}
