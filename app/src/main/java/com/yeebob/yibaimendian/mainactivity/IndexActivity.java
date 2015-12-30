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
@ContentView(R.layout.indexactivity)
public class IndexActivity extends AppCompatActivity {

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // App Logo
        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle("翼百门店");
        // Sub Title
        mToolbar.setSubtitle("go go go");
        setSupportActionBar(mToolbar);
        // 设置导航栏图标
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // 菜单事件监听
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
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
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
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
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
       // return super.onCreateOptionsMenu(menu);
    }
}
