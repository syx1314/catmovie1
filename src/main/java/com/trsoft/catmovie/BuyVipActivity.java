package com.trsoft.catmovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.trsoft.app.lib.http.ApiResultBean;
import com.trsoft.app.lib.http.IApiReturn;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.adapter.PayTypeAdapter;
import com.trsoft.catmovie.adapter.TaoCanAdapter;
import com.trsoft.catmovie.api.OrderApi;
import com.trsoft.catmovie.base.BaseLoginActivity;
import com.trsoft.catmovie.constant.Constant;
import com.trsoft.catmovie.entity.PayType;
import com.trsoft.catmovie.entity.TaoCanBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyVipActivity extends BaseLoginActivity {

    @BindView(R.id.rv_vip_type)
    RecyclerView rvVipType;
    @BindView(R.id.rv_buy_way)
    RecyclerView rvBuyWay;
    List<PayType> payTypeList;

    private PayType selPayType;//选中的支付方式
    private TaoCanBean taoCanBean;//选中的套餐

    @Override
    protected int getLayout() {
        return R.layout.activity_buy_vip;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTaoCan();
        payTypeList = new ArrayList<>();
        payTypeList.add(new PayType("1", R.mipmap.ic_jifen, "积分支付"));
        selPayType = payTypeList.get(0);//默认的充值方式
        payTypeList.add(new PayType("alipay", R.mipmap.alipay, "支付宝"));
        payTypeList.add(new PayType("wxpay", R.mipmap.weixin, "微信支付"));
        payTypeList.add(new PayType("qqpay", R.mipmap.qq, "qq钱包"));
        final PayTypeAdapter adapter = new PayTypeAdapter(payTypeList, R.layout.item_pay_type);
        rvBuyWay.setLayoutManager(new LinearLayoutManager(this));
        rvBuyWay.setHasFixedSize(true);
        rvBuyWay.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                adapter.setSelPosition(position);
                selPayType = payTypeList.get(position);
            }
        });
    }

    private void initTaoCan() {
        Subscribe(getApiService(OrderApi.class).getTaoCanType("taocan"), new IApiReturn<List<TaoCanBean>>() {
            @Override
            public void run(ApiResultBean<List<TaoCanBean>> apiResult) {
                if (isSuccess(apiResult.getCode())) {
                    final List<TaoCanBean> data = apiResult.getData();
                    taoCanBean = data.get(0);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
                    rvVipType.setLayoutManager(gridLayoutManager);
                    final TaoCanAdapter taoCanAdapter = new TaoCanAdapter(activity,data, R.layout.item_taocan);
                    rvVipType.setAdapter(taoCanAdapter);
                    taoCanAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            taoCanAdapter.setSelPisotion(position);
                            taoCanBean = data.get(position);
                        }
                    });
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


    @OnClick(R.id.btn_recharge)
    public void onViewClicked() {
        //www.catmovie.cn/api/pay.php?token=a6ea021a984ad4b76f92d9cacd962e81&cardid=1&pay=alipay
//        if (selPayType.getId().equals("1")) {
//            startActivity(new Intent(activity, KmRechangeActivity.class));
//        } else {
            Intent intent = new Intent(activity, LoadHtmlActivity.class);
            intent.putExtra(Constant.BUNDLE_STRING, "会员充值");
            intent.putExtra(Constant.BUNDLE_URL, "http://www.catmovie.cn/api/pay.php?token="+ PreferenceUtils.getInstance().getString(Constant.TOKEN)+"&cardid="+taoCanBean.getId()+"&pay=" + selPayType.getId());
            startActivity(intent);
//        }


    }
}
