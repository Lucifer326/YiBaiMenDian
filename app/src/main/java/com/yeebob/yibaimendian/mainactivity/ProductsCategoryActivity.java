package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.madapter.CategoryAdapter;
import com.yeebob.yibaimendian.madapter.PageRecyclerView;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
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
    //   private PageRecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    private List<CateBean> mDatas = new ArrayList<>();
    private CategoryAdapter mCategoryAdapter;
    private PageRecyclerView.PageAdapter myAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // 初始化商品分类数据
        getDates();

     /*   mRecyclerView.setAdapter(myAdapter = mRecyclerView.new PageAdapter(mDatas, new PageRecyclerView.CallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int postion) {
                View view = LayoutInflater.from(ProductsCategoryActivity.this).inflate(R.layout.item_list_product, parent, false);
                return new MyHolder(view);

            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
               *//**//* ((MyHolder)holder).imageView.setImageResource(mDatas.get(position).getCatImg());*//**//*
                ImageLoader.getInstance().displayImage(mDatas.get(position).getCat_image(),((MyHolder)holder).imageView,options, new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                        ((MyHolder)holder).imageView.setImageResource(R.drawable.brand_1);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        ((MyHolder)holder).imageView.setImageBitmap(loadedImage);
                    }
                });

            }

            @Override
            public void onItemClickListener(View view, int position) {
               *//* Toast.makeText(ProductsCategoryActivity.this, "点击："
                        + mDatas.get(position).getCatName(), Toast.LENGTH_SHORT).show();*//*
                Intent intent = new Intent(ProductsCategoryActivity.this, ProductsListActivity.class);
                startActivity(intent);

            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        }));*/
        // 设置行数和列数
    /*    mRecyclerView.setPageSize(2, 5);
        // 设置页间距
        mRecyclerView.setPageMargin(20);*/
        //item事件点击
        mCategoryAdapter = new CategoryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mCategoryAdapter);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // mRecyclerView.setLayoutManager(linearLayoutManager);
        // 垂直gridview
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        // 水平滚动gridview;
      /*  mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));*/

        mCategoryAdapter.setOnItemClickLitener(new CategoryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
            /*    Toast.makeText(ProductsCategoryActivity.this, mDatas.get(position).getCat_id() + " click",
                        Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(ProductsCategoryActivity.this, ProductsListActivity.class);
                intent.putExtra("cat_id", String.valueOf(mDatas.get(position).getCat_id()));
                startActivity(intent);  // 打开商品列表*/
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

        Integer shopId = (Integer) SharedPreferencesUtil.getData(ProductsCategoryActivity.this, "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(ProductsCategoryActivity.this, "token", "");

        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/vProduct/get_cate");
        params.addBodyParameter("cat_id", "0"); //商品分类 默认0
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:cat", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, CateBean.class);
                if (resultObj.getStatus() == 1) {
                    mDatas.clear();
                    mDatas.addAll(resultObj.getData());
                    mCategoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(x.app(), "获取商品信息失败", Toast.LENGTH_SHORT).show();
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

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView imageView = null;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.id_cate_image);
        }
    }

}
