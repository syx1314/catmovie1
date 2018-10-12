package com.trsoft.catmovie.base;


import android.app.Activity;


import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mob.MobSDK;
import com.tencent.smtt.sdk.QbSdk;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.entity.ILoginConfig;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.catmovie.constant.Constant;


/**
 * Created by 小呆呆 on 2017/12/20 0020.
 */

public class MyApplication extends BaseApplication {
    @Override
    public String getApiUrl() {
        return Constant.BASEURL;
    }

    @Override
    public void quit(boolean isClearData) {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.e("BuildConfig.DEBUG" + BuildConfig.DEBUG);
//        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
//        new MainApplication(this);
//        搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                MyLog.e("app  内核 is " + (arg0 ? "x5内核加载成功" : "系统内核"));
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(this, cb);
        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(this, "2586cdc972e60", "fc29f13bdf62b2841899f8cb865b8b12");
    }

    @Override
    public ILoginConfig getLoginConfig() {
        return new ILoginConfig() {
            @Override
            public String getToken() {
                return PreferenceUtils.getInstance().getString(Constant.TOKEN);
            }
        };
    }

    @Override
    public Class getLoginActivityClass() {
        return null;
    }

    @Override
    public void saveLoginConfig(ILoginConfig mLoginConfig) {

    }


}
