package com.liang.module_laboratory.select_list;

import java.io.Serializable;

/**
 * 创建日期: 2021/1/19 on 11:18 AM
 * 描述:
 * 作者: 杨亮
 */
public class BookBean implements Serializable {

    private String name;
    private Long id;

    public BookBean() {
    }

    public BookBean(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BookBean setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BookBean setId(Long id) {
        this.id = id;
        return this;
    }
}
