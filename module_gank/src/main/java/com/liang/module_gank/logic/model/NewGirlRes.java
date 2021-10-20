package com.liang.module_gank.logic.model;

import java.io.Serializable;

/**
 * 创建日期: 2021/10/20 on 2:05 PM
 * 描述:
 * 作者: 杨亮
 */
public class NewGirlRes implements Serializable {

    private String imageUrl;
    private String imageSize;
    private Integer imageFileLength;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public Integer getImageFileLength() {
        return imageFileLength;
    }

    public void setImageFileLength(Integer imageFileLength) {
        this.imageFileLength = imageFileLength;
    }

} 
