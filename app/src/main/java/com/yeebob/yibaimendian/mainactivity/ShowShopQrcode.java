package com.yeebob.yibaimendian.mainactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * 微信商城二维码页 ShowShopQrcode
 * Created by WGL on 2016-1-6.
 */
@ContentView(R.layout.show_shop_qrcode)
public class ShowShopQrcode extends AppCompatActivity {

    @ViewInject(R.id.id_shop_qrcode_img)
    private ImageView mQrcodeImg;
    private MyHandler myHandler = new MyHandler(this);
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE); //隐藏底部虚拟按键
        x.view().inject(this);


        // getJsonData();
        final String qrcodeUrl = (String) SharedPreferencesUtil.getData(this, "href", "");
        ImageLoader.getInstance().displayImage(qrcodeUrl, mQrcodeImg, options);
    }

    /*private void getJsonData() {
        //内容
        final String qrcodeUrl = (String) SharedPreferencesUtil.getData(this, "href", "");
        Log.v("aaaa", qrcodeUrl);
        final int dimension = 500;
        final String filePath = getFileRoot(ShowShopQrcode.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QrCodeUtil.createQRImage(qrcodeUrl, dimension, dimension, null, filePath);
                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            Message msg = Message.obtain();
                            msg.obj = bitmap;
                            myHandler.sendMessage(msg);
                        }
                    });
                }
            }
        }).start();

    }*/

    // 点击任意区域 关闭页面
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.finish();
        return true;
    }

    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }

    private static class MyHandler extends Handler {
        private final WeakReference<ShowShopQrcode> mActivity;

        public MyHandler(ShowShopQrcode activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ShowShopQrcode activity = mActivity.get();
            if (activity != null) {
                //activity.mQrcodeImg.setImageBitmap((Bitmap) msg.obj);
            }

        }
    }
}

