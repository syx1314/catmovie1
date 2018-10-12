package com.trsoft.catmovie;

import android.os.Bundle;
import android.widget.EditText;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.base.BaseLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeaveWordActivity extends BaseLoginActivity {

    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected int getLayout() {
        return R.layout.activity_leave_word;
    }



    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if(Validator.isNotEmpty(etContent.getText().toString())){
           Subscribe(getApiService(LoginApi.class).leaveWord(etContent.getText().toString()), new IApiReturn<String>() {
               @Override
               public void run(ApiResultBean<String> apiResult) {
                   DialogUtil.showAlert(activity, apiResult.getMessage(), new CommonCallback<Boolean>() {
                       @Override
                       public void onCallBack(Boolean data) {
                           finish();
                       }
                   });
               }

               @Override
               public void error(String message) {
                   DialogUtil.showAlert(activity,message,null);
               }
           });
        }else {
            DialogUtil.showAlert(activity,etContent.getHint().toString(),null);
        }
    }
}
