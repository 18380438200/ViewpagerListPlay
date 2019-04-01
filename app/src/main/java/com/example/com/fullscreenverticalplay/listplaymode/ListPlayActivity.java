package com.example.com.fullscreenverticalplay.listplaymode;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.VideoView;
import com.example.com.fullscreenverticalplay.R;
import com.example.com.fullscreenverticalplay.VideoBean;
import java.util.ArrayList;

public class ListPlayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<VideoBean> datas = new ArrayList<>();
    private ListPlayAdapter listPlayAdapter;
    private int[] imgRes = new int[]{R.mipmap.cover1, R.mipmap.cover2, R.mipmap.cover3, R.mipmap.cover4, R.mipmap.cover5};
    private int[] videoRes = new int[]{R.raw.video1, R.raw.video2, R.raw.video3, R.raw.video4, R.raw.video5};
    private LinearLayoutManager layoutManager;
    private int firstVisibleItem;
    private int lastVisibleItem;
    private VideoView videoView;
    /**
     * 当前在屏幕中可见的item数
     */
    private int visibleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_play);

        init();
        setScrollEvent();
    }

    private void init() {
        videoView = new VideoView(this);

        for (int i = 0; i < imgRes.length; i++) {
            datas.add(new VideoBean(imgRes[i], videoRes[i]));
        }
        recyclerView = findViewById(R.id.recyclerview);
        listPlayAdapter = new ListPlayAdapter(this, datas);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listPlayAdapter);
    }

    private void setScrollEvent() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {  //当滑动停止时判断
                    checkAutoPlayPos();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;
            }
        });
    }

    /**
     * 检查当前应该自动播放位置
     */
    private void checkAutoPlayPos() {
        for (int i = 0; i < visibleCount; i++) {
            //获取该view在屏幕中的位置，第一个全部显示
            ViewGroup itemView = (ViewGroup) recyclerView.getChildAt(i);
            ImageView imageView = itemView.findViewById(R.id.iv_cover);
            Rect rect = new Rect();
            imageView.getGlobalVisibleRect(rect); //将itemview矩形框映射到屏幕上，将该坐标赋予rect对象，从而获取该view的顶、底坐标
            if (rect.top == 0 && rect.bottom == imageView.getHeight()) {

                dettachParentView(itemView);
                String bgVideoPath = "android.resource://" + getPackageName() + "/" + videoRes[firstVisibleItem + 1];
                videoView.setVideoPath(bgVideoPath);
                videoView.start();
            }
        }
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
        attachView.addView(videoView);
    }


}
