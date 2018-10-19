package com.trsoft.catmovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.trsoft.app.lib.utils.Validator;

/**
 * 资源网接口
 * Created by Administrator on 2018/10/12 0012.
 */

public class AllVideoBean  implements Parcelable{
    private String movieName;
    private String movieCover;
    private String movieSort;
    private String movieArea;
    private String updateTime;
    private String status;
    private JiShuBean jishu;


    protected AllVideoBean(Parcel in) {
        movieName = in.readString();
        movieCover = in.readString();
        movieSort = in.readString();
        movieArea = in.readString();
        updateTime = in.readString();
        status = in.readString();
        jishu = in.readParcelable(JiShuBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(movieCover);
        dest.writeString(movieSort);
        dest.writeString(movieArea);
        dest.writeString(updateTime);
        dest.writeString(status);
        dest.writeParcelable(jishu, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllVideoBean> CREATOR = new Creator<AllVideoBean>() {
        @Override
        public AllVideoBean createFromParcel(Parcel in) {
            return new AllVideoBean(in);
        }

        @Override
        public AllVideoBean[] newArray(int size) {
            return new AllVideoBean[size];
        }
    };

    public String getMovieName() {
        if(Validator.isNotEmpty(movieName)&&movieName.contains("影片名称")){
           movieName = movieName.replace("影片名称：", "");
            return  movieName;
        }else {
            return movieName;
        }
    }

    public String getMovieCover() {
        return movieCover.replace(" ","").trim();
    }

    public String getMovieSort() {
        return movieSort;
    }

    public String getMovieArea() {
        return movieArea;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getStatus() {
        if(Validator.isNotEmpty(status)&&status.contains("影片状态")){
            status = status.replace("影片状态：", "");
            return  status.replace(" ","");
        }else {
            return status.replace(" ","");
        }
    }

    public JiShuBean getJishu() {
        return jishu;
    }
}
