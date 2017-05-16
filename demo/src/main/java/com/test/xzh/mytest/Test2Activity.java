package com.test.xzh.mytest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class Test2Activity extends AppCompatActivity {


    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        setTitle("点击显示");
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
        Items items = new Items();

        adapter = new MultiTypeAdapter(items);
        adapter.register(Category.class,new CategoryViewProvider());
        adapter.register(Song.class,new SongViewProvider());
        for (int i = 0; i < 20; i++) {
            items.add(new Category("Songs"));
            items.add(new Song("小艾大人", R.drawable.djjt_jt));
            items.add(new Song("许岑", R.drawable.djjt_ybjt));
        }


        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


}


