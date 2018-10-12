package com.trsoft.catmovie;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DeviceUtils;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.base.BaseActivity;
import com.trsoft.catmovie.entity.Version;


import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    //定义数组来存放Fragment界面
//    private Class fragmentArray[] = {HomeFragment.class, SpecialtyFragment.class, StoryFragment.class, MeFragment.class};
    private Class fragmentArray[] = {HomeFragment.class, StoryFragment.class,JokeFragment.class, MeFragment.class};

    //定义数组来存放按钮图片
//    private int mImages[] = {R.drawable.bt_home_selector, R.drawable.bt_tab2_selector, R.drawable.bt_tab3_selector,
//            R.drawable.bt_tab4_selector};
    private int mImages[] = {R.drawable.bt_home_selector, R.drawable.bt_tab3_selector,R.drawable.bt_joke_selector,
            R.drawable.bt_tab4_selector};

    //Tab选项卡的文字
//    private String mTextviewArray[] = {"首页", "特产", "发现", "我的"};
    private String mTextviewArray[] = {"首页", "小说","搞笑","我的"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        checkVer();

    }

    private void checkVer() {
        String versionCode = DeviceUtils.getVersionCode(activity);
        Subscribe(getApiService(LoginApi.class).getVersion(versionCode), new IApiReturn<Version>() {
            @Override
            public void run(ApiResultBean<Version> apiResult) {
                if(isSuccess(apiResult.getCode())){
                    final Version data = apiResult.getData();
                    if(data.getIsUpdate()){

                        DialogUtil.showAlertCusTitle(activity, "有新版本", data.getContent(), "更新", "忽略", new CommonCallback<Boolean>() {
                            @Override
                            public void onCallBack(Boolean b) {
                              if(b){
                                  //去更新
                                  Uri uri = Uri.parse(data.getUrl());
                                  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                  startActivity(intent);
                              }else {
                                  if(data.getIsFocus()){
                                      finish();
                                  }
                              }
                            }
                        });
                    }
                }
            }

            @Override
            public void error(String message) {

            }
        });
    }


    public void init() {
        //实例化TabHost对象，得到TabHost
        tabhost.setup(activity, getSupportFragmentManager(), R.id.realtabcontent);
        tabhost.getTabWidget().setDividerDrawable(null);
        int count = fragmentArray.length;
        //得到fragment的个数
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(mTextviewArray[i]).setIndicator(getImageView(i));
            //将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
//            tabhost.getTabWidget().getChildAt(i).setBackgroundResource(android.R.color.transparent);
        }

    }


    //获取图片资源
    private View getImageView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_img);
        TextView tv_tab = (TextView) view.findViewById(R.id.textview);
        imageView.setImageResource(mImages[index]);

        tv_tab.setText(mTextviewArray[index]);
//        tv_tab.setTextColor(R.drawable.sel_tab_red_bg);
        return view;

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    private long time = 0;

    //重写onkeydown方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击的为返回键
        if (keyCode == event.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                //获得当前的时间
                time = System.currentTimeMillis();
                DialogUtil.showToastCust("再点击一次退出应用程序");

            } else {
                //点击在两秒以内
                mBaseApplication.quit(true);
            }
        }
        return true;
    }
}
