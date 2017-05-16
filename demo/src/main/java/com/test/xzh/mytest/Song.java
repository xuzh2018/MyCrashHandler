package com.test.xzh.mytest;

import android.support.annotation.NonNull;

/**
 * Created by xzh on 2017/2/14
 */

public class Song {
    @NonNull public String text;
    @NonNull public int id;
    public Song(String text, int bitmapid){
        this.text = text;
        this.id = bitmapid;
    }
}
