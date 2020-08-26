package liang.com.baseproject.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.liang.module_core_java.utils.LogUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import liang.com.baseproject.R;

import static android.app.Activity.RESULT_OK;

/**
 * 创建日期：2019/9/16 on 15:37
 * 描述: 图片选择库
 * 作者: liangyang
 */
public class PictureSelectorUtils {

    private static final String TAG = PictureSelectorUtils.class.getSimpleName();

    private static volatile PictureSelectorUtils instance;
    private static int themeId;

    public static PictureSelectorUtils getInstance() {
        if (instance == null) {
            synchronized (PictureSelectorUtils.class) {
                if (instance == null) {
                    instance = new PictureSelectorUtils();
//                    themeId = SPUtils.get()
                    themeId = R.style.picture_default_style;
                }
            }
        }
        return instance;
    }

    /**
     * @param selectionMode  单选or多选   1:单选，2:多选
     * @param chooseMode     模式 0: all 1:image 2:video 3:音频
     * @param enableCrop     是否裁剪
     * @param enableCompress 是否压缩
     * @param isCamera       是否显示拍照按钮
     * @param maxSelectNum   最大图片选择数量
     */
    public static void openGallery(Activity activity, int requestCode, int selectionMode, int chooseMode, boolean enableCrop, boolean enableCompress, boolean isCamera, int maxSelectNum) {
        //进入相册
        PictureSelector.create(activity)
                .openGallery(chooseMode)
//                .theme(themeId)// 主题样式设置
                .theme(R.style.picture_QQ_style)// 主题样式设置
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(enableCrop)// 是否裁剪
                .compress(enableCompress)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
//                .compressSavePath("BaseProject")//压缩图片保存地址
//                .selectionMedia(selectList)// 是否传入已选图片
                //.videoMaxSecond(15)
                //.videoMinSecond(10)
                //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.videoQuality(0)// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond(90)//录制视频秒数 默认60s
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                .forResult(requestCode);//结果回调onActivityResult code
    }

    public static void openGallery(Fragment fragment, int requestCode, int selectionMode, int chooseMode, boolean enableCrop, boolean enableCompress, boolean isCamera, int maxSelectNum) {
        //进入相册
        PictureSelector.create(fragment)
                .openGallery(chooseMode)
                .theme(R.style.picture_QQ_style)// 主题样式设置
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(enableCrop)// 是否裁剪
                .compress(enableCompress)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                .compressSavePath("BaseProject")//压缩图片保存地址
//                .selectionMedia(selectList)// 是否传入已选图片
                //.videoMaxSecond(15)
                //.videoMinSecond(10)
                //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.videoQuality(0)// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond(90)//录制视频秒数 默认60s
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                .forResult(requestCode);//结果回调onActivityResult code
    }


    public static List<LocalMedia> forResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 图片、视频、音频选择结果回调
//            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片、视频、音频选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
            for (LocalMedia media : selectList) {
                LogUtil.d(TAG, "原图---> " + media.getPath());
                LogUtil.d(TAG, "裁剪---> " + media.getCutPath());
                LogUtil.d(TAG, "压缩---> " + media.getCompressPath());
            }
            return selectList;
        }
        return null;
    }

//    /**
//     * 自定义压缩存储地址
//     *
//     * @return
//     */
//    public static String getPath() {
//        String path = Environment.getExternalStorageDirectory() + "BaseProject/camera";
//        File file = new File(path);
//        if (file.mkdirs()) {
//            return path;
//        }
//        return path;
//    }
}
