package com.trsoft.catmovie.api;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.catmovie.entity.MusicBeanResponse;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 呆呆 on 2018/6/9.
 */

public interface MusicApi {

    //等到分类列表音乐
    @GET("qqmusic.php")
    Observable<ApiResultBean<MusicBeanResponse>> getMusicSort();

    //显示分类下的歌曲
    @GET("kugoumusiclist.php")
    Observable<ApiResultBean<MusicBeanResponse>> getSortDetails(@Query("path") String path);

}
