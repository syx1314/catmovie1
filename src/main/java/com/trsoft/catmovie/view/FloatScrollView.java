package com.trsoft.catmovie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class FloatScrollView extends ScrollView {


    private static final int CHECK_STATE = 0;
    private OnScrollListener onScrollListener;
    private boolean inTouch = false;
    private int lastT = 0;

    public FloatScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (null != mOnScrollListener) {
//            mOnScrollListener.onScroll(t);
//        }
//
//    }
//
//
//
//
//    /**
//     * 设置滚动接口
//     *
//     * @param listener
//     */
//    public void setOnScrollListener(OnScrollListener listener) {
//        this.mOnScrollListener = listener;
//    }
//
//    /**
//     * 滚动的回调接口
//     */
//    public interface OnScrollListener {
//        /**
//         * MyScrollView滑动的Y方向距离变化时的回调方法
//         *
//         * @param scrollY
//         */
//        void onScroll(int scrollY);
//    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        //x为当前滑动条的横坐标，y表示当前滑动条的纵坐标，oldx为前一次滑动的横坐标，oldy表示前一次滑动的纵坐标
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollListener != null) {
            //在这里将方法暴露出去
            onScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }


    //是否要其弹性滑动
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        // 弹性滑动关键则是maxOverScrollX， 以及maxOverScrollY，
        // 一般默认值都是0，需要弹性时，更改其值即可
        // 即就是，为零则不会发生弹性，不为零（>0,负数未测试）则会滑动到其值的位置
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, 0, 0, isTouchEvent);
    }

    //接口
    public interface OnScrollListener {

        void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy);

    }

    public void setScrollViewListener(OnScrollListener listener) {
        onScrollListener = listener;
    }
}

