package com.trsoft.catmovie.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trsoft.app.lib.http.ApiException;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.ApiService;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.rx.RxApiManager;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.catmovie.constant.Constant;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adim on 2018/3/11.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;
    protected Activity activity;
    private int tag;

    protected abstract int getLayout();
    protected abstract void init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View mRootView = inflater.inflate(getLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);

        return mRootView;

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        init();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

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
