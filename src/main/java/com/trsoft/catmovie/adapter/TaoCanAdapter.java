package com.trsoft.catmovie.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.trsoft.app.lib.view.recycleview.ViewHolder;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;
import com.trsoft.catmovie.R;
import com.trsoft.catmovie.entity.TaoCanBean;

import java.util.List;

/**
 * Created by Adim on 2018/5/12.
 */

public class TaoCanAdapter extends BaseRecycleViewAdapter<TaoCanBean> {

    private  Context context;
    private int selPisotion=0;

    public void setSelPisotion(int selPisotion) {
        this.selPisotion = selPisotion;
        notifyDataSetChanged();
    }

    public TaoCanAdapter(Context context,List<TaoCanBean> mdatas, int resLayoutId) {
        super(mdatas, resLayoutId);
        this.context=context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TaoCanBean taoCanBean = mdatas.get(position);
        LinearLayout linearLayout = holder.getView(R.id.lin_item_taocan);
        if(selPisotion==position){
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_circle_red_stroke_bg);
            linearLayout.setBackground(drawable);
        }else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_circle_gray_stroke_bg);
            linearLayout.setBackground(drawable);
        }
        holder.setText(R.id.tv_name, taoCanBean.getName() + "(" + taoCanBean.getDay() + "天)");
        holder.setText(R.id.tv_price, "¥" + taoCanBean.getMoney());
    }
}
