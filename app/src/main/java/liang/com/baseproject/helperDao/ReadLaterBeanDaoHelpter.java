package liang.com.baseproject.helperDao;

import android.support.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;

import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.gen.ReadLaterBeanDao;
import liang.com.baseproject.mine.entity.ReadLaterBean;

/**
 * 创建日期：2019/8/29 on 18:41
 * 描述: GreenDao - 数据库 - 稍后阅读 - 工具类
 * 作者: liangyang
 */
public class ReadLaterBeanDaoHelpter {

    private static ReadLaterBeanDao readLaterBeanDao;

    public static ReadLaterBeanDao getReadLaterBeanDao() {
        return readLaterBeanDao = MyApplication.getDaoSession().getReadLaterBeanDao();
    }

    /**
     * 添加到本地的稍后阅读
     */
    public static void saveReaderLaterBean(ReadLaterBean readLaterBean) {
        MyApplication.getDaoSession().getReadLaterBeanDao().insertOrReplace(readLaterBean);
    }

    /**
     * 删除添加到本地的稍后阅读
     */
    public static void removeReaderLaterBean(ReadLaterBean readLaterBean) {
        //本地数据库删除
        MyApplication.getDaoSession().getReadLaterBeanDao().delete(readLaterBean);
//        MyApplication.getDaoSession().getReadLaterBeanDao().deleteByKey();
    }

    /**
     * 删除所有的添加到本地的稍后阅读
     */
    public static void removeAllReaderLaterBean() {
        MyApplication.getDaoSession().getReadLaterBeanDao().deleteAll();
    }

    /**
     * 查询一个稍后阅读
     */
    public static ReadLaterBean findReadLaterByTitle(String title) {
        ReadLaterBean readLaterBean = MyApplication.getDaoSession().getReadLaterBeanDao().queryBuilder()
                .where(ReadLaterBeanDao.Properties.Title.eq(title)).unique();
        return readLaterBean;
    }

    /**
     * 查询所有的添加到本地的稍后阅读
     */
    public static List<ReadLaterBean> findAllReadLaters() {
        List<ReadLaterBean> readLaterBeanList = MyApplication.getDaoSession().getReadLaterBeanDao().queryBuilder().orderDesc(ReadLaterBeanDao.Properties.Title).list();
        return readLaterBeanList;
    }

    /**
     * 分页查询添加到本地的稍后阅读
     *
     * @param page         页码
     * @param perPageCount 每页数量
     */
    public List<ReadLaterBean> findReadLatersByPage(@IntRange(from = 0) int page, int perPageCount) {
        List<ReadLaterBean> allReadLaters = findAllReadLaters();
        int firstIndex = perPageCount * (page);
        int lastIndex = firstIndex + perPageCount;
        if (allReadLaters.size() - 1 < firstIndex) {
            return new ArrayList<>(0);
        } else {
            List<ReadLaterBean> list = new ArrayList<>(perPageCount);
            if (allReadLaters.size() - 1 <= lastIndex) {
                list.addAll(firstIndex, allReadLaters);
            } else {
                for (int i = firstIndex; i <= lastIndex; i++) {
                    list.add(allReadLaters.get(i));
                }
            }
            return list;
        }
    }

}
