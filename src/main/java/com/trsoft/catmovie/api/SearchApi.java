package com.trsoft.catmovie.api;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.catmovie.entity.AllVideoBean;
import com.trsoft.catmovie.entity.SearchFilmTvResponse;
import com.trsoft.catmovie.entity.TCMovieBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Adim on 2018/5/13.
 * 搜索
 */

public interface SearchApi {
    @GET("seacher.php")
    Observable<ApiResultBean<SearchFilmTvResponse>> search(@Query("wd") String keyWord);

    @GET("qiang.php")
    Observable<ApiResultBean<List<TCMovieBean>>>  getTCMovie();


    @GET("search_all_movie/movieAllSearch.php")
    Observable<ApiResultBean<List<AllVideoBean>>> getAllVideo(@Query("p") String p,@Query("wd") String keyWord);



}
