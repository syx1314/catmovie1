package com.trsoft.catmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.ListUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.base.BaseFragment;
import com.trsoft.catmovie.common.CommonTitle;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.entity.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Adim on 2018/3/11.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.title)
    CommonTitle title;
    @BindView(R.id.banner)
    Banner banner;
    private List<String> list = new ArrayList<>();
    private int[] resId = {R.mipmap.aqy, R.mipmap.tx, R.mipmap.yk, R.mipmap.mgtv, R.mipmap.souhu, R.mipmap.ls, R.mipmap.bili, R.mipmap.yyt, R.mipmap.acfun, R.mipmap.qb};
    private String[] typeUrl = {"http://m.iqiyi.com/",
            "http://m.v.qq.com/index.html", "http://www.youku.com/", "https://m.mgtv.com/channel/home/", "https://m.tv.sohu.com/", "http://m.le.com/", "https://m.bilibili.com/", "http://m.yinyuetai.com/", "http://m.acfun.cn/", "http://m.fun.tv/", "http://vip.1905.com/?fr=homepc_menu_vip", "www.baidu.com"};
    private ArrayList<String> img;
    private ArrayList<String> bannertitle;
    private List<BannerBean> data;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {

        GridLayoutManager manager = new GridLayoutManager(activity, 4);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        getMenu();
        title.setClickFun(new CommonTitle.IClickListener() {
            @Override
            public void leftOclick() {

            }

            @Override
            public void rightOclick() {
                startActivity(new Intent(activity, SearchActivity.class));
            }
        });
        getBanner();
    }

    private void getMenu() {
        Subscribe(getApiService(LoginApi.class).getMenu(), new IApiReturn<List<BannerBean>>() {
            @Override
            public void run(ApiResultBean<List<BannerBean>> apiResult) {

                if (ListUtil.isNotEmpty(apiResult.getData())) {
                    final List<BannerBean> data = apiResult.getData();
                    BaseRecycleViewAdapter adapter = new BaseRecycleViewAdapter<BannerBean>(data, R.layout.item_home_type) {
                        @Override
                        public void onBindViewHolder(ViewHolder holder, int position) {
                            super.onBindViewHolder(holder, position);
                            BannerBean bannerBean = data.get(position);
                            holder.setImage(R.id.iv_tab_img, Constant.BASEURL + bannerBean.getImg());
                            holder.setText(R.id.textview, bannerBean.getTitle());
                        }
                    };
                    rv.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(final int position) {
                            DialogUtil.showAlertVip(activity, new CommonCallback<Boolean>() {
                                @Override
                                public void onCallBack(Boolean b) {
                                    if (data.get(position).getUrl().contains("search")) {
                                        startActivity(new Intent(activity, TcMovieActivity.class));
                                    } else {
                                        Intent intent = new Intent(activity, LoadHtmlActivity.class);
                                        intent.putExtra(Constant.BUNDLE_STRING, data.get(position).getTitle());
                                        intent.putExtra(Constant.BUNDLE_URL, data.get(position).getUrl());
                                        intent.putExtra("isjx", data.get(position).isJx());
                                        activity.startActivity(intent);
                                    }
                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void error(String message) {

            }
        });
    }

    //获取banner
    private void getBanner() {
        banner.setImageLoader(new MyLoader());
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        Subscribe(getApiService(LoginApi.class).getBanner(), new IApiReturn<List<BannerBean>>() {
            @Override
            public void run(ApiResultBean<List<BannerBean>> apiResult) {
                if (ListUtil.isNotEmpty(apiResult.getData())) {

                    data = apiResult.getData();
                    img = new ArrayList<>();
                    bannertitle = new ArrayList<>();

                    for (BannerBean bannerBean : apiResult.getData()) {
                        img.add(bannerBean.getImg());
                        bannertitle.add(bannerBean.getTitle());
                    }
                    banner.setImages(img);
                    banner.setBannerTitles(bannertitle);
                    banner.start();
                }
            }

            @Override
            public void error(String message) {

            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(activity, LoadHtmlActivity.class);
                intent.putExtra(Constant.BUNDLE_STRING, data.get(position).getTitle());
                intent.putExtra(Constant.BUNDLE_URL, data.get(position).getUrl());
                intent.putExtra("isjx", data.get(position).isJx());
                activity.startActivity(intent);
            }
        });
    }


    private class MyLoader extends com.youth.banner.loader.ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
