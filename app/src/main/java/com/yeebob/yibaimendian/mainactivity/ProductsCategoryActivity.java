package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.madapter.PageRecyclerView;

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
//    private RecyclerView mRecyclerView;
    private PageRecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    private List<CateBean> mDatas;
    private PageRecyclerView.PageAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // 初始化商品分类数据
        getDates();
        // 设置行数和列数
        mRecyclerView.setPageSize(2, 5);
        // 设置页间距
        mRecyclerView.setPageMargin(20);
        mRecyclerView.setAdapter(myAdapter = mRecyclerView.new PageAdapter(mDatas, new PageRecyclerView.CallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int postion) {
                View view = LayoutInflater.from(ProductsCategoryActivity.this).inflate(R.layout.item_list_product, parent, false);
                return new MyHolder(view);

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((MyHolder)holder).imageView.setImageResource(mDatas.get(position).getCatImg());

            }

            @Override
            public void onItemClickListener(View view, int position) {
               /* Toast.makeText(ProductsCategoryActivity.this, "点击："
                        + mDatas.get(position).getCatName(), Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(ProductsCategoryActivity.this, ProductDetailActivity.class);
                startActivity(intent);

            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        }));
        //CategoryAdapter mCategoryAdapter = new CategoryAdapter(this, mDatas);
        // mRecyclerView.setAdapter(mCategoryAdapter);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // mRecyclerView.setLayoutManager(linearLayoutManager);
        // 垂直gridview
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        // 水平滚动gridview;
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        //item事件点击
      /*  mCategoryAdapter.setOnItemClickLitener(new CategoryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
               *//* Toast.makeText(ProductsCategoryActivity.this, mDatas.get(position).getCatName() + " click",
                        Toast.LENGTH_SHORT).show();*//*
                //商品详情页
                Intent intent = new Intent(ProductsCategoryActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ProductsCategoryActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

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
        List<Integer> mImg = new ArrayList<>();
        mImg.add(R.drawable.brand_1);
        mImg.add(R.drawable.brand_2);
        mImg.add(R.drawable.brand_3);
        mImg.add(R.drawable.brand_4);
        mImg.add(R.drawable.brand_5);
        for (int i = 1; i < 22; i++) {
            CateBean cateBean = new CateBean();
            cateBean.setCatId(i);
            cateBean.setCatName("catename" + i);
            cateBean.setCatImg(mImg.get(i % mImg.size()));

            mDatas.add(cateBean);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView imageView = null;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.id_cate_image);
        }
    }

}
