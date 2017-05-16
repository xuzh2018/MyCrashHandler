package com.test.xzh.mytest.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xzh on 2017/3/9
 */

public class GankBeautyResult {
    public boolean error;
    public @SerializedName("results") List<GankBeaty> beauties;
}
