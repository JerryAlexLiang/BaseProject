package com.liang.module_core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/12/29 on 15:00
 * 描述: 使用SharedPreferences存储List数据
 * 由于SharedPreferences只能保存Map型的数据，必须要做其他转换才行
 * 作者: liangyang
 */
public class ListDataSave {

    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     */
    public <T> void setDataList(String tag, List<T> dataList) {
        if (null == dataList || dataList.size() <= 0) {
            return;
        }
        Gson gson = new Gson();
        //转换成JSON数据，再保存
        String strJson = gson.toJson(dataList);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * 获取List
     */
    public <T> List<T> getDataList(String tag) {
        List<T> datalist = new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {}.getType());
        return datalist;
    }

//    /**
//     * 保存List
//     */
//    public <AlarmsBean> void setDataList(String tag, List<AlarmsRes.AlarmsBean> dataList) {
//        if (null == dataList || dataList.size() <= 0) {
//            return;
//        }
//        Gson gson = new Gson();
//        //转换成JSON数据，再保存
//        String strJson = gson.toJson(dataList);
//        editor.clear();
//        editor.putString(tag, strJson);
//        editor.commit();
//    }
//
//    /**
//     * 获取List
//     */
//    public <AlarmsBean> List<AlarmsRes.AlarmsBean> getDataList(String tag) {
//        List<AlarmsRes.AlarmsBean> datalist = new ArrayList<AlarmsRes.AlarmsBean>();
//        String strJson = preferences.getString(tag, null);
//        if (null == strJson) {
//            return datalist;
//        }
//        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<AlarmsRes.AlarmsBean>>() {
//        }.getType());
//        return datalist;
//    }


}
