package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 登陆页 LoginActivity
 * Created by WGl on 2015-12-29.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewInject(R.id.login_submit)
    private Button SubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        //打开新页面
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录成功进入首页
                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });

    }

}
