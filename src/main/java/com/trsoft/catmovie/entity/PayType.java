package com.trsoft.catmovie.entity;

/**
 * Created by Adim on 2018/5/12.
 */

public class PayType {
    private String id;
    private int  resIcon;
    private String text;
    private Boolean check;//是否选中

    public PayType(String id, int resIcon, String text) {
        this.id = id;
        this.resIcon = resIcon;
        this.text = text;
    }

    public PayType(String id, int resIcon, String text, Boolean check) {
        this.id = id;
        this.resIcon = resIcon;
        this.text = text;
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "PayType{" +
                "id='" + id + '\'' +
                ", resIcon=" + resIcon +
                ", text='" + text + '\'' +
                ", check=" + check +
                '}';
    }
}
