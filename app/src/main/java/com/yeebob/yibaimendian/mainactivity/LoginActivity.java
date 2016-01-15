package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.Paramers;
import com.yeebob.yibaimendian.jsonbean.User;
import com.yeebob.yibaimendian.jsonbean.UserParams;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 登陆页 LoginActivity
 * Created by WGl on 2015-12-29.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewInject(R.id.login_username)
    private EditText username;

    @ViewInject(R.id.login_password)
    private EditText password;

    @ViewInject(R.id.login_submit)
    private Button submit;

    private String userName;
    private String userPassword;

   /* public SharedPreferences preferences;
    public SharedPreferences.Editor editor;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        // 获取只能被本应用程序读、写的SharedPreferences对象
       // preferences = getSharedPreferences("token", MODE_PRIVATE);
        //获得修改器
       // editor = preferences.edit();
        //打开新页面
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户名密码
                userName = username.getText().toString();
                userPassword = password.getText().toString();
                Login(userName, userPassword);
                //登录成功进入首页
              /*  Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                startActivity(intent);*/
            }
        });

    }


    // 用户登录
    protected void Login(String user, String password) {
        String md5password = MD5.md5("123456");

        Paramers paramersobj = new Paramers("shop", md5password);
        String paramerStr = JSON.toJSONString(paramersobj);

        String signature = MD5.md5(paramerStr + UserParams.SALT);
        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/Index/Login");
        // 请求参数
        params.addBodyParameter("uname", "shop");
        params.addBodyParameter("password", md5password);
        params.addBodyParameter("signature", signature);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result: ", result);
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                User user = new Gson().fromJson(result, User.class);
                if (user.getStatus() == 1) {
                    SharedPreferencesUtil.saveData(LoginActivity.this, "shopid", user.getData().getShop_id());
                    SharedPreferencesUtil.saveData(LoginActivity.this, "token", user.getData().getToken());
                  /*  editor.putString("tokent", user.getData().getToken());
                    editor.putInt("shopid", user.getData().getShop_id());
                    editor.commit();*/
                } else {
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "网络访问错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "canclled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                /*String token = preferences.getString("tokent","");*/
                Intent intent = new Intent(LoginActivity.this, CarouselImg.class);
                startActivity(intent);
               /* Toast.makeText(x.app(), token, Toast.LENGTH_LONG).show();*/
            }
        });
    }
}
