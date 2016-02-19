package com.yeebob.yibaimendian.mainactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.Paramers;
import com.yeebob.yibaimendian.jsonbean.User;
import com.yeebob.yibaimendian.jsonbean.UserParams;
import com.yeebob.yibaimendian.utils.HttpUtils;
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
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE); //隐藏底部虚拟按键

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户名密码
                userName = username.getText().toString();
                userPassword = password.getText().toString();
                //登录成功进入首页
                if (userName.length() <= 0 && userPassword.length() <= 0) {
                    Toast toast = Toast.makeText(x.app(), "用户名或密码不能为空", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (userName.length() <= 0) {
                    Toast toast = Toast.makeText(x.app(), "用户名不能为空", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (userPassword.length() <= 0) {
                    Toast toast = Toast.makeText(x.app(), "密码不能为空", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (userName != null && userPassword != null) {
                    Login(userName, userPassword); //登录
                }
            }
        });


    }


    // 用户登录
    private void Login(String user, String password) {
        // md5加密
        String md5password = MD5.md5(password);
        Paramers paramersobj = new Paramers(user, md5password);
        String paramerStr = JSON.toJSONString(paramersobj);
        String signature = MD5.md5(paramerStr + UserParams.SALT);
        /* 显示ProgressDialog */
//        pd = ProgressDialog.show(LoginActivity.this, "标题", "登录中，请稍后……");
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setMessage("登陆中，请等待...");
        pd.show();
        RequestParams params = new RequestParams(HttpUtils.BASEURL + "Index/Login");
        // 请求参数
        params.addBodyParameter("uname", user);
        params.addBodyParameter("password", md5password);
        params.addBodyParameter("signature", signature);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("result", result);
                User user = new Gson().fromJson(result, User.class);
                pd.dismiss();
                if (user.getStatus() == 1) {
                    SharedPreferencesUtil.saveData(LoginActivity.this, "shopid", user.getData().getShop_id());
                    SharedPreferencesUtil.saveData(LoginActivity.this, "token", user.getData().getToken());
                    SharedPreferencesUtil.saveData(LoginActivity.this, "href", user.getData().getHref());
                    startIndex(); // 打开首页
                } else {
                    Toast toast = Toast.makeText(x.app(), "用户名或密码错误", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                pd.dismiss();
                Toast.makeText(x.app(), "网络访问错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                pd.dismiss();
                Toast.makeText(x.app(), "canclled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void startIndex() {
        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
        startActivity(intent);
    }
}
