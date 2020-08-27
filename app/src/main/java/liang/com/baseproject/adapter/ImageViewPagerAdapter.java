package liang.com.baseproject.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import liang.com.baseproject.R;
import com.liang.module_core.utils.FileUtil;
import com.liang.module_core.utils.ImageLoaderUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 创建日期：2019/2/22 on 13:47
 * 描述: 多张图片显示器-ViewPager的适配器
 * 作者: liangyang
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private List<String> imageUrlList;

    public ImageViewPagerAdapter(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    @Override
    public int getCount() {
        return imageUrlList != null ? imageUrlList.size() : 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_view_pager_adapter, null);
//        PhotoView photoView = rootView.findViewById(R.id.photo_view);
//        FloatingActionButton saveViewPagerImg = rootView.findViewById(R.id.save_view_pager_img);
//        ImageLoaderUtils.loadRadiusImage(container.getContext(), true, photoView, imageUrlList.get(position),
//                R.drawable.default_pic_content_image_loading_light, R.drawable.default_pic_content_image_download_light, 0);
////        photoView.setZoomable(true);
//        container.addView(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        saveViewPagerImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileUtil.saveImage(container.getContext(), photoView, String.valueOf(System.currentTimeMillis()));
//            }
//        });
//
//        return rootView;

        PhotoView photoView = new PhotoView(container.getContext());
        ImageLoaderUtils.loadRadiusImage(container.getContext(), true, photoView, imageUrlList.get(position),
                R.drawable.default_pic_content_image_loading_light, R.drawable.default_pic_content_image_download_light, 0);
        photoView.setZoomable(true);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
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
                                FileUtil.saveImage(container.getContext(), photoView, String.valueOf(System.currentTimeMillis()));
                                dialog.dismiss();
                            }
                        })
                        .show();
                return false;
            }
        });

        //photoView的单击点击事件- 仿微博单击后退出大图界面
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                ((Activity)container.getContext()).finish();
            }
        });


        return photoView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
