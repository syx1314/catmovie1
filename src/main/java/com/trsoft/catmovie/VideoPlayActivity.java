package com.trsoft.catmovie;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ListUtil;
import com.trsoft.catmovie.base.BaseLoginActivity;
import com.trsoft.catmovie.constant.Utils;
import com.trsoft.catmovie.entity.AllVideoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoPlayActivity extends GSYBaseActivityDetail<ListGSYVideoPlayer> {


    ListGSYVideoPlayer detailPlayer;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        //增加title
        detailPlayer = (ListGSYVideoPlayer) findViewById(R.id.detail_player);
        next = (Button) findViewById(R.id.next);
//        detailPlayer.getTitleTextView().setVisibility(View.GONE);
//        detailPlayer.getBackButton().setVisibility(View.GONE);

//        initVideoBuilderMode();
        //普通模式
        initVideo();
        initPlayer();
    }

    @Override
    public ListGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    private void initPlayer() {
        AllVideoBean allVideoBean = getIntent().getParcelableExtra("video");
        //String url = "http://baobab.wd jcdn.com/14564977406580.mp4";
        List<GSYVideoModel> urls = new ArrayList<>();
        if(allVideoBean.getJishu()!=null&&ListUtil.isNotEmpty(allVideoBean.getJishu().getM3u8())){
            for(String s:allVideoBean.getJishu().getM3u8()) {
                String[] split = s.replace("$","#").split("#");
                if (Utils.isInteger(split[0])) {
                    urls.add(new GSYVideoModel(split[1], allVideoBean.getMovieName() + "第" + split[0] + "集"));
                }else {
                    urls.add(new GSYVideoModel(split[1], allVideoBean.getMovieName() +split[0] ));
                }
            }

        }
        if(ListUtil.isNotEmpty(urls)) {
            detailPlayer.setUp(urls, true, 0);
            //增加封面
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
//        detailPlayer.setThumbImageView(imageView);

            resolveNormalVideoUI();

            detailPlayer.setIsTouchWiget(true);
            //关闭自动旋转
            detailPlayer.setRotateViewAuto(false);
            detailPlayer.setLockLand(false);
            detailPlayer.setShowFullAnimation(false);
            //detailPlayer.setNeedLockFull(true);
            detailPlayer.setAutoFullWithSize(true);

            detailPlayer.setVideoAllCallBack(this);

            detailPlayer.setLockClickListener(new LockClickListener() {
                @Override
                public void onClick(View view, boolean lock) {
                    if (orientationUtils != null) {
                        //配合下方的onConfigurationChanged
                        orientationUtils.setEnable(!lock);
                    }
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListGSYVideoPlayer) detailPlayer.getCurrentPlayer()).playNext();
                }
            });
        }else {
            DialogUtil.showAlert(this, "资源缺失", new CommonCallback<Boolean>() {
                @Override
                public void onCallBack(Boolean data) {
                    finish();
                }
            });
        }

    }



    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
//        loadCover(imageView, url);
//        return new GSYVideoOptionBuilder()
//                .setThumbImageView(imageView)
////                .setUrl("https://doubanzyv1.tyswmp.com/2018/10/17/owWcTjjXHfN1jZTw/playlist.m3u8")
//                .setUrl("https://jx.itaoju.top/?url=https://www.ximalaya.com/youshengshu/3768275/12971133")
//                .setCacheWithPlay(true)
//                .setVideoTitle(" ")
//                .setIsTouchWiget(true)
//                .setRotateViewAuto(false)
//                .setLockLand(false)
//                .setShowFullAnimation(false)
//                .setNeedLockFull(true)
//                .setSeekRatio(1);
        return null;
    }

    @Override
    public void clickForFullScreen() {
    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }
    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }
}
