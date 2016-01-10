package com.yeebob.yibaimendian.jsonbean;

/**
 * 商品列表信息
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.jsonbean
 */
public class ProductList {

    private int productId;
    private String productName;
    private String productPrice;
    private int catImg;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getCatImg() {
        return catImg;
    }

    public void setCatImg(int catImg) {
        this.catImg = catImg;
    }
}
