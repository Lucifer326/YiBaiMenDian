package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.madapter.GalleryAdapter;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
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

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    @ViewInject(R.id.product_category)
    private TextView productCate;

    @ViewInject(R.id.detail_pic_recyclerView)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.id_image_content)
    private ImageView mImg;

    private List<Integer> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        getJsonData();
        mDatas = new ArrayList<>(Arrays.asList(R.drawable.xq1,
                R.drawable.xq2, R.drawable.xq3, R.drawable.xq4, R.drawable.xq5));
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

        // 返回上一页面
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 商品二维码
        shopQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startQrcodeActivity();
            }
        });

        // 商品分类
        productCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductCategory();
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

    public void getJsonData() {
        Integer shopId = (Integer) SharedPreferencesUtil.getData(ProductDetailActivity.this, "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(ProductDetailActivity.this,"token","");

        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/vProduct/product_details");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);
        params.addBodyParameter("product_id","8172"); //商品ID

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:detail", result);
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
}
