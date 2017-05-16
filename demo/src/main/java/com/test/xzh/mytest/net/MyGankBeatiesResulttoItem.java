package com.test.xzh.mytest.net;

import com.test.xzh.mytest.entity.GankBeaty;
import com.test.xzh.mytest.entity.GankBeautyResult;
import com.test.xzh.mytest.entity.ZhuangbiImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by xzh on 2017/3/9
 */

public class MyGankBeatiesResulttoItem implements Function<GankBeautyResult, List<ZhuangbiImage>> {
    private static MyGankBeatiesResulttoItem INSTANcE = new MyGankBeatiesResulttoItem();

    private MyGankBeatiesResulttoItem() {
    }
    public static MyGankBeatiesResulttoItem getInstance() {
        return INSTANcE;

    }

    @Override
    public List<ZhuangbiImage> apply(GankBeautyResult gankBeautyResult) throws Exception {
        List<GankBeaty> gankBeauties = gankBeautyResult.beauties;
        List<ZhuangbiImage> items = new ArrayList<>(gankBeauties.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for (GankBeaty gankBeauty : gankBeauties) {
            ZhuangbiImage item = new ZhuangbiImage();
            try {
                Date date = inputFormat.parse(gankBeauty.createdAt);
                item.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                item.description = "unknown date";
            }
            item.image_url = gankBeauty.url;
            items.add(item);
        }
        return items;

    }


}
