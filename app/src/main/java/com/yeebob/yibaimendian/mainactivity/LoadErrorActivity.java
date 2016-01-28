package com.yeebob.yibaimendian.mainactivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //隐藏toolbar name

        loadErrorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
