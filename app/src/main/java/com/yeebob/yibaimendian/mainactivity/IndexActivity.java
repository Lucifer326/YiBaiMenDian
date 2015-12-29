package com.yeebob.yibaimendian.mainactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 首页 IndexActivity
 * Created by WGL on 2015-12-29.
 */
@ContentView(R.layout.indexactivity)
public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
