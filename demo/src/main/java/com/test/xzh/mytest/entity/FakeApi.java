package com.test.xzh.mytest.entity;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by xzh on 2017/3/10
 */

public class FakeApi {
    Random random = new Random();
    public Observable<FakeToken> getFakeToken(String token) {
        return Observable.just(token)
                .map(new Function<String, FakeToken>() {
                    @Override
                    public FakeToken apply(String s) throws Exception {
                        int timecost = random.nextInt(500)+500;
                        try {
                            Thread.sleep(timecost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        FakeToken fakeToken = new FakeToken();
                        fakeToken.token = creatToken();
                        return fakeToken;
                    }
                });

    }

    private String creatToken() {
        return "Mytoken"+System.currentTimeMillis()/10000;
    }

    public Observable<FakeThing> getFakeThing(FakeToken fakeToken) {
        return Observable.just(fakeToken)
                .map(new Function<FakeToken, FakeThing>() {
                    @Override
                    public FakeThing apply(FakeToken fakeToken) throws Exception {
                        int fakeNetworkTimeCost = random.nextInt(500) + 500;
                        try {
                            Thread.sleep(fakeNetworkTimeCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (fakeToken.expired) {
                            throw new IllegalArgumentException("Token expired!");
                        }

                        FakeThing fakeData = new FakeThing();
                        fakeData.id = (int) (System.currentTimeMillis() % 1000);
                        fakeData.name = "Faker" + fakeData.id;
                        return fakeData;
                    }
                });
    }
}
