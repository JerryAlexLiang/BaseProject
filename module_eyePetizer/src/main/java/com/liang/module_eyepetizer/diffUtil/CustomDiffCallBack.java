package com.liang.module_eyepetizer.diffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.liang.module_core.utils.ToastUtil;
import com.liang.module_eyepetizer.logic.model.test.BaseCustomViewModel;
import com.liang.module_eyepetizer.logic.model.test.Test;

import java.util.List;

/**
 * 创建日期: 2020/12/3 on 3:16 PM
 * 描述: 核心类 用来判断 新旧Item是否相等
 * 作者: 杨亮
 */
public class CustomDiffCallBack extends DiffUtil.Callback {

    private final List<BaseCustomViewModel> mOldDatas;
    private final List<BaseCustomViewModel> mNewDatas;

    public CustomDiffCallBack(List<BaseCustomViewModel> mOldDatas, List<BaseCustomViewModel> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    /**
     * 旧数据集合size
     */
    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    /**
     * 新数据集合size
     */
    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    /**
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item
     * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
     * 本例判断name字段是否一致
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getSortIndex() == mNewDatas.get(newItemPosition).getSortIndex();
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * 所以你可以根据你的UI去改变它的返回值
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        BaseCustomViewModel modelOld = mOldDatas.get(oldItemPosition);
        BaseCustomViewModel newModel = mNewDatas.get(newItemPosition);

        if (modelOld instanceof Test.MultiCardBean && newModel instanceof Test.MultiCardBean) {
            List<Test.MultiCardBean.BannerBean> bannerOld = ((Test.MultiCardBean) modelOld).getBanner();
            List<Test.MultiCardBean.BannerBean> bannerNew = ((Test.MultiCardBean) newModel).getBanner();
//            for (int i = 0; i < bannerOld.size(); i++) {
//                for (int j = 0; j < bannerNew.size(); j++) {
//                    if (!bannerOld.get(i).getIconUrl().equals(bannerNew.get(j).getIconUrl())) {
//                        //如果有内容不同，就返回false
//                        ToastUtil.showShortToast("MultiCardBean变化");
//                        return false;
//                    }
//                }
//            }

            if (bannerOld.size() != bannerNew.size()) {
                return false;
            }else {
                for (int i = 0; i < bannerOld.size(); i++) {
                    if (!bannerOld.get(i).getIconUrl().equals(bannerNew.get(i).getIconUrl())) {
                        //如果有内容不同，就返回false
                        ToastUtil.showShortToast("MultiCardBean变化");
                        return false;
                    }
                }
            }
        }

        if (modelOld instanceof Test.SingleCardOneBean && newModel instanceof Test.SingleCardOneBean) {
            String iconUrlOld = ((Test.SingleCardOneBean) modelOld).getIconUrl();
            String iconUrlNew = ((Test.SingleCardOneBean) newModel).getIconUrl();
            if (!iconUrlOld.equals(iconUrlNew)) {
                ToastUtil.showShortToast("SingleCardOneBean变化");
                return false;
            }
        }

        if (modelOld instanceof Test.SingleCardTwoBean && newModel instanceof Test.SingleCardTwoBean) {
            String iconUrlOld = ((Test.SingleCardTwoBean) modelOld).getIconUrl();
            String iconUrlNew = ((Test.SingleCardTwoBean) newModel).getIconUrl();
            if (!iconUrlOld.equals(iconUrlNew)) {
                ToastUtil.showShortToast("SingleCardTwoBean变化");
                return false;
            }
        }

        //默认两个data内容是相同的
        return true;
    }
}

