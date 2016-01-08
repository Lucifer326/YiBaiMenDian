package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.madapter.CategoryAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类页 ProductsCategoryActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_product_category)
public class ProductsCategoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // 初始化商品分类数据
        initDates();
        // 初始化view
        initViews();
        CategoryAdapter mCategoryAdapter = new CategoryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mCategoryAdapter);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // mRecyclerView.setLayoutManager(linearLayoutManager);
        // 垂直gridview
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        // 水平滚动gridview;
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        //item事件点击
        mCategoryAdapter.setOnItemClickLitener(new CategoryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
               /* Toast.makeText(ProductsCategoryActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();*/
                //打开商品详情 临时
                Intent intent = new Intent(ProductsCategoryActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ProductsCategoryActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initDates() {
        mDatas = new ArrayList<>();

        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.product_category_recyclerview);
    }
}
