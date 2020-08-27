package liang.com.baseproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.utils.FileUtil;
import com.liang.module_core.utils.ImageLoaderUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 创建日期：2019/2/20 on 17:32
 * 描述: 单张大图显示器
 * 作者: liangyang
 */
public class SinglePictureActivity extends MVPBaseActivity implements View.OnLongClickListener {

    public static final String IMG_URL = "img_url";
    public static final String IMG_DESC = "img_desc";
    public static final String TRANSIT_PIC = "picture";

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
    @BindView(R.id.iv_meizhi_pic)
    ImageView ivMeizhiPic;
    @BindView(R.id.save_img)
    FloatingActionButton saveImg;
    @BindView(R.id.layout_pic)
    RelativeLayout layoutPic;
    @BindView(R.id.iv_meizhi_zoom_pic)
    PhotoView ivMeizhiZoomPic;
    private String imgUrl;
    private String imgDesc;

    public static void actionStart(Context context, String imageUrl, String imageDesc) {
        Intent intent = new Intent(context, SinglePictureActivity.class);
        intent.putExtra(IMG_URL, imageUrl);
        intent.putExtra(IMG_DESC, imageDesc);
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
//        ImageLoaderUtils.loadRadiusImage(this, true, ivMeizhiPic, imgUrl, 0, 0, 0);
//        new PhotoViewAttacher(ivMeizhiPic); //使用缩放控件
        ImageLoaderUtils.loadRadiusImage(this, true, ivMeizhiZoomPic, imgUrl, 0, 0, 0);

        //ivMeizhiZoomPic的长按点击事件
        ivMeizhiZoomPic.setOnLongClickListener(this);

        //ivMeizhiZoomPic的单击点击事件- 仿微博单击后退出大图界面   ((Activity)context).finish();
        ivMeizhiZoomPic.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });

    }

    private void parseIntent() {
        imgUrl = getIntent().getStringExtra(IMG_URL);
        imgDesc = getIntent().getStringExtra(IMG_DESC);
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.base_actionbar_right_icon, R.id.save_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.base_actionbar_right_icon:

            case R.id.save_img:
                savePictureLocal();
                break;
        }
    }

    private void savePictureLocal() {
        if (imgUrl.startsWith("http://") || imgUrl.startsWith("https://")) {
            savePicByUrl();
        }else {
            FileUtil.saveImage(SinglePictureActivity.this, ivMeizhiZoomPic, imgDesc);
        }
    }

    private void savePicByUrl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = FileUtil.saveImageByUrl(imgUrl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtil.translateBitmapToFile(mActivity, bitmap, imgDesc);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * ivMeizhiZoomPic的长按点击事件
     */
    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("保存图片")
                .setMessage("图片将保存到手机内存中，会占用内存哦~")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePicByUrl();
                        dialog.dismiss();
                    }
                })
                .show();
        return false;
    }
}
