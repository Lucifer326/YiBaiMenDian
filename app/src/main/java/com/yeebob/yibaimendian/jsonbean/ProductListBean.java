package com.yeebob.yibaimendian.jsonbean;

import java.io.Serializable;

/**
 * 商品列表信息
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.jsonbean
 */
public class ProductListBean implements Serializable{

    private int product_id;
    private String product_name;
    private String sell_price;
    private String catimg;

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getSell_price() {
        return sell_price;
    }

    public String getCatimg() {
        return catimg;
    }
}
