package com.test.xzh.mytest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by xzh on 2017/2/14
 */

public class CategoryViewProvider extends ItemViewProvider<Category, CategoryViewProvider.ViewHolder> {
    @NonNull
    @Override
    protected CategoryViewProvider.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.category, parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category category) {
            holder.mText.setText(category.text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mText;

        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
