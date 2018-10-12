package com.trsoft.catmovie;

import android.widget.EditText;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 卡密充值
 */
public class KmRechangeActivity extends BaseActivity {

    @BindView(R.id.et_jhm)
    EditText etJhm;

    @Override
    protected int getLayout() {
        return R.layout.activity_km_rechange;
    }


    @OnClick(R.id.btn_recharge)
    public void onViewClicked() {

        if (Validator.isNotEmpty(etJhm.getText().toString())) {
            Subscribe(getApiService(LoginApi.class).exCharge(etJhm.getText().toString(), "save"), new IApiReturn<String>() {
                @Override
                public void run(ApiResultBean<String> apiResult) {
                    DialogUtil.showAlert(activity, apiResult.getMessage(), null);
                }

                @Override
                public void error(String message) {
                    DialogUtil.showAlert(activity, message, null);
                }
            });

        } else {
            DialogUtil.showAlert(activity, etJhm.getHint().toString(), null);
        }
    }
}
