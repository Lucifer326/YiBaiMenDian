package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.jsonbean.ProductListBean;
import com.yeebob.yibaimendian.madapter.ProductListAdapter;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ViewInject(R.id.product_select)
    private TextView productSelect;

    @ViewInject(R.id.product_category)
    private TextView productCate;

    private List<ProductListBean> mDatas = new ArrayList<>();
    private ProductListAdapter mCategoryAdapter;
    private String tagId;
    private String catId;
    private PopupWindow mPopupWindow;
    private String[] mVals;
    private List<CateBean> mCatetags = new ArrayList<>();

    private String CateID;
    private String BrandID;

    private Integer shopId;
    private String token;

    private final static String BRAND_URI = "http://iwshop.yeebob.com/?/Brands/get_brand";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tagId = bundle.getString("tag_id", null);
        catId = bundle.getString("cat_id", null);
        if (tagId != null) {
            Log.v("bundle tagId", tagId);
        }
        if (catId != null) {
            Log.v("bundle catId", catId);
            mCatetags = (List<CateBean>) bundle.getSerializable("Catetag");
        }
        // 初始化商品分类数据
        getDates();
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
                Toast.makeText(x.app(), proId.toString(), Toast.LENGTH_SHORT).show();
                Intent intentDetail = new Intent(ProductsListActivity.this, ProductDetailActivity.class);
                intentDetail.putExtra("product_id", proId.toString());
                startActivity(intentDetail);
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

        //商品筛选
        productSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(x.app(), "商品筛选功能...", Toast.LENGTH_SHORT).show();
                showProductFilterWindow(view);
            }
        });

    }

    // 获取网络商品列表数据
    private void getDates() {

        shopId = (Integer) SharedPreferencesUtil.getData(x.app(), "shopid", 0);
        token = (String) SharedPreferencesUtil.getData(x.app(), "token", "");

        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/vProduct/get_vlist");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);
        if (tagId == null && catId != null) {
            params.addBodyParameter("catId", catId);
        }
        if (catId == null && tagId != null) {
            params.addBodyParameter("tag_id", tagId);
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:get_list", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, ProductListBean.class);
                if (resultObj.getStatus() == 1) {
                    mDatas.clear();
                    mDatas.addAll(resultObj.getData());
                    mCategoryAdapter.notifyDataSetChanged();
                    Log.v("get_list", mDatas.toString());

                } else {
                    Toast.makeText(x.app(), "获取商品列表失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    Toast.makeText(x.app(), responseCode, Toast.LENGTH_LONG).show();
                    // ...
                } else { // 其他错误
                    Toast.makeText(x.app(), " 未知错误...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //商品筛选popwindow
    private void showProductFilterWindow(View parent) {

        //加载PopupWindow的布局文件
        final LayoutInflater mInflater = LayoutInflater.from(x.app());
        View view = mInflater.inflate(R.layout.product_filter_window, null);

        final TagFlowLayout mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_cat_flowlayout); //一级分类
        final TagFlowLayout mBrandFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_brand_flowlayout); //品牌
        final Button filterBtn = (Button) view.findViewById(R.id.id_filter_btn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mVals = new String[]
                {"全部", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                        "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                        "Android", "Weclome Hello", "Button Text", "TextView", "Android", "Weclome Hi ", "Button", "TextView", "Hello"
                };
        mCatetags.add(0, new CateBean(0, "全部", "全部"));
        //设置 分类标签
        mFlowLayout.setAdapter(new TagAdapter<CateBean>(mCatetags) {
            @Override
            public View getView(FlowLayout parent, int position, CateBean o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.filter_item_cat, mFlowLayout, false);
                tv.setText(o.getCat_name());
                return tv;
            }
        });
        //监听
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(x.app(), mCatetags.get(position).getCat_id() + mCatetags.get(position).getCat_name(), Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                x.app().setTitle("choose:" + selectPosSet.toString());
                CateID = selectPosSet.toString();
                Toast.makeText(x.app(), "choose" + selectPosSet.toString(), Toast.LENGTH_SHORT).show();

                RequestParams params = new RequestParams(BRAND_URI);
                params.addBodyParameter("shop_id", String.valueOf(shopId));
                params.addBodyParameter("token", token);
                params.addBodyParameter("cat_id",CateID);

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.v("brands",result);
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
        });
        // 商品品牌标签
        mBrandFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.filter_item_brand, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        //监听
        mBrandFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(x.app(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        mBrandFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                x.app().setTitle("choose:" + selectPosSet.toString());
                BrandID = selectPosSet.toString();
                Toast.makeText(x.app(), "choose" + selectPosSet.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //声明并实例化PopupWindow
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置背景颜色
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置触摸行为
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        //为PopupWindow设置弹出的位置
        mPopupWindow.showAtLocation(parent, Gravity.TOP, 0, 0);

    }
}
