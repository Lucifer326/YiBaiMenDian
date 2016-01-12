package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.madapter.CategoryAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类页 ProductsCategoryActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_product_category)
public class ProductsCategoryActivity extends AppCompatActivity {

    @ViewInject(R.id.product_category_recyclerview)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    private List<CateBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // 初始化商品分类数据
        getDates();
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
               /* Toast.makeText(ProductsCategoryActivity.this, mDatas.get(position).getCatName() + " click",
                        Toast.LENGTH_SHORT).show();*/
                //商品详情页
                Intent intent = new Intent(ProductsCategoryActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ProductsCategoryActivity.this, position + " long click",
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
                Intent intent = new Intent(ProductsCategoryActivity.this, ShowShopQrcode.class);
                startActivity(intent);
            }
        });

    }

    private void getDates() {

        mDatas = new ArrayList<>();

        for (int i = 0; i < 21; i++) {
            CateBean cateBean = new CateBean();
            cateBean.setCatId(i);
            cateBean.setCatName("catename" + i);
            cateBean.setCatImg(R.drawable.b);

            mDatas.add(cateBean);
        }
    }

}
