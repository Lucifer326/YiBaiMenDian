package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.ProductListBean;
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
public class ProductsSearchActivity extends AppCompatActivity {

    @ViewInject(R.id.tag_category_recyclerview)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    @ViewInject(R.id.product_select)
    private TextView productSelect;

    @ViewInject(R.id.id_search_text)
    private TextView searchText;

    private List<ProductListBean> mDatas = new ArrayList<>();
    private ProductListAdapter mCategoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
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

        // 返回事件
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


}
