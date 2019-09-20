package liang.com.baseproject.utils;

import android.os.Environment;
import android.text.format.Formatter;

import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;

import liang.com.baseproject.app.MyApplication;

/**
 * 创建日期：2019/2/21 on 17:02
 * 描述: 缓存工具类
 * 作者: liangyang
 */
public class CacheCleanUtil {

    /**
     * 获取系统默认缓存文件夹
     * 优先返回SD卡中的缓存文件夹
     */
    public static String getCacheDir() {
        File cacheFile = null;
        if (FileUtil.isSDCardAlive()) {
            cacheFile = Utils.getAppContext().getExternalCacheDir();
        }
        if (cacheFile == null) {
            cacheFile = Utils.getAppContext().getCacheDir();
        }
        return cacheFile.getAbsolutePath();
    }

    /**
     * 获取总缓存大小(获取系统默认缓存文件夹内的缓存大小)
     *
     * @return cacheDir目录下文件总大小
     */
    public static String getTotalCacheSize() {
        long cacheSize = getFolderSize(MyApplication.mContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(Objects.requireNonNull(MyApplication.mContext.getExternalCacheDir()));
        }
//        return Formatter.formatFileSize(MyApplication.mContext, cacheSize);
        return FileUtil.formatSize(cacheSize);
    }

    /**
     * 清除所有缓存
     */
    public static void cleanAllCache() {
        deleteDir(MyApplication.mContext.getCacheDir());
        if (FileUtil.isSDCardAlive()) {
            deleteDir(MyApplication.mContext.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory()) {
            String[] children = cacheDir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(cacheDir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return cacheDir != null && cacheDir.delete();
    }

    @Deprecated
    private static String getFormatSize(long cacheSize) {
        double kiloByte = cacheSize / 1024;
        if (kiloByte < 1) {
            return cacheSize + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraByte = gigaByte / 1024;
        if (teraByte < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(megaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";

        }
        return "没有缓存";
    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }
}
