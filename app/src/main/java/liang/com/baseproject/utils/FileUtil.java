package liang.com.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.SinglePictureActivity;
import liang.com.baseproject.app.MyApplication;

/**
 * 创建日期：2019/1/30 on 15:14
 * 描述:
 * 作者: liangyang
 */
public class FileUtil {

    private static File imageFile;

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //递归删除文件夹下的数据
    public static synchronized void recursionDeleteFile(String filePath){
        File file = new File(filePath);
        if (!file.exists()) return;

        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File subFile : files){
                String path = subFile.getPath();
                recursionDeleteFile(path);
            }
        }
        //删除文件
        file.delete();
    }

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 把batmap 转file
     */
    public static File translateBitmapAsFile(Bitmap bitmap, String filePath) {
        //将要保存图片的路径
        File file = new File(filePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 下载保存图片到本地-单张图片源
     */
    public static void saveImage(Context context, ImageView imageView, String fileName) {
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        //将Bitmap 转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BaseProjectJerry");
        if (!dir.exists()) {
            dir.mkdir();
        }
//        File file = new File(dir, fileName.substring(0, 16) + ".png");
        File file = new File(dir, fileName + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);
            fos.flush();
            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            ToastUtil.setCustomToast(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_true),
                    true, "保存成功~", Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据URL下载图片到本地
     */
    public static Bitmap saveImageByUrl(String url) {
        URL imageUrl = null;
        Bitmap bitmap = null;
        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            //将URL转为Bitmap
            HttpURLConnection connection = (HttpURLConnection) (imageUrl != null ? imageUrl.openConnection() : null);
            assert connection != null;
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
//                    translateBitmapToFile(context, bitmap, fileName);
//            //将Bitmap 转换成二进制，写入本地
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BaseProjectJerry");
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            File file = new File(dir, fileName + ".png");
//            try {
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(byteArray, 0, byteArray.length);
//                fos.flush();
//                //用广播通知相册进行更新相册
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri uri = Uri.fromFile(file);
//                intent.setData(uri);
//                context.sendBroadcast(intent);
//                ToastUtil.setCustomToast(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_true),
//                        true, "保存成功~", Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void translateBitmapToFile(Context context, Bitmap bitmap, String fileName) {
        //将Bitmap 转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BaseProjectJerry");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, fileName + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);
            fos.flush();
            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            ToastUtil.setCustomToast(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_true),
                    true, "保存成功~", Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

