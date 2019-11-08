package liang.com.baseproject.utils;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;

import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.entity.UserBean;

import static liang.com.baseproject.Constant.Constant.KEY_BG;
import static liang.com.baseproject.Constant.Constant.KEY_ICON;
import static liang.com.baseproject.Constant.Constant.KEY_LOGIN_JSON;
import static liang.com.baseproject.utils.ResUtils.getResources;

/**
 * 创建日期：2019/8/27 on 15:57
 * 描述: 用户信息管理工具类-使用SharedPreferences本地化存储
 * 作者: liangyang
 */
public class UserLoginUtils {

    private UserBean mUserBean = null;

    private static class Holder {
        private static final UserLoginUtils INSTANCE = new UserLoginUtils();
    }

    public static UserLoginUtils getInstance() {
        return Holder.INSTANCE;
    }

    private UserLoginUtils() {
        getLoginUserBean();
    }

    public UserBean getLoginUserBean() {
        if (mUserBean == null) {
            String json = (String) SPUtils.get(MyApplication.getAppContext(), KEY_LOGIN_JSON, "");
            mUserBean = new Gson().fromJson(json, UserBean.class);
        }
        return mUserBean;
    }

    /**
     * 登录本地持久化保存登录信息
     */
    public void login(UserBean userbean) {
        mUserBean = userbean;
        userbean.setIcon(getLocalUserIcon());
        String json = new Gson().toJson(userbean);
        SPUtils.put(MyApplication.getAppContext(), KEY_LOGIN_JSON, json);
    }

    /**
     * 登出
     */
    public void logout() {
        mUserBean = null;
//        SPUtils.clear(MyApplication.getAppContext());
        SPUtils.remove(MyApplication.getAppContext(), KEY_LOGIN_JSON);
    }

    /**
     * 更新数据
     */
    public void update(UserBean userbean) {
        mUserBean = userbean;
        SPUtils.put(MyApplication.getAppContext(), KEY_LOGIN_JSON, mUserBean);
    }

    /**
     * 判断是否已登录
     */
    public boolean isLogin() {
        UserBean loginUserBean = getLoginUserBean();
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
            onShowToast(getResources().getString(R.string.please_login_first));
            LoginActivity.actionStart();
            return false;
        }
    }

    public void setLocalUserIcon(String icon) {
        SPUtils.put(MyApplication.getAppContext(), KEY_ICON, icon);
//        mUserBean.setIcon(icon);
//        String json = new Gson().toJson(mUserBean);
//        SPUtils.put(MyApplication.getAppContext(), KEY_LOGIN_JSON, json);
    }

    public String getLocalUserIcon() {
        return (String) SPUtils.get(MyApplication.getAppContext(), KEY_ICON, "");
    }

    public void setLocalBg(String icon) {
        SPUtils.put(MyApplication.getAppContext(), KEY_BG, icon);
//        mUserBean.setBgIcon(icon);
//        String json = new Gson().toJson(mUserBean);
//        SPUtils.put(MyApplication.getAppContext(), KEY_LOGIN_JSON, json);
    }

    public String getLocalBg() {
        return (String) SPUtils.get(MyApplication.getAppContext(), KEY_BG, "");
    }

    public void onShowToast(String content) {
        ToastUtil.setCustomToast(MyApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                true, content, getResources().getColor(R.color.toast_bg), Color.WHITE, Gravity.CENTER, Toast.LENGTH_SHORT);
    }
}
