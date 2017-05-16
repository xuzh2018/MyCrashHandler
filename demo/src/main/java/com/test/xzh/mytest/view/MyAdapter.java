package com.test.xzh.mytest.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.xzh.mytest.R;
import com.test.xzh.mytest.entity.ZhuangbiImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzh on 2017/3/8
 */

public class MyAdapter extends RecyclerView.Adapter {
    List<ZhuangbiImage> images;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ZhuangbiImage zhuangbiImage = images.get(position);
        Glide.with(myViewHolder.imageIv.getContext()).load(zhuangbiImage.image_url).into(myViewHolder.imageIv);
        myViewHolder.descriptionTv.setText(zhuangbiImage.description);
    }

    @Override
    public int getItemCount() {
        return images == null ? 0:images.size();
    }

    public void setImages(List<ZhuangbiImage> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageIv)
        ImageView imageIv;
        @BindView(R.id.descriptionTv)
        TextView descriptionTv;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
