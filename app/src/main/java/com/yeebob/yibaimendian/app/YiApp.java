package com.yeebob.yibaimendian.app;

import android.app.Application;

import org.xutils.x;

/**
 * 翼百门店app YiApp
 * Created by WGL on 2015-12-29.
 */
public class YiApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils3.1.2 加载初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);   //是否打开debug调试
    }
}
