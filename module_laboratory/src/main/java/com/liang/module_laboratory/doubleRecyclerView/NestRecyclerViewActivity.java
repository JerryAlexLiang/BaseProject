package com.liang.module_laboratory.doubleRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.liang.module_core.jetpack.MVVMBaseActivity;
import com.liang.module_laboratory.R;
import com.liang.module_laboratory.doubleRecyclerView.widget.CoordinatorLayoutFix;
import com.liang.module_laboratory.doubleRecyclerView.widget.CustomBehavior;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NestRecyclerViewActivity extends MVVMBaseActivity {

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private ArrayList<String> mDataList = new ArrayList<>();

    private String[] strArray = new String[]{"关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门"};

    private ViewPager viewPager;
    private TabLayout tabs;
    private RecyclerView parentRecyclerView;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NestRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_nest_recycler_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        IndexPagerAdapter indexPagerAdapter = new IndexPagerAdapter(getSupportFragmentManager(), stringList, fragmentList);
        viewPager.setAdapter(indexPagerAdapter);

        tabs.setupWithViewPager(viewPager);
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        parentRecyclerView = (RecyclerView) findViewById(R.id.parent_recycler_view);

        parentRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        for (int i = 0; i < 10; i++) {
            mDataList.add("top: " + i);
        }

        adapter.addData(mDataList);
        parentRecyclerView.setAdapter(adapter);

        parentRecyclerView.getAdapter().notifyDataSetChanged();


        CoordinatorLayoutFix coordinatorLayoutFix = (CoordinatorLayoutFix) findViewById(R.id.coordinator);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        coordinatorLayoutFix.setOnInterceptTouchListener(new CoordinatorLayoutFix.OnInterceptTouchListener() {
            @Override
            public void onIntercept() {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
                if (behavior instanceof CustomBehavior) {
                    //fix 解决与RecyclerView联合使用的回弹问题
                    if (!fragmentList.isEmpty() && viewPager.getCurrentItem() >= 0 && viewPager.getCurrentItem() < fragmentList.size()) {
                        ((ChildFragment) fragmentList.get(viewPager.getCurrentItem())).stopNestedScrolling();
                    }

                    //fix 解决动画抖动
                    ((CustomBehavior) behavior).stopFling();
                }
            }
        });

        initData();

    }

    private void initData() {
        stringList.addAll(Arrays.asList(strArray));

        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
        fragmentList.add(ChildFragment.newInstance());
    }

    class IndexPagerAdapter extends FragmentPagerAdapter {

        private List<String> titleList;

        public IndexPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @androidx.annotation.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}