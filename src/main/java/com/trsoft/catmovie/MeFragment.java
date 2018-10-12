package com.trsoft.catmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DateUtil;
import com.trsoft.app.lib.utils.DeviceUtils;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.DisplayUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.base.BaseLoginFragment;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.entity.LoginBean;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Adim on 2018/3/11.
 */

public class MeFragment extends BaseLoginFragment {
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private LoginBean data;

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        tvVersion.setText(DeviceUtils.getVersionName(activity));
    }

    @Override
    public void onResume() {
        super.onResume();
        Subscribe(getApiService(LoginApi.class).login("user", null, null), new IApiReturn<LoginBean>() {
            @Override
            public void run(ApiResultBean<LoginBean> apiResult) {
                if (isSuccess(apiResult.getCode()) || apiResult.getCode() == 202) {
                     data = apiResult.getData();
                    ValidatorUtil.setTextVal(tvUserName, data.getU_name());
                    ValidatorUtil.setTextVal(tvPoints, data.getU_points());
                    if (Validator.isNotEmpty(data.getU_end())) {
                        tvEndTime.setText("过期时间:" + DateUtil.date2Str(new Date(Long.valueOf(data.getU_end()) * 1000), "yyyy-MM-dd"));
                    } else {
                        tvEndTime.setText("会员过期");
                    }
                } else {
                    if (apiResult.getCode() == 4002 || apiResult.getCode() == 201) {
                        startActivity(new Intent(activity, LoginActivity.class));
                    } else {
                        error(apiResult.getMessage());
                    }
                }
            }

            @Override
            public void error(String message) {
                DialogUtil.showAlert(activity, message, null);
            }
        });
    }

    @OnClick({R.id.tv_vip_change, R.id.tv_question, R.id.tv_leave_word, R.id.tv_km_change,R.id.tv_yaoqing, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vip_change:
                startActivity(new Intent(activity, BuyVipActivity.class));
                break;
            case R.id.tv_question:
                Intent intent = new Intent(activity, LoadHtmlActivity.class);
                intent.putExtra(Constant.BUNDLE_STRING, "常见问题");
                intent.putExtra(Constant.BUNDLE_URL, "http://catmovie.cn/api/wenti.html");
                startActivity(intent);
                break;
            case R.id.tv_km_change:
                startActivity(new Intent(activity, KmRechangeActivity.class));
                break;
            case R.id.tv_yaoqing:

                shareText("邀请好友得积分","","http://www.catmovie.cn/ucenter/reg.php?tg="+data.getU_id());
                break;
            case R.id.tv_leave_word:
                startActivity(new Intent(activity, LeaveWordActivity.class));
                break;
            case R.id.btn_logout:
                PreferenceUtils.getInstance().clear();
                startActivity(new Intent(activity, LoginActivity.class));

                break;
        }
    }


    /**
     * 分享文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     */
    private void shareText(String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }
}
