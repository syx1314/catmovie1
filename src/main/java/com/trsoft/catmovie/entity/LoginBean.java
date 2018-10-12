package com.trsoft.catmovie.entity;

/** 登录
 * Created by Adim on 2018/5/1.
 */

public class LoginBean {
    private String u_id;
    private String u_name;
    private String u_end;
    private String u_group;
    private String u_token;
    private String u_points;//积分

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_points() {
        return u_points;
    }

    public void setU_points(String u_points) {
        this.u_points = u_points;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_end() {
        return u_end;
    }

    public void setU_end(String u_end) {
        this.u_end = u_end;
    }

    public String getU_group() {
        return u_group;
    }

    public void setU_group(String u_group) {
        this.u_group = u_group;
    }

    public String getU_token() {
        return u_token;
    }

    public void setU_token(String u_token) {
        this.u_token = u_token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "u_name='" + u_name + '\'' +
                ", u_end='" + u_end + '\'' +
                ", u_group='" + u_group + '\'' +
                ", u_token='" + u_token + '\'' +
                '}';
    }
}
