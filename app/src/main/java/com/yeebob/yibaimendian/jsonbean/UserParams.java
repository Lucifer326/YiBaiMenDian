package com.yeebob.yibaimendian.jsonbean;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

/**
 * 登录请求参数
 * Created by WGL on 2016-1-14.
 */


@HttpRequest(
        host = "https://www.baidu.com",
        path = "s",
        builder = DefaultParamsBuilder.class/*可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/)
public class UserParams extends RequestParams {

    public final  static String  SALT = "yebob2016";
    public String uname;     // 传递的用户名
    public String password;  //传递的密码
    public String signature; // 加密参数

}
