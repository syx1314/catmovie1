package com.trsoft.catmovie.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trsoft.app.lib.ActivitySupport;
import com.trsoft.app.lib.http.ApiException;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.ApiService;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.rx.RxApiManager;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.catmovie.LoginActivity;
import com.trsoft.catmovie.constant.Constant;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adim on 2018/3/11.
 */

public abstract class BaseLoginActivity extends BaseActivity {

    private boolean isLogin() {
        String token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        if (Validator.isNotEmpty(token)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isLogin()){
            startActivity(new Intent(activity, LoginActivity.class));
            finish();
        }
    }
}
