package com.trsoft.catmovie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018/10/19 0019.
 */

public class MyScrollView extends ScrollView {

    private OnScrollListener listener;

    /**
     * 设置滑动距离监听器
     */
    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 滑动距离监听器
    public interface OnScrollListener {

        /**
         * 在滑动的时候调用，scrollY为已滑动的距离
         */
        void onScroll(int scrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(t);
        }

    }

}
