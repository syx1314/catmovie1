package com.trsoft.catmovie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.base.BaseActivity;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.entity.LoginBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private CountDownTimer timer;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 4) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
//                        ToastUtils.showShort(RegisterActivity.this,"提交验证码成功");
                        Log.e("提交验证码成功", "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
//                        Log.e("获取验证码成功","获取验证码成功");
//                        DialogUtil.showAlert(activity, "获取验证码成功", null);
//                        ToastUtils.showShort(RegisterActivity.this,"获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @OnClick(R.id.tv_send_code)
    public void onSendCode() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                tvSendCode.setText(l / 1000 + "");
                tvSendCode.setEnabled(false);
                tvSendCode.setClickable(false);
            }

            @Override
            public void onFinish() {
                tvSendCode.setClickable(true);
                tvSendCode.setEnabled(true);
                tvSendCode.setText(getString(R.string.login_code));
            }
        };
        String phone = etPhone.getText().toString();
        if (Validator.isNotEmpty(phone)) {
            if (Validator.isMobile(phone)) {
//                loginPresenter.sendCode(phone);
                SMSSDK.getVerificationCode("+86", phone);
                timer.start();
            } else {
                DialogUtil.showAlert(activity, "手机号有误", null);
            }
        } else {
            DialogUtil.showAlert(activity, "请输入手机号", null);
        }
    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        if (Validator.isNotEmpty(phone) && Validator.isNotEmpty(code)) {
//                loginPresenter.login(phone, code);
            Subscribe(getApiService(LoginApi.class).login("submit",phone, code), new IApiReturn<LoginBean>() {
                @Override
                public void run(ApiResultBean<LoginBean> apiResult) {
                    if (isSuccess(apiResult.getCode())) {
                        DialogUtil.showAlert(activity, apiResult.getMessage(), null);
                        if(apiResult.getData()!=null) {
                            PreferenceUtils.getInstance().saveData(Constant.TOKEN, apiResult.getData().getU_token());
                        }
                        finish();
                    } else {
                        error(apiResult.getMessage());
                    }
                }

                @Override
                public void error(String message) {
                    DialogUtil.showAlert(activity, message, null);
                }
            });

        } else {
            DialogUtil.showAlert(activity, "请输入手机号", null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

