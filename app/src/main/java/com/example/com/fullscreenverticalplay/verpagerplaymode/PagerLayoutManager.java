package com.example.com.fullscreenverticalplay.verpagerplaymode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * create by libo
 * description
 */
public class PagerLayoutManager extends LinearLayoutManager {
    private RecyclerView recyclerView;
    private PagerSnapHelper pagerSnapHelper;
    private OnPagerChangeListener pagerChangeListener;

    public PagerLayoutManager(Context context, RecyclerView recyclerView) {
        super(context);
        this.recyclerView = recyclerView;
        init();
    }

    private void init() {
        pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {  //滑动停止时判断当前滑动到的位置
                    View view = pagerSnapHelper.findSnapView(PagerLayoutManager.this);  //获取当前滑动位置的itemview
                    int position = getPosition(view);  //根据当前itemview获取当前item的position，方法来自于Recyclerview

                    if (pagerChangeListener != null) {
                        pagerChangeListener.onCurPage(position);
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void setPagerChangeListener(OnPagerChangeListener pagerChangeListener) {
        this.pagerChangeListener = pagerChangeListener;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                //item进入可见区域
                if (pagerChangeListener != null) {
                    pagerChangeListener.onReleasePage(getPosition(view));
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                //item滑出可见区域
                if (pagerChangeListener != null && getChildCount() == 1) {
                    pagerChangeListener.onInitPage();
                }
            }
        });
    }
}
