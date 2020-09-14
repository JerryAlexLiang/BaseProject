package liang.com.baseproject.entity;

import com.liang.module_core.mvp.MVPBaseBean;

/**
 * 创建日期: 2020/9/14 on 3:45 PM
 * 描述: 首页功能入口列表
 * 作者: 杨亮
 */
public class HomeFunctionBean extends MVPBaseBean {

    private int functionId;
    private int functionIconId;
    private String functionName;

    public HomeFunctionBean() {
    }

    public HomeFunctionBean(int functionId, int functionIconId, String functionName) {
        this.functionId = functionId;
        this.functionIconId = functionIconId;
        this.functionName = functionName;
    }

    public int getFunctionId() {
        return functionId;
    }

    public HomeFunctionBean setFunctionId(int functionId) {
        this.functionId = functionId;
        return this;
    }

    public int getFunctionIconId() {
        return functionIconId;
    }

    public HomeFunctionBean setFunctionIconId(int functionIconId) {
        this.functionIconId = functionIconId;
        return this;
    }

    public String getFunctionName() {
        return functionName;
    }

    public HomeFunctionBean setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }


}
