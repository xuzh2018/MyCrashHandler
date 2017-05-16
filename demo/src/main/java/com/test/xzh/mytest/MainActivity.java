package com.test.xzh.mytest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.test1)
    Button test1;
    @BindView(R.id.test2)
    Button test2;
    @BindView(R.id.test3)
    Button test3;
    @BindView(R.id.test4)
    Button test4;
    @BindView(R.id.test5)
    Button test5;
    @BindView(R.id.test6)
    Button test6;
    @BindView(R.id.test7)
    Button test7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("点击显示");
        ButterKnife.bind(this);

    }


    @OnClick({R.id.test1, R.id.test2,R.id.test3,R.id.test4,R.id.test5,R.id.test6,R.id.test7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test1:
                Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, Test1Activity.class);
                startActivity(intent1);
                break;
            case R.id.test2:
                Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, Test2Activity.class);
                startActivity(intent2);
                break;
            case R.id.test3:
                Intent intent3 = new Intent(this, Test3Activity.class);
                startActivity(intent3);
                break;
            case R.id.test4:
                Intent intent4 = new Intent(this, Test4Activity.class);
                startActivity(intent4);
                break;
            case R.id.test5:
                Intent intent5 = new Intent(this, Test5Activity.class);
                startActivity(intent5);
                break;
            case R.id.test6:
                Intent intent6 = new Intent(this, Test6Activity.class);
                startActivity(intent6);
                break;
            case R.id.test7:
                Toast.makeText(this,"dianji",Toast.LENGTH_LONG).show();
                Intent intent7 = new Intent(this, Test7Activity.class);
                startActivity(intent7);
                break;
        }
    }


}















































       /* RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/

