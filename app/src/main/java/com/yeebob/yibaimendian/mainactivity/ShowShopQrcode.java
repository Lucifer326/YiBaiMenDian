package com.yeebob.yibaimendian.mainactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 微信商城二维码页 ShowShopQrcode
 * Created by WGL on 2016-1-6.
 */
@ContentView(R.layout.show_shop_qrcode)
public class ShowShopQrcode extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
    }

    // 点击任意区域 关闭页面
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.finish();
        return  true;
    }
}

