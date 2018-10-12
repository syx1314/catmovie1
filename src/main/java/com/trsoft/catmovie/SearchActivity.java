package com.trsoft.catmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ListUtil;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.api.SearchApi;
import com.trsoft.catmovie.base.BaseActivity;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.entity.SearchFilmTvBean;
import com.trsoft.catmovie.entity.SearchFilmTvResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_keyWord)
    EditText etKeyWord;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etKeyWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    requestSearch(etKeyWord.getText().toString());
                }
                return false;
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void back(){
        onBackPressed();
    }

    private void requestSearch(String keyWord) {
        Subscribe(getApiService(SearchApi.class).search(keyWord), new IApiReturn<SearchFilmTvResponse>() {
            @Override
            public void run(ApiResultBean<SearchFilmTvResponse> apiResult) {
                if (isSuccess(apiResult.getCode())) {
                    //请求成功
                    final List<SearchFilmTvBean> searchFilmTvList = new ArrayList<>();
                    SearchFilmTvResponse data = apiResult.getData();
                    if (data != null) {
                        //第一种类数据
                        if (ListUtil.isNotEmpty(data.getHref())) {
                            int i = 0;
                            for (String href : data.getHref()) {

                                SearchFilmTvBean searchFilmTvBean = new SearchFilmTvBean();
                                searchFilmTvBean.setHref(href);
                                if (ListUtil.isNotEmpty(data.getJishu())) {
                                    searchFilmTvBean.setJishu(data.getJishu().get(i));
                                }
                                if (ListUtil.isNotEmpty(data.getImg())) {
                                    searchFilmTvBean.setImg(data.getImg().get(i));
                                }
                                if (ListUtil.isNotEmpty(data.getType())) {
                                    searchFilmTvBean.setType(data.getType().get(i));
                                }
                                if (ListUtil.isNotEmpty(data.getTitle())) {
                                    searchFilmTvBean.setTitle(data.getTitle().get(i));
                                }
                                i++;
                                searchFilmTvList.add(searchFilmTvBean);
                            }
                        }
                        //end 第一种 数据结束
                        //第二种类数据
                        if (ListUtil.isNotEmpty(data.getMg_href())) {
                            int i = 0;
                            for (String href : data.getMg_href()) {

                                SearchFilmTvBean searchFilmTvBean = new SearchFilmTvBean();
                                searchFilmTvBean.setHref(href);
//                                if (ListUtil.isNotEmpty(data.getM())) {
//                                    searchFilmTvBean.setJishu(data.getJishu().get(i));
//                                }
                                if (ListUtil.isNotEmpty(data.getMg_img())) {
                                    searchFilmTvBean.setImg(data.getMg_img().get(i));
                                }
                                if (ListUtil.isNotEmpty(data.getMg_year())) {
                                    searchFilmTvBean.setType("年代" + data.getMg_year().get(i));
                                }
                                if (ListUtil.isNotEmpty(data.getMg_title())) {
                                    searchFilmTvBean.setTitle(data.getMg_title().get(i));
                                }
                                i++;
                                searchFilmTvList.add(searchFilmTvBean);
                            }
                        }
                        rvSearch.setLayoutManager(new LinearLayoutManager(activity));
                        BaseRecycleViewAdapter adapter = new BaseRecycleViewAdapter<SearchFilmTvBean>(searchFilmTvList, R.layout.item_search) {
                            @Override
                            public void onBindViewHolder(ViewHolder holder, int position) {
                                super.onBindViewHolder(holder, position);
                                SearchFilmTvBean searchFilmTvBean = mdatas.get(position);
                                holder.setImage(R.id.iv_film_img, searchFilmTvBean.getImg());
                                holder.setText(R.id.tv_flim_title, searchFilmTvBean.getTitle());
                                holder.setText(R.id.tv_flim_jishu, searchFilmTvBean.getJishu());
                                holder.setTextHtml(R.id.tv_flim_type, searchFilmTvBean.getType());
                            }
                        };
                        rvSearch.setAdapter(adapter);
//
                        adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(activity, LoadHtmlActivity.class);
                                intent.putExtra(Constant.BUNDLE_STRING, "播放");
                                String token= PreferenceUtils.getInstance().getString(Constant.TOKEN);
                                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL + "/play.php?play=" + searchFilmTvList.get(position).getHref().replace("http://www.360kan.com", "")+"&token="+token);
                                activity.startActivity(intent);
                            }
                        });
                    }
                } else {
                    error(apiResult.getMessage());
                }
            }

            @Override
            public void error(String message) {
                DialogUtil.showAlert(activity, message, null);
            }
        });
    }
}
