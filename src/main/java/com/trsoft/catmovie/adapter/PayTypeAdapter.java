package com.trsoft.catmovie.adapter;

import android.widget.CheckBox;

import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.R;
import com.trsoft.catmovie.entity.PayType;

import java.util.List;

/**
 * Created by Adim on 2018/5/12.
 * 支付方式
 */

public class PayTypeAdapter extends BaseRecycleViewAdapter<PayType> {
    public PayTypeAdapter(List<PayType> mdatas, int resLayoutId) {
        super(mdatas, resLayoutId);
    }

    private int selPosition = 0;

    public void setSelPosition(int selPosition) {
        this.selPosition = selPosition;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        PayType payType = mdatas.get(position);
        holder.setImageResource(R.id.iv_pay_icon, payType.getResIcon());
        holder.setText(R.id.tv_pay_name, payType.getText());
        CheckBox cb = holder.getView(R.id.cb);

        if (selPosition == position) {
            payType.setCheck(true);
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
            payType.setCheck(false);
        }


    }
}
