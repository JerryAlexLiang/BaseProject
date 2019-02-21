package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.base.BasePresenter;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.utils.FileUtil;
import liang.com.baseproject.utils.ImageLoaderUtils;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 创建日期：2019/2/20 on 17:32
 * 描述:
 * 作者: liangyang
 */
public class SinglePictureActivity extends MVPBaseActivity {

    public static final String IMG_URL = "img_url";
    public static final String IMG_DESC = "img_desc";
    public static final String TRANSIT_PIC = "picture";

    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.base_toolbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_toolbar)
    FrameLayout baseToolbar;
    @BindView(R.id.iv_meizhi_pic)
    ImageView ivMeizhiPic;
    @BindView(R.id.save_img)
    FloatingActionButton saveImg;
    @BindView(R.id.layout_pic)
    RelativeLayout layoutPic;
    private String imgUrl;
    private String imgDesc;

    public static void actionStart(Context context, String imageUrl, String imageDesc) {
        Intent intent = new Intent(context, SinglePictureActivity.class);
        intent.putExtra(IMG_URL, imageUrl);
        intent.putExtra(IMG_DESC, imageDesc);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_single_picture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_single_picture);
//        getActionBarTheme(baseToolbar);
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setImageResource(R.drawable.icon_file_download);
        baseToolbarTitle.setVisibility(View.GONE);

        //获取传递过来的数据
        parseIntent();
        //赋值
        ImageLoaderUtils.loadImage(this, ivMeizhiPic, imgUrl, 0, 0, 0);
        new PhotoViewAttacher(ivMeizhiPic);
    }

    private void parseIntent() {
        imgUrl = getIntent().getStringExtra(IMG_URL);
        imgDesc = getIntent().getStringExtra(IMG_DESC);
    }

    @OnClick({R.id.base_toolbar_left_icon, R.id.base_toolbar_right_icon,R.id.save_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_toolbar_left_icon:
                finish();
                break;

            case R.id.base_toolbar_right_icon:
                FileUtil.saveImage(SinglePictureActivity.this, ivMeizhiPic, imgDesc);
                break;

            case R.id.save_img:
                FileUtil.saveImage(SinglePictureActivity.this, ivMeizhiPic, imgDesc);
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
