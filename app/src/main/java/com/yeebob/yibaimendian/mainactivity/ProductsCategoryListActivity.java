package com.yeebob.yibaimendian.mainactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.madapter.CategoryAdapter;
import com.yeebob.yibaimendian.utils.HttpUtils;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类品牌列表页 ProductsCategoryListActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_product_category)
public class ProductsCategoryListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @ViewInject(R.id.shop_qrcode)
    private TextView shopQrcode;

    @ViewInject(R.id.id_arrow_back)
    private TextView arrowBack;

    private List<CateBean> mDatas = new ArrayList<>();
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // 初始化商品分类数据
        getJsonDates();

        mCategoryAdapter = new CategoryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mCategoryAdapter);
        // 垂直gridview
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    private void getJsonDates() {
        Integer shopId = (Integer) SharedPreferencesUtil.getData(ProductsCategoryListActivity.this, "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(ProductsCategoryListActivity.this, "token", "");

        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_cate");
        params.addBodyParameter("cat_id", "0"); //商品分类 默认0
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Log.v("result:cat", result);
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
}
