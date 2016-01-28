package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.jsonbean.ProductListBean;
import com.yeebob.yibaimendian.madapter.ProductListAdapter;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品自定义推广分类页 ProductsListActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_category_tag)
public class ProductsSearchActivity extends AppCompatActivity {

    @ViewInject(R.id.tag_category_recyclerview)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private LinearLayout shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private LinearLayout arrowBack;

    @ViewInject(R.id.product_filter)
    private LinearLayout productFilter;



    @ViewInject(R.id.id_search_text)
    private EditText searchText;

    @ViewInject(R.id.search_btn)
    private ImageView searchBtn;

    private List<ProductListBean> mDatas = new ArrayList<>();
    private ProductListAdapter mCategoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        productFilter.setVisibility(View.GONE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mDatas = (List<ProductListBean>) bundle.getSerializable("searchdata");
        Log.v("mdatas", mDatas.toString());


        mCategoryAdapter = new ProductListAdapter(this, mDatas);
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
                Integer proId = mDatas.get(position).getProduct_id();
                Intent intentDetail = new Intent(ProductsSearchActivity.this, ProductDetailActivity.class);
                intentDetail.putExtra("product_id", proId.toString());
                startActivity(intentDetail);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        // 商城二维码展示
        shopQrcode.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent intent = new Intent(ProductsSearchActivity.this, ShowShopQrcode.class);
                                              startActivity(intent);
                                          }
                                      }

        );
        //返回按钮
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 响应搜索
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchText.getText().toString();

                if ("".equals(keyword)) {
                    Toast.makeText(x.app(), "请输入搜索关键词", Toast.LENGTH_SHORT).show();
                } else {
                    getSearchData(keyword);
                }
            }
        });
    }

    private void getSearchData(String keyword) {
        //获取搜索商品
        Integer shopId = (Integer) SharedPreferencesUtil.getData(ProductsSearchActivity.this, "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(ProductsSearchActivity.this, "token", "");

        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/vProduct/get_vlist");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);
        params.addBodyParameter("keywords", keyword);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:keywords", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, ProductListBean.class);
                if (resultObj.getStatus() == 1) {
                    List<ProductListBean> datas = resultObj.getData();
                    if (datas.size() > 0) {
                        Intent searhIntent = new Intent(x.app(), ProductsSearchActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("searchdata", (Serializable) datas);
                        searhIntent.putExtras(bundle);
                        startActivity(searhIntent);
                    } else {
                        startErrorActivity();
                    }


                } else {
                    startErrorActivity();
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

    private void startErrorActivity(){
        Intent errorIntent = new Intent(x.app(),LoadErrorActivity.class);
        startActivity(errorIntent);
    }
}
