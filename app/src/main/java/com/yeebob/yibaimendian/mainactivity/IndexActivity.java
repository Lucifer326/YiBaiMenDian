package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 首页 IndexActivity
 * Created by WGL on 2015-12-29.
 */
@ContentView(R.layout.activity_index)
public class IndexActivity extends AppCompatActivity {

    @ViewInject(R.id.index_toolbar)
    private Toolbar mToolbar;
    @ViewInject(R.id.id_shop_qrcode)
    private TextView shopQrcode;
    @ViewInject(R.id.id_product_category)
    private TextView productCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        shopQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQrcodeActivity(); //商城二维码
            }
        });

        productCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductCategory(); //打开商品分类
            }
        });

    }

    private void startProductCategory() {
        // 打开商品品牌分类页
        Intent intent = new Intent(IndexActivity.this, ProductsCategoryActivity.class);
        startActivity(intent);
    }

    private void startQrcodeActivity() {
        // 打开微信商城二维码
        Intent intent = new Intent(IndexActivity.this, ShowShopQrcode.class);
        startActivity(intent);
    }

}
