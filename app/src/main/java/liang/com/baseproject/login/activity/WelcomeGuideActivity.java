package liang.com.baseproject.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.adapter.GuideViewPagerAdapter;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.utils.SPUtils;

public class WelcomeGuideActivity extends MVPBaseActivity implements View.OnClickListener {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.ll_dot_container)
    LinearLayout llDotContainer;

    private List<View> views;
    // 引导页图片资源
    private static final int[] pics = {R.layout.viwe_guide_one, R.layout.viwe_guide_two,
            R.layout.viwe_guide_three, R.layout.viwe_guide_four, R.layout.viwe_guide_five};
    // 底部小点图片
    private ImageView[] dots;
    // 记录当前选中位置
    private int currentIndex;
    private Button startBtn;
    private GuideViewPagerAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WelcomeGuideActivity.class);
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
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_welcome_guide;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        initDots();
    }

    private void initDots() {
//        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_dot_container);
        dots = new ImageView[pics.length];
        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) llDotContainer.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态
    }

    private void initView() {
        views = new ArrayList<View>();
        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if (i == pics.length - 1) {
                startBtn = (Button) view.findViewById(R.id.btn_enter);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }

//        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        adapter = new GuideViewPagerAdapter(views);
        vpGuide.setAdapter(adapter);
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置底部小点选中状态
                setCurDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    /**
     * 设置底部小点选中状态
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        //最后一页Button
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        //设置当前view
        setCurView(position);
        //设置底部小点选中状态
        setCurDot(position);
    }

    private void enterMainActivity() {
        //设置非第一次进入
        SPUtils.put(this, Constant.IS_FIRST_FLAG, false);
        MainHomeActivity.actionStart(this);
        //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    /**
     * 设置当前view
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vpGuide.setCurrentItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页，设置非第一次进入
        SPUtils.put(this, Constant.IS_FIRST_FLAG, false);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
