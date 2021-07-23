package liang.com.baseproject.entity;

import java.util.List;

/**
 * 创建日期: 2021/7/23 on 2:43 PM
 * 描述:
 * 作者: 杨亮
 */
public class NiceGankGirlRes {

    private List<GankGirlRes> data;
    private Integer page;
    private Integer page_count;
    private Integer status;
    private Integer total_counts;

    public List<GankGirlRes> getData() {
        return data;
    }

    public NiceGankGirlRes setData(List<GankGirlRes> data) {
        this.data = data;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal_counts() {
        return total_counts;
    }

    public void setTotal_counts(Integer total_counts) {
        this.total_counts = total_counts;
    }

}
