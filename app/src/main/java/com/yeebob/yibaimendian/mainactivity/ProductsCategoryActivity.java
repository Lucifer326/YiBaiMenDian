package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.jsonbean.ProductListBean;
import com.yeebob.yibaimendian.madapter.CategoryAdapter;
import com.yeebob.yibaimendian.utils.HttpUtils;
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
 * 商品分类页 ProductsCategoryActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_product_category)
public class ProductsCategoryActivity extends AppCompatActivity {

    @ViewInject(R.id.product_category_recyclerview)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private LinearLayout shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private LinearLayout arrowBack;

    @ViewInject(R.id.id_search_text)
    private EditText searchText;

    @ViewInject(R.id.search_btn)
    private ImageView searchBtn;

    private List<CateBean> mDatas = new ArrayList<>();
    private CategoryAdapter mCategoryAdapter;

    private Integer shopId;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE); //隐藏底部虚拟按键
        // 初始化商品分类数据
        getDates();

        //item事件点击
        mCategoryAdapter = new CategoryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mCategoryAdapter);
        // 垂直gridview
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        // 水平滚动gridview;

        mCategoryAdapter.setOnItemClickLitener(new CategoryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                if (mDatas.get(position).getCat_id() >= 0) {
                    Intent intent = new Intent(ProductsCategoryActivity.this, ProductsCategoryListActivity.class);
                    intent.putExtra("cat_id", String.valueOf(mDatas.get(position).getCat_id()));
                    startActivity(intent);  // 打开商品列表*/
                } else {
                    startErrorActivity();
                }

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
        //响应软键盘搜索
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String keyword = searchText.getText().toString();

                    if ("".equals(keyword)) {
                        Toast.makeText(x.app(), "请输入搜索关键词", Toast.LENGTH_SHORT).show();
                    } else {
                        getSearchData(keyword);
                    }
                    return true;
                }
                return false;
            }
        });
        //响应点击按钮搜索
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
        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_vlist");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);
        params.addBodyParameter("keywords", keyword);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Log.v("result:keywords", result);
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

    private void getDates() {

        shopId = (Integer) SharedPreferencesUtil.getData(ProductsCategoryActivity.this, "shopid", 0);
        token = (String) SharedPreferencesUtil.getData(ProductsCategoryActivity.this, "token", "");

        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_cate");
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

    //错误页面
    private void startErrorActivity() {
        Intent intent = new Intent(x.app(), LoadErrorActivity.class);
        startActivity(intent);
    }

}
