package com.liang.module_gank.logic.model;

import java.util.List;

/**
 * 创建日期: 2021/10/20 on 11:14 AM
 * 描述: 新接口api 美女图片
 * https://www.mxnzp.com/api/image/girl/list
 * 作者: 杨亮
 */
public class NewGirlsRes {

    private Integer code;
    private String msg;
    private DataBean data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private Integer page;
        private Integer totalCount;
        private Integer totalPage;
        private Integer limit;
        private List<NewGirlRes> list;

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public List<NewGirlRes> getList() {
            return list;
        }

        public void setList(List<NewGirlRes> list) {
            this.list = list;
        }

    }
}
