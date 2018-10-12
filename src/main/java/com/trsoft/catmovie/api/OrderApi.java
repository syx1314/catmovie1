package com.trsoft.catmovie.api;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.catmovie.entity.TaoCanBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Adim on 2018/5/12.
 */

public interface OrderApi {

    @GET("pay.php")
    Observable<ApiResultBean<List<TaoCanBean>>> getTaoCanType(@Query("type") String type);

}
