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
     * 查询所有的添加到本地的稍后阅读(根据加入时间倒序排列)
     */
    public static List<ReadLaterBean> findAllReadLaters() {
        List<ReadLaterBean> readLaterBeanList = MyApplication.getDaoSession().getReadLaterBeanDao().queryBuilder().orderDesc(ReadLaterBeanDao.Properties.Time).list();
        return readLaterBeanList;
    }

    /**
     * 分页查询数据添加到本地的稍后阅读(根据加入时间倒序排列)
     * @param offset   页数
     * @param count    每页数量
     * @return
     */
    public static List<ReadLaterBean> getReadLatersByPage(int offset,int count){
        List<ReadLaterBean> readLaterBeanList = MyApplication.getDaoSession().queryBuilder(ReadLaterBean.class).orderDesc(ReadLaterBeanDao.Properties.Time)
                .offset(offset * count).limit(count).list();
        return readLaterBeanList;
    }
}
