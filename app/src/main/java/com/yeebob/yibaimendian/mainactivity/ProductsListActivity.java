package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.ProductList;
import com.yeebob.yibaimendian.madapter.ProductListAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品自定义推广分类页 ProductsListActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_category_tag)
public class ProductsListActivity extends AppCompatActivity {

    @ViewInject(R.id.tag_category_recyclerview)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    @ViewInject(R.id.product_category)
    private TextView productCate;

    private List<ProductList> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer integer = bundle.getInt("beanid");
        Log.d("xxxxxxxxxxxxxxxxx", String.valueOf(integer));
        // 初始化商品分类数据
        getDates();
        ProductListAdapter mCategoryAdapter = new ProductListAdapter(this, mDatas);
        mRecyclerView.setAdapter(mCategoryAdapter);
        // 垂直gridview
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        // 水平滚动gridview;
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        //item事件点击
        mCategoryAdapter.setOnItemClickLitener(new ProductListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //打开商品详情 临时
                Intent intent = new Intent(ProductsListActivity.this, ProductDetailActivity.class );
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ProductsListActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 返回事件
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 商城二维码展示
        shopQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsListActivity.this, ShowShopQrcode.class);
                startActivity(intent);
            }
        });
        // 商品分类
        productCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsListActivity.this, ProductsCategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getDates() {

       /* mDatas = new ArrayList<>();

        for (int i = 0; i < 21; i++) {
            ProductList productList = new ProductList();
            productList.setProductId(i);
            productList.setProductName("小米 红色NOTE3 双网通版");
            productList.setProductPrice("¥ 1099.00");
            productList.setCatImg(R.drawable.l);

            mDatas.add(productList);
        }*/

        mDatas = new ArrayList<>();
        List<Integer> mImg = new ArrayList<>();
        mImg.add(R.drawable.brand_1);
        mImg.add(R.drawable.brand_2);
        mImg.add(R.drawable.brand_3);
        mImg.add(R.drawable.brand_4);
        mImg.add(R.drawable.brand_5);
        for (int i = 1; i < 22; i++) {
            ProductList productList = new ProductList();
            productList.setProductId(i);
            productList.setProductName("小米 红色NOTE3 双网通版");
            productList.setProductPrice("¥ 1099.00");
            productList.setCatImg(mImg.get(i % mImg.size()));

            mDatas.add(productList);
        }
    }

}
