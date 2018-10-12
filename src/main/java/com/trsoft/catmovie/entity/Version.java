package com.trsoft.catmovie.entity;

/**
 * Created by 呆呆 on 2018/6/30.
 */

public class Version {
    private String url;//跳转的地址
    private String isFocus;//是否强制
    private String isUpdate;
    private String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getIsFocus() {
        return "1".equals(isFocus)?true:false;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }

    public boolean getIsUpdate() {
        return "1".equals(isUpdate)?true:false;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getContent() {
        return content;
    }
}
