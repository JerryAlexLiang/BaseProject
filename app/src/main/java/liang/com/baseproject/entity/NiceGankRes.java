package liang.com.baseproject.entity;

import java.util.List;
/**
 * 创建日期：2019/2/25 on 15:06
 * 描述:  枝干API - 福利接口返回数据-颜如玉
 * 作者: liangyang
 */
public class NiceGankRes {

    private boolean error;
    private List<GankRes> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankRes> getResults() {
        return results;
    }

    public void setResults(List<GankRes> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "NiceGankRes{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
