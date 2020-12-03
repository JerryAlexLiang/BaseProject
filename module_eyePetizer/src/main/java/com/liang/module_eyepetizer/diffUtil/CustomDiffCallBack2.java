package com.liang.module_eyepetizer.diffUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.liang.module_core.utils.ToastUtil;
import com.liang.module_eyepetizer.logic.model.test.BaseCustomViewModel;
import com.liang.module_eyepetizer.logic.model.test.Test;

import java.util.List;

/**
 * 创建日期: 2020/12/3 on 4:29 PM
 * 描述:
 * 作者: 杨亮
 */
public class CustomDiffCallBack2 extends DiffUtil.ItemCallback<BaseCustomViewModel> {

    /**
     * 判断是否是同一个item
     */
    @Override
    public boolean areItemsTheSame(@NonNull BaseCustomViewModel oldItem, @NonNull BaseCustomViewModel newItem) {
        return oldItem.getSortIndex() == newItem.getSortIndex();
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     */
    @Override
    public boolean areContentsTheSame(@NonNull BaseCustomViewModel oldItem, @NonNull BaseCustomViewModel newItem) {
        boolean isSame = false;
        if (oldItem instanceof Test.SingleCardOneBean && newItem instanceof Test.SingleCardOneBean) {
            String iconUrlOld = ((Test.SingleCardOneBean) oldItem).getIconUrl();
            String iconUrlNew = ((Test.SingleCardOneBean) newItem).getIconUrl();
//            if (!iconUrlOld.equals(iconUrlNew)) {
//                //如果有内容不同，就返回false
//                ToastUtil.showShortToast("SingleCardTwoBean变化");
//                return false;
//            }
            //如果有内容不同，就返回false
            isSame = iconUrlOld.equals(iconUrlNew);
        }

        if (oldItem instanceof Test.SingleCardTwoBean && newItem instanceof Test.SingleCardTwoBean) {
            String iconUrlOld = ((Test.SingleCardTwoBean) oldItem).getIconUrl();
            String iconUrlNew = ((Test.SingleCardTwoBean) oldItem).getIconUrl();
//            if (!iconUrlOld.equals(iconUrlNew)) {
//                //如果有内容不同，就返回false
//                ToastUtil.showShortToast("SingleCardTwoBean变化");
//                return false;
//            }
            //如果有内容不同，就返回false
            isSame = iconUrlOld.equals(iconUrlNew);
        }

        if (oldItem instanceof Test.MultiCardBean && newItem instanceof Test.MultiCardBean) {
            List<Test.MultiCardBean.BannerBean> bannerOld = ((Test.MultiCardBean) oldItem).getBanner();
            List<Test.MultiCardBean.BannerBean> bannerNew = ((Test.MultiCardBean) newItem).getBanner();

            if (bannerOld.size() != bannerNew.size()) {
                return false;
            } else {
                for (int i = 0; i < bannerOld.size(); i++) {
//                    if (!bannerOld.get(i).getIconUrl().equals(bannerNew.get(i).getIconUrl())) {
////                    //如果有内容不同，就返回false
//                        ToastUtil.showShortToast("MultiCardBean变化");
//                        return false;
//                    }
                    //如果有内容不同，就返回false
                    isSame = bannerOld.get(i).getIconUrl().equals(bannerNew.get(i).getIconUrl());
                    return isSame;
                }
            }
//            isSame = (bannerOld.size() == bannerNew.size());
//
//            if (bannerOld.size() == bannerNew.size()) {
//                for (int i = 0; i < bannerOld.size(); i++) {
//                    //如果有内容不同，就返回false
//                    isSame = bannerOld.get(i).getIconUrl().equals(bannerNew.get(i).getIconUrl());
//                }
//            }


        }
        return isSame;
    }
}
