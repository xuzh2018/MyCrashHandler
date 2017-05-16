package com.test.xzh.mytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Hashtable;


public class Test1Activity extends AppCompatActivity {


    private WebView webView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        setTitle("点击显示");
        webView = (WebView) this.findViewById(R.id.web);
        mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null != result){
                    int type = result.getType();
                    if(type == WebView.HitTestResult.IMAGE_TYPE ||type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE ){
                        String imageUrl = result.getExtra();
                        getbitmap(imageUrl);
                    }
                }
                return false;
            }
        });
        webView.loadUrl("http://blog.csdn.net/harvic880925/article/details/51523983");

    }

    private void getbitmap(String imageUrl) {
        RequestParams requestParams = new RequestParams(imageUrl);
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Result image = scannImage(result.getAbsolutePath());
                if(result == null) {

                    Toast.makeText(Test1Activity.this, "图片格式错误", Toast.LENGTH_SHORT).show();

                }else {

                    //result = "http://weixin.qq.com/d";
                    Toast.makeText(Test1Activity.this, result.toString() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private Result scannImage(String imagePath) {

        if(TextUtils.isEmpty(imagePath)) {

            return null;
        }

        //------------------------------------------------------------------------------------------------------------

        Hashtable<DecodeHintType, String> hintTypeStringHashtable = new Hashtable<DecodeHintType, String>();

        //解析转换类型UTF-8
        hintTypeStringHashtable.put(DecodeHintType.CHARACTER_SET, "utf-8");

        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();

        //只返回bitmap的信息，不返回bitmap本身
        options.inJustDecodeBounds = true;

        //inJustDecodeBounds = true； bitmap = null；只返回bitmap基本信息
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        //设置二维码变成为400像素，正方形
        int sampleSize = (int) (options.outHeight / (float) 400);

        if (sampleSize <= 0) {

            sampleSize = 1;
        }

        options.inSampleSize = sampleSize;

        //返回缩放后的bitmap
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(imagePath, options);

        //-------------------------------------------------------------------------------------------------------------------
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);

        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));

        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();

        try {
            //返回解析结果
            return reader.decode(binaryBitmap, hintTypeStringHashtable);

        } catch (Exception e) {

            // TODO: handle exception
        }

        return null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
