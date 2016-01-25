package com.yeebob.yibaimendian.mainactivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.jude.rollviewpager.RollPagerView;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CarouselBean;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.madapter.LoopAdapter;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 锁屏 图片广告轮播
 * Created by WGL on 2016-1-15.
 */
@ContentView(R.layout.activit_carousel_img)
public class CarouselImg extends AppCompatActivity {

    @ViewInject(R.id.roll_view_pager)
    private RollPagerView mRollViewPager;

    @ViewInject(R.id.videoview)
    private FullscreenVideoLayout videoLayout;

    private List<String> mUrls = new ArrayList<>();
    private LoopAdapter mLoopAdapter;

    private List<String> mVideoUris = new ArrayList<>();

    private static final String CAROUSEL_URL = "http://iwshop.yeebob.com/?/Advert/get_advert";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //startCarousel(); //开始轮播
        startVideo();
        getJsonData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void startVideo() {
        videoLayout.setVisibility(View.VISIBLE);
        videoLayout.setActivity(this);
        Uri videoUri = Uri.parse("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");
        try {
            videoLayout.setVideoURI(videoUri);
            videoLayout.hideControls();
            videoLayout.setShouldAutoplay(true);
            videoLayout.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getJsonData() {
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
                CommonJsonList resultObj = CommonJsonList.fromJson(result, CarouselBean.class);
                List<String> urls = new ArrayList<>(); //图片轮播地址
                List<String> uris = new ArrayList<>(); //视频轮播地址
                if (resultObj.getStatus() == 1) {
                    List<CarouselBean> dataObj = resultObj.getData();
                    for (int i = 0; i < dataObj.size(); i++) {
                        if (dataObj.get(i).getAdvert_type().equals("1")) {
                            urls.addAll(dataObj.get(i).getAdvert_resource());
                        } else {
                            uris.addAll(dataObj.get(i).getAdvert_resource());
                        }
                    }
                    mUrls.clear();
                    mUrls.addAll(urls);
                    // mLoopAdapter.notifyDataSetChanged();

                    mVideoUris.clear();
                    mVideoUris.addAll(uris);

                } else {
                    Toast.makeText(x.app(), "获取轮播图错误", Toast.LENGTH_SHORT).show();
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

    private void startCarousel() {
        mUrls.add("http://pic23.nipic.com/20120831/10705080_094138516197_2.jpg");
        mUrls.add("http://pic48.nipic.com/file/20140911/19553859_130737471000_2.jpg");
        mUrls.add("http://pic23.nipic.com/20120813/6906103_153852672124_2.jpg");
        mUrls.add("http://pic.nipic.com/2007-10-12/20071012214019250_2.jpg");
        mRollViewPager.setPlayDelay(5000);
        mRollViewPager.setAnimationDurtion(500);
        mLoopAdapter = new LoopAdapter(mRollViewPager, CarouselImg.this, mUrls);
        mRollViewPager.setAdapter(mLoopAdapter);
        mRollViewPager.setHintView(null);
        //mRollViewPager.setAdapter(new TestNomalAdapter());
        // mRollViewPager.setHintView(new IconHintView(this,R.drawable.point_focus,R.drawable.point_normal));
        //mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
    }


}
