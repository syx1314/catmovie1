package com.trsoft.catmovie;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;

import android.widget.LinearLayout;
import android.widget.Toast;


import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.catmovie.base.BaseActivity;
import com.trsoft.catmovie.base.BaseLoginActivity;
import com.trsoft.catmovie.common.CommonTitle;
import com.trsoft.catmovie.common.WebViewJavaScriptFunction;
import com.trsoft.catmovie.common.X5WebView;
import com.trsoft.catmovie.constant.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class LoadHtmlActivity extends BaseLoginActivity {

    @BindView(R.id.ct_title)
    CommonTitle ctTitle;
    @BindView(R.id.webView)
    X5WebView webView;
    @BindView(R.id.btn_play)
    LinearLayout btnPlay;

    String title;
    String url;


    @Override
    protected int getLayout() {
        return R.layout.activity_load_html;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        title = getIntent().getStringExtra(Constant.BUNDLE_STRING);
        url = getIntent().getStringExtra(Constant.BUNDLE_URL);
        boolean isjx = getIntent().getBooleanExtra("isjx",false);
        if(isjx){
            btnPlay.setVisibility(View.VISIBLE);
        }

        if (Validator.isNotEmpty(title)) {
            ctTitle.setTvTitle(title);
            ctTitle.setClickFun(new CommonTitle.IClickListener() {
                @Override
                public void leftOclick() {}
                @Override
                public void rightOclick() {
                    finish();
                }
            });
        }
        if (Validator.isNotEmpty(url)) {
            webView.loadUrl(url);
            // 146339127142985693
        }

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.onDestroy();
        webView.destroy();
    }
    @OnClick(R.id.btn_play)
    public void play(View view){
        String token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        Intent intent = new Intent(activity, LoadHtmlActivity.class);
        intent.putExtra(Constant.BUNDLE_STRING, title);
        intent.putExtra(Constant.BUNDLE_URL, "http://vip.catmovie.cn/index1.php?url="+webView.getUrl()+"&token="+token);
        activity.startActivity(intent);
    }
}
