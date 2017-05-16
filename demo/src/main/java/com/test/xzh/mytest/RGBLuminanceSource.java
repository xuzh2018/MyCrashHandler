package com.test.xzh.mytest;


import android.graphics.Bitmap;

import com.google.zxing.LuminanceSource;

/**
 * Created by jiemiao.zhang on 2017-2-15.
 */
public class RGBLuminanceSource extends LuminanceSource {

    private byte bitmapPixels[];

    protected RGBLuminanceSource(Bitmap bitmap) {

        super(bitmap.getWidth(), bitmap.getHeight());

        //获取图片像素组内容
        int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];

        bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];

        bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

        //讲int数组转换为byte数组
        for (int i=0; i < data.length; i++) {

            bitmapPixels[i] = (byte)data[i];
        }
    }

    @Override
    public byte[] getRow(int y, byte[] row) {

        // 这里要得到指定行的像素数据
        System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
        return row;
    }

    @Override
    public byte[] getMatrix() {

        //返回生成好的像素数据
        return bitmapPixels;
    }
}
