package liang.com.baseproject.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 创建日期：2019/2/27 on 16:04
 * 描述: 轮播Banner ViewPager适配器
 * 作者: liangyang
 */
public class MyBannerPagerAdapter extends PagerAdapter {

    private List<ImageView> imageList;
    private ViewPager viewPager;
    private ViewPagerClickInterFace viewPagerClickInterFace;

    public MyBannerPagerAdapter(List<ImageView> imageList, ViewPager viewPager) {
        this.imageList = imageList;
        this.viewPager = viewPager;
    }

    /**
     * 返回的int的值, 会作为ViewPager的总长度来使用.
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//Integer.MAX_VALUE伪无限循环
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    /**
     * 初始化一个条目
     * position 就是当前需要加载条目的索引
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 把position对应位置的ImageView添加到ViewPager中
        ImageView iv = imageList.get(position % imageList.size());
        viewPager.addView(iv);
        //点击事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPagerClickInterFace!=null){
                    //执行回调方法
                    viewPagerClickInterFace.onClick(position % imageList.size());
                }
            }
        });
        // 把当前添加ImageView返回回去.
        return iv;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 把ImageView从ViewPager中移除掉
        viewPager.removeView(imageList.get(position % imageList.size()));
    }

    /**
     * 点击事件监听器回调接口
     */
    public interface ViewPagerClickInterFace {
        /**
         * item点击事件监听
         */
        void onClick(int position);
    }

    /**
     * 点击事件监听器回调方法
     */
    public void setViewPagerClickInterFace(ViewPagerClickInterFace viewPagerClickInterFace) {
        this.viewPagerClickInterFace = viewPagerClickInterFace;
    }
}
