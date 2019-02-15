package liang.com.baseproject.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.FragmentViewPagerAdapter;
import liang.com.baseproject.fragment.OneFragment;

public class ScanCodeActivity extends AppCompatActivity {

    @BindView(R.id.scan_tab_layout)
    TabLayout scanTabLayout;
    @BindView(R.id.scan_view_pager)
    ViewPager scanViewPager;

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        //初始化资源
        initData();
        //初始化适配器
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList, stringList);
        //设置至少9个fragment，防止重复创建和销毁，造成内存溢出
        scanViewPager.setOffscreenPageLimit(9);
        //绑定适配器
        scanViewPager.setAdapter(fragmentViewPagerAdapter);
        //TabLayout同步fragment
        scanTabLayout.setupWithViewPager(scanViewPager);
    }

    private void initData() {
        stringList.add("头条");
        stringList.add("国内");
        stringList.add("国际");
        stringList.add("视频");
        stringList.add("时尚");
        stringList.add("明星");
        stringList.add("科技");

        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
        fragmentList.add(OneFragment.newInstance(null));
    }
}
