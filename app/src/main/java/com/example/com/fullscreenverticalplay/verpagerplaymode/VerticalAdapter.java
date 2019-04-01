package com.example.com.fullscreenverticalplay.verpagerplaymode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.com.fullscreenverticalplay.BaseRvAdapter;
import com.example.com.fullscreenverticalplay.BaseRvViewHolder;
import com.example.com.fullscreenverticalplay.R;
import com.example.com.fullscreenverticalplay.VideoBean;

import java.util.List;

/**
 * create by libo
 * description
 */
public class VerticalAdapter extends BaseRvAdapter<VideoBean, VerticalAdapter.VerticalViewHolder> {

    public VerticalAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(VerticalViewHolder holder, VideoBean data, int position) {
        holder.ivCover.setBackgroundResource(data.getPicRes());
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_verticalplay, viewGroup, false);
        return new VerticalViewHolder(view);
    }

    class VerticalViewHolder extends BaseRvViewHolder {
        ImageView ivCover;

        public VerticalViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
        }
    }
}
