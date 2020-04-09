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
        newsViewPager.setOffscreenPageLimit(9);
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
        stringList.add("娱乐");
        stringList.add("科技");

        Bundle bundle = new Bundle();
        bundle.putString("newsKey", "top");

        Bundle bundle2 = new Bundle();
        bundle2.putString("newsKey", "guonei");

        Bundle bundle3 = new Bundle();
        bundle3.putString("newsKey", "guoji");

        Bundle bundle4 = new Bundle();
        bundle4.putString("newsKey", "yule");

        Bundle bundle5 = new Bundle();
        bundle5.putString("newsKey", "keji");

        fragmentList.add(JuheNewsTabFragment.newInstance(bundle));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle2));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle3));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle4));
        fragmentList.add(JuheNewsTabFragment.newInstance(bundle5));
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
