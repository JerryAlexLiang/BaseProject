package com.liang.module_core.utils;

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

import com.liang.module_core.R;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 创建日期：2019/1/30 on 15:14
 * 描述:
 * 作者: liangyang
 */
public class FileUtil {

    public static boolean isSDCardAlive() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

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

    public static boolean delete(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String c : children) {
                boolean success = delete(new File(file, c));
                if (!success) {
                    return false;
                }
            }
        }
        return file.delete();
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
    public static synchronized void recursionDeleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
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
            ToastUtil.setCustomToast(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.core_icon_true),
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
            ToastUtil.setCustomToast(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.core_icon_true),
                    true, "保存成功~", Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化单位
     */
    public static String formatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0KB";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 存在沙盒中的path不能直接使用new File(path)形式生成文件 ，可以通过以下方式转换下
    public static String getRealPath(Context context,String path) {
        if (PictureMimeType.isContent(path)){
            Uri uri = Uri.parse(path);
            try {
                File imgFile = context.getExternalFilesDir("image");
                if (!imgFile.exists()){
                    imgFile.mkdir();
                }
                try {
                    File file = new File(imgFile.getAbsolutePath() + File.separator +
                            System.currentTimeMillis() + ".jpg");
                    // 使用openInputStream(uri)方法获取字节输入流
                    InputStream fileInputStream = context.getContentResolver().openInputStream(uri);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while (-1 != (byteRead = fileInputStream.read(buffer))) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    // 文件可用新路径 file.getAbsolutePath()
                    path=file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }
}

