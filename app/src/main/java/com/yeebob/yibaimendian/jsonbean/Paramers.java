package com.yeebob.yibaimendian.jsonbean;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * 登录参数json对象
 * Created by WGl on 2016-1-14.
 */

@JSONType(orders={"uname","password"})
public class Paramers {

    private String uname;
    private String password;

    public Paramers(String uname, String password) {
        this.uname = uname;
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
