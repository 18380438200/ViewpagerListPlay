package com.example.com.fullscreenverticalplay.listplaymode;

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
public class ListPlayAdapter extends BaseRvAdapter<VideoBean, ListPlayAdapter.ListPlayViewHolder> {

    public ListPlayAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(ListPlayViewHolder holder, VideoBean data, int position) {
        holder.ivCover.setBackgroundResource(data.getPicRes());
    }

    @NonNull
    @Override
    public ListPlayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listplay, viewGroup, false);
        return new ListPlayViewHolder(view);
    }

    class ListPlayViewHolder extends BaseRvViewHolder {
        ImageView ivCover;

        public ListPlayViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
        }
    }
}
