package com.test.xzh.mytest.entity;

/**
 * Created by xzh on 2017/3/10
 */

public class FakeToken {
    public String token;
    public boolean expired;
    public FakeToken() {
    }
    public FakeToken(boolean expired){
        this.expired = expired;
    }
}
