package com.liang.module_core.widget.pageMenuLayout.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期: 2020/12/1 on 2:09 PM
 * 描述: ViewPager适配器
 * 作者: 杨亮
 */
public class PageViewPagerAdapter extends PagerAdapter {

    private List<View> mViewList;
    private boolean isCanLoop;

    public PageViewPagerAdapter(List<View> viewList, boolean isCanLoop) {
        this.mViewList = viewList;
        this.isCanLoop = isCanLoop;
        if (viewList == null) {
            mViewList = new ArrayList<>();
        }
    }

    public PageViewPagerAdapter(List<View> viewList) {
        this.mViewList = viewList;
        if (viewList == null) {
            mViewList = new ArrayList<>();
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView(mViewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position % mViewList.size();
        View view = mViewList.get(realPosition);
        if (container.equals(view.getParent())) {
            container.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (mViewList.isEmpty()) {
            return 0;
        }
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
