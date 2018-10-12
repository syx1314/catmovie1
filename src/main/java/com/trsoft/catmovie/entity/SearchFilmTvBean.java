package com.trsoft.catmovie.entity;

/**
 * Created by Adim on 2018/5/13.
 * 影视搜索结果包装类
 */

public class SearchFilmTvBean {
    private String title;
    private String img;
    private String year;
    private String href;
    private String type;
    private String jishu;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJishu() {
        return jishu;
    }

    public void setJishu(String jishu) {
        this.jishu = jishu;
    }
}
