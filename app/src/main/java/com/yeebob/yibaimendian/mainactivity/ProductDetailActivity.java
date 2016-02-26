package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.DetailBean;
import com.yeebob.yibaimendian.jsonbean.ProductStyleBean;
import com.yeebob.yibaimendian.madapter.GalleryAdapter;
import com.yeebob.yibaimendian.utils.HttpUtils;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页 ProductDetailActivity
 * Created by WGl on 2016-1-6.
 */
@ContentView(R.layout.activity_product_detail)
public class ProductDetailActivity extends AppCompatActivity {

    @ViewInject(R.id.shop_qrcode)
    private LinearLayout shopQrcode;

    @ViewInject(R.id.id_search)
    private LinearLayout searchBar;

    @ViewInject(R.id.id_arrow_back)
    private LinearLayout arrowBack;

    @ViewInject(R.id.holer_style)
    private TextView holderStyle;

    @ViewInject(R.id.detail_pic_recyclerView)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.id_image_content)
    private ImageView mImg;

    @ViewInject(R.id.id_qrcode_image)
    private ImageView productQrcode;

    @ViewInject(R.id.id_detail_name)
    private TextView ProductDesc;

    @ViewInject(R.id.id_product_price)
    private TextView productPrice;

    @ViewInject(R.id.id_product_instock)
    private TextView productInstock;

    @ViewInject(R.id.id_style_flowlayout)
    private TagFlowLayout mFlowLayout;

    private List<String> mDatas = new ArrayList<>();
    private GalleryAdapter mAdapter;
    private List<ProductStyleBean> styleDatas = new ArrayList<>();
    private TagAdapter<ProductStyleBean> tagAdapter;


    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true)
            .showImageForEmptyUri(R.drawable.productdetailerror)//设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.productdetailerror)  //设置图片加载/解码过程中错误时候显示的图片
            .displayer(new RoundedBitmapDisplayer(15)) //圆角处理
            .bitmapConfig(Bitmap.Config.RGB_565).build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        getWindow().getDecorView().setSystemUiVisibility(View.GONE); //隐藏底部虚拟按键

        shopQrcode.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);

        getJsonData();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new GalleryAdapter(ProductDetailActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        // mImg.setImageResource(mDatas.get(0));
        // 显示大图片
        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//				Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT)
//						.show();
                //  mImg.setImageResource(mDatas.get(position));
                ImageLoader.getInstance().displayImage(mDatas.get(position), mImg, options);
            }
        });

        // 返回上一页面
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    public void getJsonData() {
        Intent intent = getIntent();
        String productId = intent.getStringExtra("product_id");
        // Log.v("productid", productId); //商品id
        Integer shopId = (Integer) SharedPreferencesUtil.getData(ProductDetailActivity.this, "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(ProductDetailActivity.this, "token", "");

        RequestParams params = new RequestParams(HttpUtils.BASEURL + "vProduct/product_details");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);
        params.addBodyParameter("product_id", productId); //商品ID

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result:xiangqing", result);
                Gson gson = new Gson();
                DetailBean resultObj = gson.fromJson(result, DetailBean.class);
                if (resultObj.getStatus() == 1) {
                    // Log.v("getdata", resultObj.getData().toString());
                    mDatas.clear();
                    mDatas.addAll(resultObj.getData().getProduct_images());
                    mAdapter.notifyDataSetChanged();
                    setDataForDetail(resultObj);
                } else {
                    finish();
                    startErrorActivity();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                finish();
                startErrorActivity(); //打开错误页面
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void setDataForDetail(DetailBean resultObj) {
        // 设置商品详情首图
        ImageLoader.getInstance().displayImage(resultObj.getData().getCatimg(), mImg, options);
        // 设置二维码
        ImageLoader.getInstance().displayImage(resultObj.getData().getTwc_image(), productQrcode);
        // Log.v("qrcode", resultObj.getData().getTwc_image());
        //设置商品名称
        ProductDesc.setText(resultObj.getData().getProduct_name()); //商品名称
        String proPrice = "".equals(resultObj.getData().getSell_price()) ? "暂无" : "￥ " + resultObj.getData().getSell_price();
        productPrice.setText(proPrice); //设置价格
        String proInstocks = "".equals(resultObj.getData().getProduct_instocks()) ? "暂无" : resultObj.getData().getProduct_instocks();
        productInstock.setText(proInstocks);  //设置库存
        styleDatas.clear();
        //型号设置
        List<DetailBean.DataEntity.AttrEntity> obj = resultObj.getData().getAttr();
        if (obj.size() > 0) {
            for (int i = 0; i < resultObj.getData().getAttr().size(); i++) {
                ProductStyleBean productStyleBean = new ProductStyleBean();
                productStyleBean.setProduct_id(resultObj.getData().getAttr().get(i).getProduct_id()); //商品iD
                productStyleBean.setSpec_det_id1(resultObj.getData().getAttr().get(i).getSpec_det_id1());    //商品规格标号
                productStyleBean.setSpec_name(resultObj.getData().getAttr().get(i).getSpec_name());  //商品名字
                productStyleBean.setSale_price(resultObj.getData().getAttr().get(i).getSale_price());    //商品价格
                productStyleBean.setInstock(resultObj.getData().getAttr().get(i).getInstock());  //库存

                styleDatas.add(productStyleBean);
            }
            if (styleDatas.size() > 0 && styleDatas.get(0).getSpec_name() != null) {
                holderStyle.setVisibility(View.VISIBLE);
                mFlowLayout.setVisibility(View.VISIBLE);
                //holderStyle.setText(styleDatas.get(0).getSpec_name());
                mFlowLayout.setAdapter(tagAdapter = new TagAdapter<ProductStyleBean>(styleDatas) {
                    @Override
                    public View getView(FlowLayout parent, int position, ProductStyleBean productStyleBean) {
                        TextView tv = (TextView) LayoutInflater.from(x.app()).inflate(R.layout.product_style_item, mFlowLayout, false);
                        tv.setText(styleDatas.get(position).getSpec_name());
                        return tv;
                    }
                });
                tagAdapter.setSelectedList(0);

            }

            productPrice.setText("￥ " + styleDatas.get(0).getSale_price()); //设置价格
            productInstock.setText(styleDatas.get(0).getInstock());  //设置库存
            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    productPrice.setText("￥ " + styleDatas.get(position).getSale_price());
                    productInstock.setText(styleDatas.get(position).getInstock());
                    return true;
                }
            });
        } else {
            holderStyle.setVisibility(View.GONE);
        }
    }
    //错误页面
    private void startErrorActivity() {
        Intent intent = new Intent(x.app(), LoadErrorActivity.class);
        startActivity(intent);
    }
}
