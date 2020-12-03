package com.liang.module_eyepetizer.logic.model.test;

import java.io.Serializable;

/**
 * 应用模块: common
 * <p>
 * 类描述: 用于各个组件之间公用的 契约类,需要共同遵守相关规定
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public class BaseCustomViewModel implements Serializable {

    private int sortIndex;

    public int getSortIndex() {
        return sortIndex;
    }

    public BaseCustomViewModel setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
        return this;
    }
}
