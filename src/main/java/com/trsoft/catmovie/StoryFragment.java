package com.trsoft.catmovie;

import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.catmovie.base.BaseFragment;
import com.trsoft.catmovie.common.X5WebView;
import com.trsoft.catmovie.constant.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Adim on 2018/3/11.
 */

public class StoryFragment extends BaseFragment {

    @BindView(R.id.wv_story)
    X5WebView wvStory;


    @Override
    protected int getLayout() {
        return R.layout.fragment_story;
    }

    @Override
    protected void init() {
        String token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        wvStory.loadUrl("http://story.catmovie.cn/");
    }


    @OnClick(R.id.iv_back)
    public void back() {
        MyLog.e("是否能返回"+wvStory.canGoBack());
        if (wvStory.canGoBack()) {
            wvStory.goBack();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(wvStory!=null)
        wvStory.destroy();
    }


}
