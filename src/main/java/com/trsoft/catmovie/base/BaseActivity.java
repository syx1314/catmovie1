package com.trsoft.catmovie.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.trsoft.app.lib.ActivitySupport;

import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.catmovie.R;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.app.lib.http.ApiException;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.ApiService;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.rx.RxApiManager;
import com.trsoft.app.lib.utils.MyLog;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adim on 2018/3/11.
 */

public abstract class BaseActivity extends ActivitySupport {

    protected abstract int getLayout();
    protected Activity activity;
    private int tag = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        activity=this;
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.red).init();
    }



    /**
     * 得到API的Service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T getApiService(Class<T> service) {
        return ApiService.getInstance(Constant.BASEURL).create(service);
    }

    public <T> void Subscribe(final Observable<ApiResultBean<T>> observable, final IApiReturn<T> apiReturn) {
        tag = this.hashCode();
        RxApiManager.instance().add(tag, observable.hashCode(), observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ApiResultBean<T>>() {
            @Override
            public void onCompleted() {
                RxApiManager.instance().cancel(tag, observable.hashCode());
            }

            @Override
            public void onError(Throwable e) {
                MyLog.e(e + "----------我的错误" + e.getMessage() + "----");
                if (ApiException.handleException(e).message != null) {
                    apiReturn.error(ApiException.handleException(e).message);
                }
                RxApiManager.instance().cancel(tag);
            }

            @Override
            public void onNext(ApiResultBean<T> tApiResult) {
                if (tApiResult != null) {
                    apiReturn.run(tApiResult);
                }
            }
        }));
    }



    protected boolean isSuccess(int code) {
        if (code == 200) {
            return true;
        }
        return false;
    }

    public void cancelRequest() {
        RxApiManager.instance().cancel(tag);
    }
}
