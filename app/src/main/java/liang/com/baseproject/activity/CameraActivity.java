package liang.com.baseproject.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import liang.com.baseproject.R;
import com.liang.module_core_java.mvp.MVPBaseActivity;
import com.liang.module_core_java.mvp.MVPBasePresenter;

public class CameraActivity extends MVPBaseActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    /**
     * 操作的是SurfaceHolder，所以定义全局变量
     */
    private SurfaceHolder surfaceHolder;

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
        return R.layout.activity_camera;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不能直接操作SurfaceView，需要通过SurfaceView拿到SurfaceHolder
        surfaceHolder = surfaceView.getHolder();

        // 使用SurfaceHolder设置屏幕高亮，注意：所有的View都可以设置 设置屏幕高亮
        surfaceHolder.setKeepScreenOn(true);

        // 使用SurfaceHolder设置把画面或缓存 直接显示出来
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        /**
         * 由于要观察SurfaceView生命周期，所以需要设置监听
         * 此监听不一样，是addCallback
         *
         */
        surfaceHolder.addCallback(callback);
    }

    /**
     * 定义相机对象
     */
    private Camera camera;

    /**
     * 定义SurfaceView监听回调
     */
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {

        /**
         * SurfaceView被创建了
         * @param holder
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            /**
             * 只有在SurfaceView生命周期方法-->SurfaceView被创建后在打开相机
             * 以前我在 onResume 之前去打开相机，结果报错了，所以只有在这里打开相机，才是安全🔐
             */
            // Camera要选择hardware.Camera，因为Camera属于硬件hardware
            // Camera.open(1); // 这了传入的值，可以指定为：前置摄像头/后置摄像头
            camera = Camera.open(0);

            /**
             * 设置Camera与SurfaceHolder关联，Camera的数据让SurfaceView显示
             */
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "相机设置预览失败");
            }

            /**
             * 开始显示
             */
            camera.startPreview();
        }

        /**
         * SurfaceView发生改变了
         * @param holder
         * @param format
         * @param width
         * @param height
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // 以后做具体业务功能的时候，才会用到改变的值来处理
            // ......
        }

        /**
         * SurfaceView被销毁了
         * @param holder
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            /**
             * SurfaceView被销毁后，一定要释放硬件资源，Camera是硬件
             */
            camera.stopPreview(); // (一定要有，不然只release也可能出问题)
            camera.release();
            camera = null;
            System.gc();
        }
    };

    /**
     * 拍照
     */
    public void takepicture(View view) {
        if (null != camera) {
            /**
             * 参数一：相机的快门，咔嚓，这里用不到
             * 参数二：相机原始的数据
             * 参数三：相机处理后的数据
             */
            camera.takePicture(null, null, pictureCallback);
        }
    }

    /**
     * 相机处理后的数据回调
     */
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        /**
         * 拍照完成，可以拿到数据了
         * @param data 数据
         * @param camera
         */
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 写在指定目录 sdcard 中去
            File file = new File(Environment.getExternalStorageDirectory(), "pictureCallback.jpg");
            try {
                // 字节文件输出流，把byte[]数据写入到文件里面去
                OutputStream os = new FileOutputStream(file);
                os.write(data);

                // 关闭字节文件输出流
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "保存失败");
                Toast.makeText(CameraActivity.this, "照片保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 对焦
     */
    public void focus(View view) {
        // 对焦都是硬件设备来完成的
        camera.autoFocus(null);
    }

    /**
     * 当Activity被销毁的时候，一定要移除监听
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ">>>> onDestroy()");
        if (null != callback) surfaceHolder.removeCallback(callback);
    }
}
