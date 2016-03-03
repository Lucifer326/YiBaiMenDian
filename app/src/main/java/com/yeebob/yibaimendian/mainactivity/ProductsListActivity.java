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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CateBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.jsonbean.ProductListBean;
import com.yeebob.yibaimendian.jsonbean.TagBrandBean;
import com.yeebob.yibaimendian.madapter.ProductListAdapter;
import com.yeebob.yibaimendian.utils.HttpUtils;
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

import java.io.Serializable;
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
    private LinearLayout shopQrcode;


    @ViewInject(R.id.product_filter)
    private LinearLayout productFilter;

    @ViewInject(R.id.id_arrow_back)
    private LinearLayout arrowBack;

    @ViewInject(R.id.id_search_text)
    private EditText searchText;

    @ViewInject(R.id.search_btn)
    private ImageView searchBtn;


    private List<ProductListBean> mDatas = new ArrayList<>();
    private List<ProductListBean> tmpDatas = new ArrayList<>();


    private ProductListAdapter mCategoryAdapter;
    private PopupWindow mPopupWindow;
    private List<CateBean> mCatetags = new ArrayList<>();
    private List<TagBrandBean> tagBrandBeans = new ArrayList<>();

    private TagAdapter mBrandTagAdapter;
    private TagFlowLayout mBrandFlowLayout;

    private int selectCatePos = -1;
    private int selectBrandPos = -1;

    private Integer shopId;
    private String token;

    private final static String BRAND_URI = HttpUtils.BASEURL + "Brands/get_brand";

    private final static int CATIDTAG = 0;  //分类标识
    private final static int BANNERTAG = 1;  //轮播标识
    private final static int CUSTOMTAG = 2; //自定义标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);


        final Intent intent = getIntent();

        String tagId = intent.getStringExtra("tag_id");
        final String catId = intent.getStringExtra("cat_id");
        String bannerId = intent.getStringExtra("banner_id");

        if (tagId != null) {
            getDates(CUSTOMTAG, tagId);
        }
        if (catId != null) {
            getSenCate(catId); //获取分类tag
            getALlBrand(catId); //获取品牌tag
            getDates(CATIDTAG, catId); //获取所有商品
            productFilter.setVisibility(View.VISIBLE);
        } else {
            productFilter.setVisibility(View.GONE);
        }
        if (bannerId != null) {
            getDates(BANNERTAG, bannerId);
        }

        // 初始化商品分类数据
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
                Intent intentDetail = new Intent(ProductsListActivity.this, ProductDetailActivity.class);
                intentDetail.putExtra("product_id", proId.toString());
                startActivity(intentDetail);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(ProductsListActivity.this, position + " long click",
//                        Toast.LENGTH_SHORT).show();
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
        // 搜索商品
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

        //商品筛选
        productFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProductFilterWindow(view);
            }
        });
        //搜索
    }

    // 获取二级分类标签
    private void getSenCate(String catId) {
        shopId = (Integer) SharedPreferencesUtil.getData(ProductsListActivity.this, "shopid", 0);
        token = (String) SharedPreferencesUtil.getData(ProductsListActivity.this, "token", "");

        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_cate");
        params.addBodyParameter("cat_id", catId); //商品分类 默认0
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("catgor: ", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, CateBean.class);
                if (resultObj.getStatus() == 1 && resultObj.getData().size() > 0) {
                    mCatetags.clear();
                    for (int i = 0; i < resultObj.getData().size(); i++) {
                        CateBean cateBean = (CateBean) resultObj.getData().get(i);
                        mCatetags.add(cateBean);
                    }
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

    private void getSearchData(String keyword) {
        //获取搜索商品
        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_vlist");
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

    //获取筛选数据
    private void getFilterData(String catId2, String brandId) {

        //获取筛选商品
        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_vlist");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        if (catId2 == null && "".equals(catId2)) {
            return;
        }

        if (brandId == null && "".equals(brandId)) {
            return;
        }
        params.addBodyParameter("cat_id", catId2);
        params.addBodyParameter("brand_id", brandId);


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:filter", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, ProductListBean.class);
                if (resultObj.getStatus() == 1) {
                    List<ProductListBean> datas = resultObj.getData();
                    if (datas.size() > 0) {
                        mDatas.clear();
                        mDatas.addAll(datas);
                        mCategoryAdapter.notifyDataSetChanged();

                    }
                } else {
                    Toast.makeText(x.app(), "暂无商品...", Toast.LENGTH_SHORT).show();
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

    // 获取网络商品列表数据
    private void getDates(final int flag, final String id) {

        shopId = (Integer) SharedPreferencesUtil.getData(x.app(), "shopid", 0);
        token = (String) SharedPreferencesUtil.getData(x.app(), "token", "");

        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/get_vlist");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        switch (flag) {
            case 0:
                params.addBodyParameter("cat_id", id); //一级分类catID
                break;
            case 1:
                params.addBodyParameter("banner_id", id);
                break;
            case 2:
                params.addBodyParameter("tag_id", id);
                break;
            default:
                break;
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:get_listxxxx", String.valueOf(flag));
                Log.v("result:get_listxxxx", id);
                Log.v("result:get_listxxxx", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, ProductListBean.class);
                if (resultObj.getStatus() == 1 && resultObj.getData().size() > 0) {
                    mDatas.clear();
                    mDatas.addAll(resultObj.getData());
                    mCategoryAdapter.notifyDataSetChanged();
                    // 暂存
                    tmpDatas.clear();
                    tmpDatas.addAll(resultObj.getData());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    startErrorActivity();
                    // ...
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
    private void showProductFilterWindow(final View parent) {

        //加载全部分类标签

        //加载PopupWindow的布局文件
        final LayoutInflater mInflater = LayoutInflater.from(x.app());
        final View view = mInflater.inflate(R.layout.product_filter_window, null);

        final TagFlowLayout mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_cat_flowlayout); //一级分类
        mBrandFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_brand_flowlayout); //品牌
        final ImageButton filterBtn = (ImageButton) view.findViewById(R.id.id_filter_btn);

        //获取分类商品
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (selectCatePos == 0 && -1 == selectBrandPos) {
                    mDatas.clear();
                    mDatas.addAll(tmpDatas);
                    mCategoryAdapter.notifyDataSetChanged();
                } else if (selectCatePos == -1 && -1 == selectBrandPos) {

                } else if (selectCatePos >= 0 && selectBrandPos != -1) {
                    String catId2 = String.valueOf(mCatetags.get(selectCatePos).getCat_id());
                    String brandId = tagBrandBeans.get(selectBrandPos).getId();
                    getFilterData(catId2, brandId);
                } else if (selectCatePos >= 0 && selectBrandPos == -1) {
                    String catId2 = String.valueOf(mCatetags.get(selectCatePos).getCat_id());
                    String brandId = null;
                    getFilterData(catId2, brandId);
                }*/
                if (selectCatePos > 0 && selectBrandPos >= 0) {
                    String catId2 = String.valueOf(mCatetags.get(selectCatePos).getCat_id());
                    String brandId = tagBrandBeans.get(selectBrandPos).getId();
                    getFilterData(catId2, brandId);
                }


                mPopupWindow.dismiss();
            }
        });

        if (mCatetags.size() > 0) {
            if (mCatetags.get(0).getCat_id() != 0) {
                mCatetags.add(0, new CateBean(0, "全部", "全部"));
            }
        } else {
            mCatetags.add(0, new CateBean(0, "全部", "全部"));
        }


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
                return true;
            }
        });
        //二级分类标签监听
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {

            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //从set集合取值
                int pos = 0;
                for (Integer position : selectPosSet) {
                    pos = position;
                }
                //重置标签选取位置
                selectCatePos = pos;
                Log.v("selectCatePos", String.valueOf(selectCatePos));


            }
        });
        // 商品品牌标签
        mBrandFlowLayout.setAdapter(mBrandTagAdapter = new TagAdapter<TagBrandBean>(tagBrandBeans) {
            @Override
            public View getView(FlowLayout parent, int position, TagBrandBean tagBrandBean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.filter_item_brand, mFlowLayout, false);
                if (tagBrandBeans.size() - 1 == position) {
                    tv.setText(tagBrandBeans.get(position).getBrand_name());
                    tv.setCompoundDrawables(null, null, null, null);
                } else {
                    tv.setText(tagBrandBeans.get(position).getBrand_name());
                }
                return tv;
            }
        });
        //监听
        mBrandFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        mBrandFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                int pos = 0;
                for (Integer id : selectPosSet) {
                    pos = id;
                }
                selectBrandPos = pos;
                Log.v("selectBrandPos", String.valueOf(selectBrandPos));

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

    //获取所有品牌

    private void getALlBrand(String catid) { //通过一级分类标签获取所有品牌

        RequestParams params = new RequestParams(BRAND_URI);
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);
        params.addBodyParameter("cat_id", catid);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("brands", result);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, TagBrandBean.class);
                if (resultObj.getStatus() == 1) {
                    List<TagBrandBean> obj = (List<TagBrandBean>) resultObj.getData();
                    if (resultObj.getData().size() > 0) {
                        tagBrandBeans.addAll(obj);
                        mBrandTagAdapter.notifyDataChanged();
                        mBrandFlowLayout.setVisibility(View.VISIBLE);
                    } else {
                        mBrandFlowLayout.setVisibility(View.INVISIBLE);
                    }
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
        Intent errorIntent = new Intent(x.app(), LoadErrorActivity.class);
        startActivity(errorIntent);

    }
}