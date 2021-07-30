package liang.com.baseproject.fragment;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;


/**
 * 创建日期：2019/2/13 on 16:29
 * 描述: 新闻-Fragment Container
 * 作者: liangyang
 */
public class JuheNewsContainerFragment extends Fragment {

    @BindView(R.id.news_tab_layout)
    TabLayout newsTabLayout;
    @BindView(R.id.news_view_pager)
    ViewPager newsViewPager;
    Unbinder unbinder;
    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private MainHomeActivity mActivity;

    public JuheNewsContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainHomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juhe_news_container, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化资源
        initData();
        //初始化适配器
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getFragmentManager(), fragmentList, stringList);
        //设置至少9个fragment，防止重复创建和销毁，造成内存溢出
//        newsViewPager.setOffscreenPageLimit(9);
        //绑定适配器
        newsViewPager.setAdapter(fragmentViewPagerAdapter);
        //TabLayout属性设置
        //设置下划线指示器圆角
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(dp2px(2));
        newsTabLayout.setSelectedTabIndicator(gradientDrawable);
        //TabLayout同步fragment
        newsTabLayout.setupWithViewPager(newsViewPager);

//        //设置TabLayout下方下划线的宽度
//        newsTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                OptimizeIndicator optimizeIndicator = new OptimizeIndicator();
//                optimizeIndicator.setIndicator(newsTabLayout, 15, 15);
//            }
//        });

        //自定义TabLayout背景
        initCustomTabLayout();
    }

    private void initCustomTabLayout() {
        newsTabLayout.removeAllTabs();
        for (int i = 0; i < stringList.size(); i++) {
            TabLayout.Tab newTab = newsTabLayout.newTab();
            View tabCustomView = LayoutInflater.from(getContext()).inflate(R.layout.core_select_tab_bg_rectangle, null);
            TextView tvTab = tabCustomView.findViewById(R.id.tvChooseTab);
            tvTab.setText(stringList.get(i));
            newTab.setCustomView(tabCustomView);
            newsTabLayout.addTab(newTab);

            //TabLayout点击事件
            TabLayout.Tab tab = newsTabLayout.getTabAt(i);
            if (tab != null) {
                if (tab.getCustomView() != null) {
                    //获得tab对应父item
                    View tabView = (View) tab.getCustomView().getParent();
                    //父item放入标记值
                    tabView.setTag(i);

                    int finalI = i;
                    tabView.setOnClickListener(v -> {
                        TabLayout.Tab tab1 = newsTabLayout.getTabAt(finalI);
                        if (tab1 != null) {
                            tab1.select();
                        }
                    });
                }
            }

        }
    }

    private void initData() {
        /**
         * 新闻头条
         * http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
         * 类型,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
         */
        //TabLayout
        stringList.add("头条");
        stringList.add("国内");
        stringList.add("国际");
        stringList.add("军事");
        stringList.add("娱乐");
        stringList.add("财经");
        stringList.add("科技");
        stringList.add("社会");
        stringList.add("体育");
        stringList.add("时尚");

        Bundle bundle = new Bundle();
        bundle.putString("newsKey", "top");

        Bundle bundle2 = new Bundle();
        bundle2.putString("newsKey", "guonei");

        Bundle bundle3 = new Bundle();
        bundle3.putString("newsKey", "guoji");

        Bundle bundle4 = new Bundle();
        bundle3.putString("newsKey", "junshi");

        Bundle bundle5 = new Bundle();
        bundle4.putString("newsKey", "yule");

        Bundle bundle6 = new Bundle();
        bundle5.putString("newsKey", "caijing");

        Bundle bundle7 = new Bundle();
        bundle5.putString("newsKey", "keji");

        Bundle bundle8 = new Bundle();
        bundle5.putString("newsKey", "shehui");

        Bundle bundle9 = new Bundle();
        bundle5.putString("newsKey", "tiyu");

        Bundle bundle10 = new Bundle();
        bundle5.putString("newsKey", "shishang");

        fragmentList.add(JuheNewsTabFragment.newInstance(bundle));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle2));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle3));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle4));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle5));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle6));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle7));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle8));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle9));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle10));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private int dp2px(float dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
