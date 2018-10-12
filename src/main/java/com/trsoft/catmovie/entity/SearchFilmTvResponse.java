package com.trsoft.catmovie.entity;

import java.util.List;

/**
 * Created by Adim on 2018/5/13.
 */

public class SearchFilmTvResponse {
    private List<String> mg_href;
    private List<String> mg_img;
    private List<String> mg_title;
    private List<String> mg_year;
    private List<String> title;
    private List<String> img;
    private List<String> jishu;
    private List<String> type;
    private List<String> href;

    public List<String> getMg_href() {
        return mg_href;
    }

    public void setMg_href(List<String> mg_href) {
        this.mg_href = mg_href;
    }

    public List<String> getMg_img() {
        return mg_img;
    }

    public void setMg_img(List<String> mg_img) {
        this.mg_img = mg_img;
    }

    public List<String> getMg_title() {
        return mg_title;
    }

    public void setMg_title(List<String> mg_title) {
        this.mg_title = mg_title;
    }

    public List<String> getMg_year() {
        return mg_year;
    }

    public void setMg_year(List<String> mg_year) {
        this.mg_year = mg_year;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public List<String> getJishu() {
        return jishu;
    }

    public void setJishu(List<String> jishu) {
        this.jishu = jishu;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getHref() {
        return href;
    }

    public void setHref(List<String> href) {
        this.href = href;
    }
}
