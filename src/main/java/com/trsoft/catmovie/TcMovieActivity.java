package com.trsoft.catmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.api.SearchApi;
import com.trsoft.catmovie.base.BaseLoginActivity;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.entity.TCMovieBean;

import java.util.List;

import butterknife.BindView;

public class TcMovieActivity extends BaseLoginActivity {

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @Override
    protected int getLayout() {
        return R.layout.activity_tc_movie;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTcMovie();
    }

    private void getTcMovie(){
        Subscribe(getApiService(SearchApi.class).getTCMovie(), new IApiReturn<List<TCMovieBean>>() {
            @Override
            public void run(ApiResultBean<List<TCMovieBean>> apiResult) {
                if (isSuccess(apiResult.getCode())){
                    rvMovie.setLayoutManager(new LinearLayoutManager(activity));
                    final BaseRecycleViewAdapter<TCMovieBean> adapter = new BaseRecycleViewAdapter<TCMovieBean>(apiResult.getData(), R.layout.item_search) {
                        @Override
                        public void onBindViewHolder(ViewHolder holder, int position) {
                            super.onBindViewHolder(holder, position);
                            TCMovieBean tcMovieBean = mdatas.get(position);
                            holder.setImage(R.id.iv_film_img, tcMovieBean.getD_picture());
                            holder.setText(R.id.tv_flim_title, tcMovieBean.getD_name());
                            if (Validator.isNotEmpty(tcMovieBean.getPlayList())) {
                                String[] split = tcMovieBean.getPlayList().split("\n");
                                holder.setText(R.id.tv_flim_jishu, "更新至：" + split.length + "集");
                            }
                            holder.setTextHtml(R.id.tv_flim_type, "主演：" + tcMovieBean.getD_zhuyan());
                        }
                    };
                    rvMovie.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(activity, LoadHtmlActivity.class);
                            intent.putExtra(Constant.BUNDLE_STRING, "播放");
                            String token= PreferenceUtils.getInstance().getString(Constant.TOKEN);
                            intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL + "/bplay.php?play=" + adapter.mdatas.get(position).getD_id()+"&token="+token);
                            activity.startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void error(String message) {
                DialogUtil.showAlert(activity,message,null);
            }
        });
    }

}
