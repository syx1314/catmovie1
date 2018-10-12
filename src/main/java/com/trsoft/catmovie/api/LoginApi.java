package com.trsoft.catmovie.api;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.catmovie.entity.BannerBean;
import com.trsoft.catmovie.entity.LoginBean;
import com.trsoft.catmovie.entity.Version;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Adim on 2018/5/1.
 */

public interface LoginApi {

 @GET("login.php")
 Observable<ApiResultBean<LoginBean>> login(@Query("submit") String submit,@Query("phone") String phone,@Query("code") String code);

 @GET("chongzhi.php")
 Observable<ApiResultBean<String>> exCharge(@Query("c_number") String c_number,@Query("save") String save);

 @FormUrlEncoded
 @POST("book.php")
 Observable<ApiResultBean<String>> leaveWord(@Field("content") String content);

 @GET("adv.php")
 Observable<ApiResultBean<List<BannerBean>>> getBanner();

 @GET("menu.php")
 Observable<ApiResultBean<List<BannerBean>>> getMenu();

@GET("checkVer.php")
Observable<ApiResultBean<Version>> getVersion(@Query("v") String v);

}
