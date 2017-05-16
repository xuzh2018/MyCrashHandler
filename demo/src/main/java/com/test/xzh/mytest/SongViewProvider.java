package com.test.xzh.mytest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by xzh on 2017/2/14
 */

public class SongViewProvider extends ItemViewProvider<Song, SongViewProvider.ViewHolder> {
    @NonNull
    @Override
    protected SongViewProvider.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.song, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Song song) {
            holder.mText.setText(song.text);
            holder.mImage.setImageResource(song.id);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mText;
        private final ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
            mImage = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
