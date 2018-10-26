package com.trsoft.catmovie;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerUrl;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.trsoft.app.lib.utils.ListUtil;
import com.trsoft.catmovie.base.BaseLoginActivity;
import com.trsoft.catmovie.constant.Utils;
import com.trsoft.catmovie.entity.AllVideoBean;
import com.trsoft.catmovie.view.CusTxPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


//public class VideoPlayActivity extends GSYBaseActivityDetail<CustomGSYVideoPlayer> {
public class VideoPlayActivity extends BaseLoginActivity {
    @BindView(R.id.player)
    CusTxPlayer playerView;
    private TXVodPlayer mVodPlayer;
    SuperPlayerView AA;

    @Override
    protected int getLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllVideoBean allVideoBean = getIntent().getParcelableExtra("video");
        //String url = "http://baobab.wd jcdn.com/14564977406580.mp4";
        List<SuperPlayerUrl> urls = new ArrayList<>();
        if (allVideoBean.getJishu() != null && ListUtil.isNotEmpty(allVideoBean.getJishu().getM3u8())) {
            for (String s : allVideoBean.getJishu().getM3u8()) {
                String[] split = s.replace("$", "#").split("#");
                if (Utils.isInteger(split[0])) {
                    urls.add(new SuperPlayerUrl(allVideoBean.getMovieName() + "第" + split[0] + "集", split[1]));
                } else {
                    urls.add(new SuperPlayerUrl(allVideoBean.getMovieName() + split[0], split[1]));
                }
            }

        }
        SuperPlayerModel model = new SuperPlayerModel();

        model.videoURL = "https://doubanzyv1.tyswmp.com/2018/10/11/9AVGZFT2XQcePJHv/playlist.m3u8";
        model.multiVideoURLs = urls;
        playerView.playWithMode(model);
        //创建player对象
//        mVodPlayer = new TXVodPlayer(activity);
//
////关键player对象与界面view
//        mVodPlayer.setPlayerView(playerView);
//        mVodPlayer.startPlay("https://doubanzyv1.tyswmp.com/2018/10/11/9AVGZFT2XQcePJHv/playlist.m3u8");

    }
    //    CustomGSYVideoPlayer detailPlayer;
//    private Button next;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_play);
//
//        //增加title
//        detailPlayer = (CustomGSYVideoPlayer) findViewById(R.id.detail_player);
//        next = (Button) findViewById(R.id.next);
////        detailPlayer.getTitleTextView().setVisibility(View.GONE);
////        detailPlayer.getBackButton().setVisibility(View.GONE);
//
////        initVideoBuilderMode();
//        //普通模式
//        initVideo();
//        initPlayer();
//
//
//    }
//
//    @Override
//    public CustomGSYVideoPlayer getGSYVideoPlayer() {
//        return detailPlayer;
//    }
//
//    private void initPlayer() {
//        AllVideoBean allVideoBean = getIntent().getParcelableExtra("video");
//        //String url = "http://baobab.wd jcdn.com/14564977406580.mp4";
//        List<GSYVideoModel> urls = new ArrayList<>();
//        if(allVideoBean.getJishu()!=null&&ListUtil.isNotEmpty(allVideoBean.getJishu().getM3u8())){
//            for(String s:allVideoBean.getJishu().getM3u8()) {
//                String[] split = s.replace("$","#").split("#");
//                if (Utils.isInteger(split[0])) {
//                    urls.add(new GSYVideoModel(split[1], allVideoBean.getMovieName() + "第" + split[0] + "集"));
//                }else {
//                    urls.add(new GSYVideoModel(split[1], allVideoBean.getMovieName() +split[0] ));
//                }
//            }
//
//        }
//        if(ListUtil.isNotEmpty(urls)) {
////            detailPlayer.setUp(urls, true, 0);
//            detailPlayer.setUp(urls.get(0).getUrl(),false,urls.get(0).getTitle());
//            //增加封面
////        ImageView imageView = new ImageView(this);
////        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////        imageView.setImageResource(R.mipmap.xxx1);
////        detailPlayer.setThumbImageView(imageView);
//
//            resolveNormalVideoUI();
//
//            detailPlayer.setIsTouchWiget(true);
//            //关闭自动旋转
//            detailPlayer.setRotateViewAuto(false);
//            detailPlayer.setLockLand(false);
//            detailPlayer.setShowFullAnimation(false);
//            //detailPlayer.setNeedLockFull(true);
//            detailPlayer.setAutoFullWithSize(true);
//
//            detailPlayer.setVideoAllCallBack(this);
//
//            detailPlayer.setLockClickListener(new LockClickListener() {
//                @Override
//                public void onClick(View view, boolean lock) {
//                    if (orientationUtils != null) {
//                        //配合下方的onConfigurationChanged
//                        orientationUtils.setEnable(!lock);
//                    }
//                }
//            });
//
//            next.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((ListGSYVideoPlayer) detailPlayer.getCurrentPlayer()).playNext();
//                }
//            });
//        }else {
//            DialogUtil.showAlert(this, "资源缺失", new CommonCallback<Boolean>() {
//                @Override
//                public void onCallBack(Boolean data) {
//                    finish();
//                }
//            });
//        }
//
//    }
//
//
//
//    @Override
//    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
//        //内置封面可参考SampleCoverVideo
//        ImageView imageView = new ImageView(this);
////        loadCover(imageView, url);
////        return new GSYVideoOptionBuilder()
////                .setThumbImageView(imageView)
//////                .setUrl("https://doubanzyv1.tyswmp.com/2018/10/17/owWcTjjXHfN1jZTw/playlist.m3u8")
////                .setUrl("https://jx.itaoju.top/?url=https://www.ximalaya.com/youshengshu/3768275/12971133")
////                .setCacheWithPlay(true)
////                .setVideoTitle(" ")
////                .setIsTouchWiget(true)
////                .setRotateViewAuto(false)
////                .setLockLand(false)
////                .setShowFullAnimation(false)
////                .setNeedLockFull(true)
////                .setSeekRatio(1);
//        return new GSYVideoOptionBuilder().setNeedShowWifiTip(true);
//    }
//
//    @Override
//    public void clickForFullScreen() {
//    }
//
//    @Override
//    public boolean getDetailOrientationRotateAuto() {
//        return true;
//    }
//    private void resolveNormalVideoUI() {
//        //增加title
//        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
//        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
//    }
//
//    private GSYVideoPlayer getCurPlay() {
//        if (detailPlayer.getFullWindowPlayer() != null) {
//            return detailPlayer.getFullWindowPlayer();
//        }
//        return detailPlayer;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerView != null) {
//            mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
//            playerView.onDestroy();
            playerView.resetPlayer();
        }

    }
}
