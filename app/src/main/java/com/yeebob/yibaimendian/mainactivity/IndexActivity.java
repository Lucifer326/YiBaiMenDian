package com.yeebob.yibaimendian.mainactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 首页 IndexActivity
 * Created by WGL on 2015-12-29.
 */
@ContentView(R.layout.activity_index)
public class IndexActivity extends AppCompatActivity {

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // App Logo
        mToolbar.setLogo(R.mipmap.ic_launcher);
        // 清空标题文字
        mToolbar.setTitle("");

        this.setSupportActionBar(mToolbar);

        // Menu item click 的监听事件 setSupportActionBar 后才有作用
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        //返回按钮
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.shop_qrcode:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(IndexActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // init toolbar menu layout
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }
}
