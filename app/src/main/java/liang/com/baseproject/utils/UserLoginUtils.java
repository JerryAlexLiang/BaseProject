package liang.com.baseproject.utils;

import com.google.gson.Gson;

import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.entity.Userbean;

import static liang.com.baseproject.Constant.Constant.KEY_BG;
import static liang.com.baseproject.Constant.Constant.KEY_ICON;
import static liang.com.baseproject.Constant.Constant.KEY_LOGIN_JSON;

/**
 * 创建日期：2019/8/27 on 15:57
 * 描述: 用户信息管理工具类-使用SharedPreferences本地化存储
 * 作者: liangyang
 */
public class UserLoginUtils {

    private Userbean mUserbean = null;

    private static class Holder {
        private static final UserLoginUtils INSTANCE = new UserLoginUtils();
    }

    public static UserLoginUtils getInstance() {
        return Holder.INSTANCE;
    }

    private UserLoginUtils() {
        getLoginUserBean();
    }

    public Userbean getLoginUserBean() {
        if (mUserbean == null) {
            String json = (String) SPUtils.get(MyApplication.getAppContext(), KEY_LOGIN_JSON, "");
            mUserbean = new Gson().fromJson(json, Userbean.class);
        }
        return mUserbean;
    }

    /**
     * 登录本地持久化保存登录信息
     */
    public void login(Userbean userbean) {
        mUserbean = userbean;
        String json = new Gson().toJson(userbean);
        SPUtils.put(MyApplication.getAppContext(), KEY_LOGIN_JSON, json);
    }

    /**
     * 登出
     */
    public void logout() {
        mUserbean = null;
//        SPUtils.clear(MyApplication.getAppContext());
        SPUtils.remove(MyApplication.getAppContext(), KEY_LOGIN_JSON);
    }

    /**
     * 更新数据
     */
    public void update(Userbean userbean) {
        mUserbean = userbean;
        SPUtils.put(MyApplication.getAppContext(), KEY_LOGIN_JSON, mUserbean);
    }

    /**
     * 判断是否已登录
     */
    public boolean isLogin() {
        Userbean loginUserBean = getLoginUserBean();
        if (loginUserBean == null) {
            return false;
        }
        if (loginUserBean.getId() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要登录
     */
    public boolean doIfNeedLogin() {
        if (isLogin()) {
            return true;
        } else {
            //跳转登录界面
            LoginActivity.actionStart();
            return false;
        }
    }

    public void setLocalUserIcon(String icon) {
        SPUtils.put(MyApplication.getAppContext(), KEY_ICON, icon);
    }

    public String getLocalUserIcon() {
        return (String) SPUtils.get(MyApplication.getAppContext(), KEY_ICON, "");
    }

    public void setLocalBg(String icon) {
        SPUtils.put(MyApplication.getAppContext(), KEY_BG, icon);
    }

    public String getLocalBg() {
        return (String) SPUtils.get(MyApplication.getAppContext(), KEY_BG, "");
    }
}
