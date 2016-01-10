package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.madapter.GalleryAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品详情页 ProductDetailActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_product_detail)
public class ProductDetailActivity extends AppCompatActivity {

    @ViewInject(R.id.id_product_category)
    private Toolbar mToolbar;

    private List<Integer> mDatas;
    private ImageView mImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        initToolBar();

        mImg = (ImageView) findViewById(R.id.id_content);
        mDatas = new ArrayList<>(Arrays.asList(R.drawable.a,
                R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
                R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.l));
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.detail_pic_list);
       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GalleryAdapter mAdapter = new GalleryAdapter(ProductDetailActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mImg.setImageResource(mDatas.get(0));
        // 显示大图片
        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//				Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT)
//						.show();
                mImg.setImageResource(mDatas.get(position));
            }
        });


    }

    private void initToolBar() {
        // 点击返回按钮
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // 清空标题文字
        mToolbar.setTitle("");

        this.setSupportActionBar(mToolbar);

        // Menu item click 的监听事件 setSupportActionBar 后才有作用
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.shop_qrcode:
                    startQrcodeActivity();  //打开商城二维码页
                    break;
                case R.id.action_share:
                    startProductCategory();  //打开商品品牌分类页
                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    private void startProductCategory() {
        // 打开商品品牌分类页
        Intent intent = new Intent(ProductDetailActivity.this, ProductsCategoryActivity.class);
        startActivity(intent);
    }

    private void startQrcodeActivity() {
        // 打开微信商城二维码
        Intent intent = new Intent(ProductDetailActivity.this, ShowShopQrcode.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // init toolbar menu layout
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

}
