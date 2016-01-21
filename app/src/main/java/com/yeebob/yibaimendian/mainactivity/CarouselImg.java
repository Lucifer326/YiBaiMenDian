package com.yeebob.yibaimendian.mainactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 锁屏 图片广告轮播
 * Created by WGL on 2016-1-15.
 */
@ContentView(R.layout.activit_carousel_img)
public class CarouselImg extends AppCompatActivity {

    @ViewInject(R.id.roll_view_pager)
    private RollPagerView mRollViewPager;

    private  static final String CAROUSEL_URL = "http://iwshop.yeebob.com//?/Advert/get_advert";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        getJsonData();
    }

    private void getJsonData(){
        Integer shopId = (Integer) SharedPreferencesUtil.getData(CarouselImg.this, "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(CarouselImg.this, "token", "");

        RequestParams params = new RequestParams(CAROUSEL_URL);
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               /* CommonJsonList resultObj = CommonJsonList.fromJson(result, BannerBean.class);*/
                Log.v("bean", result);
               /* if (resultObj.getStatus() == 1) {

                } else {
                    Toast.makeText(x.app(), "获取轮播图错误", Toast.LENGTH_SHORT).show();
                }*/
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

    private void startCarousel(){
        mRollViewPager.setPlayDelay(5000);
        mRollViewPager.setAnimationDurtion(500);
        mRollViewPager.setAdapter(new LoopAdapter(mRollViewPager));
        mRollViewPager.setHintView(null);
        //mRollViewPager.setAdapter(new TestNomalAdapter());
        // mRollViewPager.setHintView(new IconHintView(this,R.drawable.point_focus,R.drawable.point_normal));
        //mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
    }

    private class LoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4,
        };

        public LoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }

}
