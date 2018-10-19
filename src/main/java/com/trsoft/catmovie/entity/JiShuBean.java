package com.trsoft.catmovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 资源网集数
 * Created by Administrator on 2018/10/12 0012.
 */

public class JiShuBean implements Parcelable{
    private List<String> m3u8;
    private List<String> mp4;

    protected JiShuBean(Parcel in) {
        m3u8 = in.createStringArrayList();
        mp4 = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(m3u8);
        dest.writeStringList(mp4);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JiShuBean> CREATOR = new Creator<JiShuBean>() {
        @Override
        public JiShuBean createFromParcel(Parcel in) {
            return new JiShuBean(in);
        }

        @Override
        public JiShuBean[] newArray(int size) {
            return new JiShuBean[size];
        }
    };

    public List<String> getM3u8() {
        return m3u8;
    }

    public List<String> getMp4() {
        return mp4;
    }
}
