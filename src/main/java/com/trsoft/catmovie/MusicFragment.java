package com.trsoft.catmovie;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.ListUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.api.MusicApi;
import com.trsoft.catmovie.base.BaseFragment;
import com.trsoft.catmovie.entity.MusicBean;
import com.trsoft.catmovie.entity.MusicBeanResponse;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 21点19分
 *
 * @author 呆呆
 */

public class MusicFragment extends BaseFragment {

    @BindView(R.id.rv_left_sort)
    RecyclerView rvLeftSort;
    @BindView(R.id.rv_right_sort_details)
    RecyclerView rvRightSortDetails;
    private List<MusicBean> musicBean = new ArrayList<>();
    private List<MusicBean> musicSortDetails = new ArrayList<>();
    private BaseRecycleViewAdapter<MusicBean> sortDetailsList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music;
    }

    @Override
    protected void init() {
        rvLeftSort.setLayoutManager(new LinearLayoutManager(activity));
        rvRightSortDetails.setLayoutManager(new LinearLayoutManager(activity));
        BaseRecycleViewAdapter<MusicBean> sortList = new BaseRecycleViewAdapter<MusicBean>(musicBean, R.layout.item_music_sort) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MusicBean musicBean = MusicFragment.this.musicBean.get(position);
                holder.setImage(R.id.iv_sort, musicBean.getIcon());
                holder.setText(R.id.tv_sort_name, musicBean.getTitle());
            }
        };
        rvLeftSort.setAdapter(sortList);
        sortDetailsList = new BaseRecycleViewAdapter<MusicBean>(musicSortDetails, R.layout.item_music_sort) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MusicBean musicBean = musicSortDetails.get(position);
                holder.setText(R.id.tv_num, (position + 1) + "");
                holder.setText(R.id.tv_sort_name, musicBean.getTitle());
            }
        };
        rvRightSortDetails.setAdapter(sortDetailsList);
        requestMusicSort();
        sortList.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                musicSortDetails.clear();
                sortDetailsList.refreshData();
                resquestSortDetails(musicBean.get(position).getHref());
            }
        });

    }


    @OnClick(R.id.btn)
    public void brn() {
//        startActivity(new Intent(activity, com.wm.remusic.activity.MainActivity.class));

        MyLog.e("跳转");
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
        ARouter.getInstance().build("/module/main").navigation();
    }

    private void requestMusicSort() {
        Subscribe(getApiService(MusicApi.class).getMusicSort(), new IApiReturn<MusicBeanResponse>() {
            @Override
            public void run(ApiResultBean<MusicBeanResponse> apiResult) {
                if (isSuccess(apiResult.getCode())) {
                    MusicBeanResponse data = apiResult.getData();
                    List<MusicBean> tempMusicBean = new ArrayList<>();
                    if (ListUtil.isNotEmpty(data.getTitle())) {
                        for (int i = 0; i < data.getTitle().size(); i++) {
                            String s = data.getTitle().get(i);
                            MusicBean musicBean = new MusicBean();
                            int i1 = s.indexOf("\"");
                            s= s.substring(0, (i1>0?i1:s.length()));
                            musicBean.setTitle(s);
                            if (ListUtil.isNotEmpty(data.getIcon())) {
                                if (data.getIcon().size()>i&&data.getIcon().get(i) != null) {
                                    musicBean.setIcon(data.getIcon().get(i));
                                }
                            }
                            if (ListUtil.isNotEmpty(data.getHref())) {
                                if (data.getHref().size()>i&&data.getHref().get(i) != null) {
                                    musicBean.setHref(data.getHref().get(i));
                                }
                            }
                            tempMusicBean.add(musicBean);
                        }
                    }
                    musicBean.addAll(tempMusicBean);
                    if(ListUtil.isNotEmpty(musicBean)) {
                        musicBean.remove(0);//第一个分类错的
                        resquestSortDetails(musicBean.get(0).getHref());
                    }
                    rvLeftSort.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void error(String message) {

            }
        });
    }

    private void resquestSortDetails(String path){
        Subscribe(getApiService(MusicApi.class).getSortDetails(path), new IApiReturn<MusicBeanResponse>() {
            @Override
            public void run(ApiResultBean<MusicBeanResponse> apiResult) {
                if (isSuccess(apiResult.getCode())) {
                    MusicBeanResponse data = apiResult.getData();
                    List<MusicBean> tempMusicBean = new ArrayList<>();
                    if (ListUtil.isNotEmpty(data.getTitle())) {
                        for (int i = 0; i < data.getTitle().size(); i++) {
                            String s = data.getTitle().get(i);
                            MusicBean musicBean = new MusicBean();
                            musicBean.setTitle(s);
                            if (ListUtil.isNotEmpty(data.getHref())) {
                                if (data.getHref().size()>i&&data.getHref().get(i) != null) {
                                    musicBean.setHref(data.getHref().get(i));
                                }
                            }
                            tempMusicBean.add(musicBean);
                        }
                    }
                    musicSortDetails.addAll(tempMusicBean);
                    if(ListUtil.isNotEmpty(musicBean)) {
                        musicSortDetails.remove(0);//第一个分类错的
                    }
                    rvRightSortDetails.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void error(String message) {

            }
        });
    }
}
