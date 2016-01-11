package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.madapter.ImageAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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
    private TextView shopQrcode;
    @ViewInject(R.id.id_product_category)
    private TextView productCate;

    private Gallery mGallery;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mHandler = new Handler();
    private int[] Pics = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*********/
        mGallery = ((Gallery) findViewById(R.id.gallery));
        //Pics為要顯示圖片的Resources陣列
        mGallery.setAdapter(new ImageAdapter(this , Pics));
        //圖片不透明
        mGallery.setUnselectedAlpha( 255 );
        //圖片不漸層，漸層長度為0
        mGallery.setFadingEdgeLength( 0 );
        //圖片不重疊，圖片間距為0
        mGallery.setSpacing(200);
        //圖片一開始顯示在第幾張設定在Integer.MAX_VALUE/2的位置(Integer.MAX_VALUE為int的最高值)
        mGallery.setSelection(Integer.MAX_VALUE/2);
        //圖片在切換圖片的速度
        mGallery.setAnimationDuration(2000);
        //設定點擊圖片時觸發
        mGallery.setOnItemClickListener(click);
        /*********/

        shopQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQrcodeActivity(); //商城二维码
            }
        });

        productCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductCategory(); //打开商品分类
            }
        });


    }
    private AdapterView.OnItemClickListener click = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            Toast.makeText(IndexActivity.this, "您點擊第" + ((arg2 % Pics.length) + 1) + "張圖片", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        if(mTimer!=null)
        {
            mTimer.cancel();
            mTimer=null;
        }

        if(mTimerTask!=null)
        {
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //因下方會重新new Timer，避免重複佔據系統不必要的資源，在此確認mTimer是否為null
        if(mTimer!=null)
        {
            mTimer.cancel();
            mTimer=null;
        }

        //因下方會重新new TimerTask，避免重複佔據系統不必要的資源，在此確認mTimerTask是否為null
        if(mTimerTask!=null)
        {
            mTimerTask.cancel();
            mTimerTask=null;
        }

        //建立Timer
        mTimer = new Timer();
        //建立TimerTask
        mTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //每3秒觸發時要做的事
                        //scroll 3
                        mGallery.onScroll(null, null, 3, 0);
                        //向右滾動
                        mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT , null);
                    }
                });
            }
        };

        //從3秒開始，每3秒觸發一次，每三秒自動滾動
        mTimer.schedule(mTimerTask, 3000, 3000);
    }
}
