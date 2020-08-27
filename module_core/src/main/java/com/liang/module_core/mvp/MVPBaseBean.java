package com.liang.module_core.mvp;

import com.liang.module_core.utils.GsonUtils;
import com.liang.module_core.utils.JsonFormatUtils;

import java.io.Serializable;

/**
 * 创建日期：2019/8/30 on 13:58
 * 描述: 网络请求的实体类基类
 * 作者: liangyang
 */
public class MVPBaseBean implements Serializable {

    public String toJson() {
//        return new Gson().toJson(this);
        return GsonUtils.toJson(this);
    }

    public String toFormatJson() {
        return JsonFormatUtils.format(toJson());
    }

}
