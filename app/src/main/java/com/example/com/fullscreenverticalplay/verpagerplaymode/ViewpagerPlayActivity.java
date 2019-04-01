package com.example.com.fullscreenverticalplay.verpagerplaymode;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import com.example.com.fullscreenverticalplay.FullScreenVideoView;
import com.example.com.fullscreenverticalplay.R;
import com.example.com.fullscreenverticalplay.VideoBean;
import java.util.ArrayList;

public class ViewpagerPlayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VerticalAdapter verticalAdapter;
    private ArrayList<VideoBean> datas = new ArrayList<>();
    private int[] imgRes = new int[]{R.mipmap.cover1, R.mipmap.cover2, R.mipmap.cover3, R.mipmap.cover4, R.mipmap.cover5};
    private int[] videoRes = new int[]{R.raw.video1, R.raw.video2, R.raw.video3, R.raw.video4, R.raw.video5};
    private FullScreenVideoView videoView;
    private PagerLayoutManager pagerLayoutManager;
    private int curPos;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAdapter();
    }

    private void setAdapter() {
        recyclerView = findViewById(R.id.recyclerview);
        pagerLayoutManager = new PagerLayoutManager(getApplicationContext(), recyclerView);
        recyclerView.setLayoutManager(pagerLayoutManager);
        for (int i = 0; i < imgRes.length; i++) {
            datas.add(new VideoBean(imgRes[i], imgRes[i]));
        }
        verticalAdapter = new VerticalAdapter(getApplicationContext(), datas);
        recyclerView.setAdapter(verticalAdapter);

        setVideoView();
        setPageChangeEvent();
    }

    private void setVideoView() {
        videoView = new FullScreenVideoView(getApplicationContext());
        videoView.setOnCompletionListener(mp -> mp.start());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ViewGroup itemView = (ViewGroup) pagerLayoutManager.findViewByPosition(curPos);
                ImageView ivCover = itemView.findViewById(R.id.iv_cover);
                ivCover.setVisibility(View.GONE);
            }
        });
    }

    private void setPageChangeEvent() {

        pagerLayoutManager.setPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onInitPage() {
                playVideo(0);
            }

            @Override
            public void onReleasePage(int position) {
                videoView.stopPlayback();
            }

            @Override
            public void onCurPage(int position) {
                playVideo(position);
            }
        });
    }

    private void playVideo(int position) {
        curPos = position;
        ViewGroup itemView = (ViewGroup) pagerLayoutManager.findViewByPosition(position);

        dettachParentView(itemView);

        String bgVideoPath = "android.resource://" + getPackageName() + "/" + videoRes[position];
        videoView.setVideoPath(bgVideoPath);
        videoView.start();
    }

    /**
     * 播放器替换到新的父容器下
     * @param attachView
     */
    private void dettachParentView(ViewGroup attachView) {
        ViewParent viewParent = videoView.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(videoView);
        }
        attachView.addView(videoView, 0);
    }

}
