package com.yeebob.yibaimendian.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 加载错误页面
 * Created by WGl on 2016-1-28.
 */
@ContentView(R.layout.loaderro_activity)
public class LoadErrorActivity extends AppCompatActivity {

    @ViewInject(R.id.load_error_data)
    private ImageView loadErrorData;

    private String flag = null;
    PowerManager pm;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        Intent intent = getIntent();
        flag = intent.getStringExtra("errorAcivity");

        if ("error".equals(flag)) {
            loadErrorData.setImageResource(R.drawable.carsouimg);
            pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            wakeLock.acquire(); //点亮屏幕
        }


        loadErrorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag != null) {
                    wakeLock.release(); //释放屏幕长亮
                }
                finish();
            }
        });
    }

}
