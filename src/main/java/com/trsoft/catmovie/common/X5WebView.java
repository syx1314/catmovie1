package com.trsoft.catmovie.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;

import com.trsoft.catmovie.R;
import com.trsoft.catmovie.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import static com.trsoft.app.lib.BaseApplication.mContext;

public class X5WebView extends WebView {
    private  String htmlContent;
    private  String[] stringArray;
    private  String token;
    TextView title;
    Context context;
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            MyLog.e("加载url*****" + url);
            if (url.startsWith("tenvideo2://") || url.startsWith("youku://") || url.startsWith("hntvmobile") || url.startsWith("letvclient") || url.startsWith("bilibili") || url.startsWith("vipshop://") || url.contains("jak.yqcy988.com")) {
                return true;
            }

            if (url.startsWith("alipays://")) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent;
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    // intent.setSelector(null);
                    context.startActivity(intent);

                } catch (Exception e) {

                }
                return true;
            }

//
//            try {
//                URL url1 = new URL(url);
//                String host = url1.getHost();
//                for (int i=0;i<stringArray.length;i++){
//                    if (host.contains(stringArray[i])) {
//                        view.loadUrl(url);
//                        break;
//                    }
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            MyLog.e("加载地址1111111" + s);
            DialogUtil.showProgressByApi(((BaseApplication) mContext).getCurActivity(), true);

        }

        @Override
        public void onPageFinished(WebView view, String s) {
            super.onPageFinished(view, s);
            MyLog.e("加载地址" + s);

//            String s1 = s.replace("m.", "");
//            htmlContent.replace("s1",s);
            htmlContent = "<iframe  allowtransparency=\"true\"  mozallowfullscreen=\"true\"  webkitallowfullscreen=\"true\"  allowfullscreen=true allowfullscreen=\"true\" id=\"iframepage\" marginwidth=\"0\" marginheight=\"0\" hspace=\"0\" vspace=\"0\" frameborder=\"0\" scrolling=\"no\" src=\"http://vip.hooshop.cn/index1.php?url=" + s + "&token=" + token + "\" height=\"200px\" width=\"100%\"></iframe>";

            MyLog.e("加载url*****onPageFinished页面加载完毕" + htmlContent);
            String fun = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) {  aResult.push(aEle[i]); } }; return aResult; }";
            String aqyfun = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('section'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) {  aResult.push(aEle[i]); } }; return aResult; }";
            String ww = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('a');  var i=0; for(i<0;i<aEle.length;i++) {aEle[i].value=aEle[i].value.replace(\"//\",\"\");  }; }";


//            if (s.contains("iqiyi")) {
//                String fun71="javascript:function hideOther() { document.getElementById('videoZone').innerHTML='"+htmlContent+"';}";
//                view.loadUrl(fun71);
//                view.loadUrl(ww);
//
////				String fun2 = "javascript:function hideOther() {getClass(document,'m-box m-box-top')[0].innerHTML='" + htmlContent + "';}";
////                String fun2 = "javascript:function hideOther() {getClass(document,'m-video-player')[0].innerHTML='" + htmlContent + "';}";
////                view.loadUrl(fun2);
//            }
            if (s.contains("qq.com")) {
                view.loadUrl(fun);
                String fun3 = "javascript:function hideOther() { getClass(document,'site_player_inner')[0].innerHTML='" + htmlContent + "';}";
                view.loadUrl(fun3);
            }
            if (s.contains("youku.com")) {
                view.loadUrl(fun);
                String fun4 = "javascript:function hideOther() { getClass(document,'player-box')[0].innerHTML='" + htmlContent + "';}";
                view.loadUrl(fun4);
            }
            if (s.contains("mgtv.com")) {
                view.loadUrl(fun);
                String fun5 = "javascript:function hideOther() { getClass(document,'video-area')[0].innerHTML='" + htmlContent + "';}";
                view.loadUrl(fun5);
            }
            if (s.contains("sohu.com")) {
                view.loadUrl(fun);
                String fun6 = "javascript:function hideOther() { getClass(document,'player')[0].innerHTML='" + htmlContent + "';}";
                view.loadUrl(fun6);
            }
            if (s.contains("le.com")) {
                view.loadUrl(fun);
//				String fun7="javascript:function hideOther() { document.getElementById('j-player').innerHTML='"+htmlContent+"';}";
                String fun7 = "javascript:function hideOther() { getClass(document,'column play')[0].innerHTML='" + htmlContent + "';}";
                view.loadUrl(fun7);
            }

//			if(s.contains("bilibili.com")){
//				String fun8="javascript:function hideOther() { getClass(document,'player-container')[0].innerHTML='"+htmlContent+"';}";
//				view.loadUrl(fun8);
//			}
            view.loadUrl("javascript:hideOther();");
//            try {
//                InputStream is = context.getAssets().open("tubiaoapp.user.js");
//                int lenght = is.available();
//                byte[]  buffer = new byte[lenght];
//                is.read(buffer);
//                String result  = new String(buffer, "utf8");
//                MyLog.e("加载的 js---"+result);
//			view.loadUrl(result);
//			view.loadUrl("javascript:play();");
//            } catch (IOException e) {
//                e.printStackTrace();
            //  }
//			view.loadUrl("file:///android_asset/tubiaoapp.user.js");
//            view.loadUrl("javascript:play();");
            DialogUtil.dismissProgress();
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.context = arg0;
        this.setWebViewClient(client);
//		 this.setWebChromeClient(new WebChromeClient(){});
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);
        token = PreferenceUtils.getInstance().getString(Constant.TOKEN);

        stringArray = context.getResources().getStringArray(R.array.filter_url);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计

    }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
        this.context = arg0;
    }

}
