package com.trsoft.catmovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gyf.barlibrary.ImmersionBar;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.DisplayUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.ListUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.api.LoginApi;
import com.trsoft.catmovie.api.SearchApi;
import com.trsoft.catmovie.base.BaseFragment;
import com.trsoft.catmovie.common.CommonTitle;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.constant.Utils;
import com.trsoft.catmovie.entity.AllVideoBean;
import com.trsoft.catmovie.entity.BannerBean;
import com.trsoft.catmovie.view.FloatScrollView;
import com.trsoft.catmovie.view.MyScrollView;
import com.trsoft.catmovie.view.MySpecialScrollView;
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

public class HomeFragment extends BaseFragment implements FloatScrollView.OnScrollListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rv_recomend)
    RecyclerView rvRecomend;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.mySv)
    FloatScrollView mySv;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.tv_update_sub_title)
    TextView tvUpdateSubTitle;
    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;
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
        ImmersionBar.with(this).titleBar(R.id.lin_title).init();
        GridLayoutManager manager = new GridLayoutManager(activity, 4);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        rvRecomend.setLayoutManager(new GridLayoutManager(activity, 3));
        rvRecomend.setHasFixedSize(true);
        getMenu();
        getBanner();
        mySv.setScrollViewListener(this);

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
                            if (Validator.isNotEmpty(bannerBean.getImg())) {
                                holder.setImageCircle(R.id.iv_tab_img, Constant.BASEURL + bannerBean.getImg());
                            }
//                            holder.setImage(R.id.iv_tab_img, "http://img3.doubanio.com/view/photo/s_ratio_poster/public/p2506610722.jpg");
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
        Subscribe(getApiService(SearchApi.class).getAllVideo("1", null), new IApiReturn<List<AllVideoBean>>() {
            @Override
            public void run(ApiResultBean<List<AllVideoBean>> apiResult) {
                if (isSuccess(apiResult.getCode())) {
                    rvRecomend.setAdapter(new BaseRecycleViewAdapter<AllVideoBean>(apiResult.getData(), R.layout.item_video) {
                        @Override
                        public void onBindViewHolder(ViewHolder holder, int position) {
                            super.onBindViewHolder(holder, position);
                            final AllVideoBean allVideoBean = mdatas.get(position);
                            if (Validator.isNotEmpty(allVideoBean.getStatus())) {
                                if (Utils.isInteger(allVideoBean.getStatus())) {
                                    holder.setText(R.id.tv_video_status, "更新至" + allVideoBean.getStatus() + "集");
                                } else {
                                    holder.setText(R.id.tv_video_status, allVideoBean.getStatus());
                                }
                            } else {
                                holder.setText(R.id.tv_video_status, "");
                            }
                            holder.setImage(R.id.iv_video, allVideoBean.getMovieCover());
                            holder.setText(R.id.tv_video_name, allVideoBean.getMovieName());
                            holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, VideoPlayActivity.class);
                                    intent.putExtra("video",allVideoBean);
                                    startActivity(intent);
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

    @Override
    public void onScrollChanged(View scrollView, int x, int scrollY, int oldx, int oldy) {
        int height = linTitle.getHeight();
        if(scrollY>banner.getHeight()+rv.getHeight()-DisplayUtil.dipToPx(activity,20)){
            tvUpdateSubTitle.setVisibility(View.VISIBLE);
        }else {
            tvUpdateSubTitle.setVisibility(View.GONE);
        }
        if(scrollY> DisplayUtil.dipToPx(activity,20)){
            int alpha=255*scrollY/height;
            if(alpha>255){
                alpha=255;
            }
            linTitle.setBackgroundColor(Color.argb(alpha,255,0,0));
        }else {
            linTitle.setBackgroundResource(R.drawable.shape_jianbian_bg);
        }
    }


    private class MyLoader extends com.youth.banner.loader.ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//            Glide.with(context).load((String) path).into(imageView);
            ImageLoader.display((String) path,imageView);
        }
    }
}
