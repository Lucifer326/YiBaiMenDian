package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.BannerBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.madapter.ImageAdapter;
import com.yeebob.yibaimendian.utils.HttpUtils;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页 IndexActivity
 * Created by WGL on 2015-12-29.
 */
@ContentView(R.layout.activity_index)
public class IndexActivity extends AppCompatActivity {

    @ViewInject(R.id.index_toolbar)
    private Toolbar mToolbar;
    @ViewInject(R.id.id_shop_qrcode)
    private LinearLayout shopQrcode;
    @ViewInject(R.id.id_product_category)
    private LinearLayout productCate;

    private Integer shopId;
    private String token;

    private Gallery mGallery;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mHandler = new Handler();
    private List<BannerBean> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE); //隐藏底部虚拟按键
        x.view().inject(this);

        this.setSupportActionBar(mToolbar);
      /*  Intent mService = new Intent(IndexActivity.this, ScreenService.class);//启动服务
        mService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(mService);*/
        getJsonData();
        shopQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startProductCategory(); //打开商品分类
            }
        });

        productCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQrcodeActivity(); //商城二维码
            }
        });


    }

    private void startBannert() {

        /*商品推荐轮播*/
        mGallery = ((Gallery) findViewById(R.id.gallery));
        //Pics图片的Resources阵列
        mGallery.setAdapter(new ImageAdapter(this, mDatas));
        //图片透明度
        mGallery.setUnselectedAlpha(255);
        //图片不渐变，渐变长为0
        mGallery.setFadingEdgeLength(0);
        //图片不重叠，图片间距为0
        mGallery.setSpacing(36);
        //图片一开始设定在第几张Integer.MAX_VALUE/2的位置(Integer.MAX_VALUE为int的最高值)
        mGallery.setSelection(Integer.MAX_VALUE / 2);
        //轮播的速度
        mGallery.setAnimationDuration(5000);
        //设定图片点击触发
        mGallery.setOnItemClickListener(click);
        mGallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN: { //按住事件发生后执行代码的区域
                        stopAutoScroller();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: { //移动事件发生后执行代码的区域
                        stopAutoScroller();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {   //松开事件发生后执行代码的区域
                        startAutoScroller();
                        break;
                    }

                    default:

                        break;
                }
                return false;
            }
        });
        /*商品推荐轮播*/
    }

    private void getJsonData() {
        shopId = (Integer) SharedPreferencesUtil.getData(IndexActivity.this, "shopid", 0);
        token = (String) SharedPreferencesUtil.getData(IndexActivity.this, "token", "");

        RequestParams params = new RequestParams(HttpUtils.BASEURL + "Banner/banner_list");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonJsonList resultObj = CommonJsonList.fromJson(result, BannerBean.class);
                Log.v("bean", result);
                if (resultObj.getStatus() == 1) {
                    mDatas = resultObj.getData();
                    //Log.v("xxxxxx", mDatas.toString());
                    if (mDatas.size() > 0) {
                        startScreeSaver();
                        startBannert();
                    }

                } else {
                    Toast.makeText(x.app(), "获取轮播图错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "网络错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private AdapterView.OnItemClickListener click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            String bannerId = mDatas.get(arg2 % mDatas.size()).getBanner_id();


            if ("".equals(bannerId)) {
                Toast.makeText(x.app(), "暂无商品推荐，敬请期待!", Toast.LENGTH_SHORT).show();
            } else {
                Intent bannerIntent = new Intent(IndexActivity.this, ProductsListActivity.class);
                bannerIntent.putExtra("banner_id", bannerId);
                startActivity(bannerIntent);
            }

        }
    };


    private void startProductCategory() {
        // 打开商品品牌分类页
        Intent intent = new Intent(IndexActivity.this, ProductsCategoryActivity.class);
        startActivity(intent);
    }

    private void startQrcodeActivity() {
        // 打开微信商城二维码
        Intent intent = new Intent(IndexActivity.this, ShowShopQrcode.class);
        startActivity(intent);
    }

    private void startAutoScroller() {
        //因下方會重新new Timer，避免重複佔據系統不必要的資源，在此確認mTimer是否為null
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        //因下方會重新new TimerTask，避免重複佔據系統不必要的資源，在此確認mTimerTask是否為null
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        //建立Timer
        mTimer = new Timer();
        //建立TimerTask
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //每3秒觸發時要做的事
                        //scroll 3
                        mGallery.onScroll(null, null, 3, 0);
                        //向右滾動
                        mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    }
                });
            }
        };

        //從3秒開始，每3秒觸發一次，每三秒自動滾動
        mTimer.schedule(mTimerTask, 5000, 5000);
    }

    private void stopAutoScroller() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private void startScreeSaver() {
        Intent mService = new Intent(IndexActivity.this, ScreenService.class);//启动服务
        mService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(mService);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDatas.size() > 0) {
            stopAutoScroller();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDatas.size() > 0) {
            startAutoScroller();
        }
    }

}
