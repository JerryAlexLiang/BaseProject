package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.ImageViewPagerAdapter;
import com.liang.module_core_java.mvp.MVPBasePresenter;
import com.liang.module_core_java.mvp.MVPBaseActivity;
import com.liang.module_core_java.utils.FileUtil;
import com.liang.module_core_java.utils.LogUtil;

/**
 * 创建日期：2019/2/22 on 13:18
 * 描述: ViewPager-多张大图显示器
 * 作者: liangyang
 */
public class ViewPagerPictureActivity extends MVPBaseActivity {

    private static final String TAG = ViewPagerPictureActivity.class.getSimpleName();
    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_actionbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseToolbar;
    @BindView(R.id.image_view_pager)
    ViewPager imageViewPager;
    @BindView(R.id.tv_current_page)
    TextView tvCurrentPage;
    @BindView(R.id.save_img)
    FloatingActionButton saveImg;
    private List<String> imageUrlList;

    private int currentPage = 0;

    public static void actionStart(Context context, List<String> imageUrlList) {
        Intent intent = new Intent(context, ViewPagerPictureActivity.class);
        intent.putExtra("imageUrlList", (Serializable) imageUrlList);
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
        return R.layout.activity_view_pager_picture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_pager_picture);
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarTitle.setVisibility(View.GONE);

        //获取传递过来的数据
        parseIntent();
        //初始化监听事件
        initListener();
        //初始化数据
        initData();

    }

    private void initData() {
        //初始化图片容器ViewPager的适配器
        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(imageUrlList);
        imageViewPager.setAdapter(imageViewPagerAdapter);
        //设置当前显示位置
        imageViewPager.setCurrentItem(imageUrlList.indexOf(0));
    }

    private void initListener() {
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //图片指示器
                tvCurrentPage.setText(String.format("%s/" + imageUrlList.size(), position + 1));
                currentPage = position;
                LogUtil.d(TAG, "当前图片索引是:  " + currentPage);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void parseIntent() {
        imageUrlList = (List<String>) getIntent().getSerializableExtra("imageUrlList");
        LogUtil.d(TAG, imageUrlList.toString());
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.save_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.save_img:
                int currentItem = imageViewPager.getCurrentItem();
                String currentImageUrl = imageUrlList.get(currentItem);
                LogUtil.d(TAG, "当前图片地址:  " + currentImageUrl);
                Bitmap bitmap = FileUtil.saveImageByUrl(currentImageUrl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtil.translateBitmapToFile(mActivity, bitmap, String.valueOf(System.currentTimeMillis()));
                    }
                });
                break;
        }
    }
}
