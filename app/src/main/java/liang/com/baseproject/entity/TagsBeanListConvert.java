package liang.com.baseproject.entity;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

import liang.com.baseproject.utils.GsonUtils;

/**
 * 创建日期：2019/9/9 on 16:09
 * 描述: 玩Android - API - 返回数据 tags 数据 转换类  implements PropertyConverter<MovieRating, String>
 * 这个转换类实现的PropertyConverter接口，里面有两个方法，很好理解，String转JavaBean 和JavaBean转String。
 * 作者: liangyang
 */
public class TagsBeanListConvert implements PropertyConverter<List<TagsBean>, String> {

    /**
     * String转JavaBean
     */
    @Override
    public List<TagsBean> convertToEntityProperty(String databaseValue) {
        return GsonUtils.parseJsonArrayToBean(databaseValue, TagsBean.class);
    }

    /**
     * JavaBean转String
     */
    @Override
    public String convertToDatabaseValue(List<TagsBean> entityProperty) {
        return GsonUtils.toJson(entityProperty);
    }

}
