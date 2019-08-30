package liang.com.baseproject.helperDao;

import java.io.File;
import java.util.List;

import liang.com.baseproject.gen.DaoSession;
import liang.com.baseproject.gen.ReadLaterBeanDao;
import liang.com.baseproject.mine.entity.ReadLaterBean;
import liang.com.baseproject.utils.FileUtil;

/**
 * 创建日期：2019/8/29 on 18:41
 * 描述: GreenDao - 数据库 - 稍后阅读 - 工具类
 * 作者: liangyang
 */
public class ReadLaterBeanDaoHelpter {

    private static volatile ReadLaterBeanDaoHelpter sInstance;
    private static DaoSession daoSession;
    private static ReadLaterBeanDao readLaterBeanDao;

    public static ReadLaterBeanDaoHelpter getInstance() {
        if (sInstance == null) {
            synchronized (ReadLaterBeanDaoHelpter.class) {
                if (sInstance == null) {
                    sInstance = new ReadLaterBeanDaoHelpter();
                    daoSession = DaoDbHelper.getInstance().getDaoSession();
                    readLaterBeanDao = daoSession.getReadLaterBeanDao();
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加到本地的稍后阅读
     */
    public void saveReaderLaterBean(ReadLaterBean readLaterBean) {
        readLaterBeanDao.insertOrReplace(readLaterBean);
    }

    /**
     * 删除添加到本地的稍后阅读
     */
    public void removeReaderLaterBean(ReadLaterBean readLaterBean) {
        File file = new File(readLaterBean.getId());
        if (file.exists()) {
            //本地数据库删除
            readLaterBeanDao.delete(readLaterBean);
            //删除本地文件
            FileUtil.deleteFile(readLaterBean.getId());
        }
    }

    /**
     * 删除所有的添加到本地的稍后阅读
     */
    public void removeAllReaderLaterBean() {
        readLaterBeanDao.deleteAll();
    }

    /**
     * 查询一个稍后阅读
     */
    public ReadLaterBean findReadLaterByTitle(String title){
        ReadLaterBean readLaterBean = readLaterBeanDao.queryBuilder().where(ReadLaterBeanDao.Properties.Title.eq(title)).unique();
        return readLaterBean;
    }

    /**
     * 查询所有的添加到本地的稍后阅读
     */
    public List<ReadLaterBean> findAllReadLaters(){
        List<ReadLaterBean> readLaterBeanList = readLaterBeanDao.queryBuilder().orderDesc(ReadLaterBeanDao.Properties.Id).list();
        return readLaterBeanList;
    }
}
