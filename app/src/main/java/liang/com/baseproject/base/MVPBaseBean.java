package liang.com.baseproject.base;

import java.io.Serializable;

import liang.com.baseproject.utils.GsonUtils;
import liang.com.baseproject.utils.JsonFormatUtils;
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
