package com.trsoft.catmovie.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.catmovie.LoginActivity;
import com.trsoft.catmovie.constant.Constant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Adim on 2018/3/11.
 */

public abstract class BaseLoginFragment extends BaseFragment {

    private boolean isLogin() {
        String token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        if (Validator.isNotEmpty(token)) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!isLogin()){
            startActivity(new Intent(activity, LoginActivity.class));
        }
        init();
    }
}
