package com.yeebob.yibaimendian.jsonbean;

/**
 * 品牌分类
 * Created by WGL on 2016-1-28.
 */
public class TagBrandBean {

    /**
     * id : 8
     * brand_name : zara
     * brand_img2 : 1451917262568a7fce301fc.png
     * brand_img : http://iwshop.yeebob.com/uploads/brands/1451917262568a7fce301fc.png
     */

    private String id;
    private String brand_name;
    private String brand_img2;
    private String brand_img;

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public void setBrand_img2(String brand_img2) {
        this.brand_img2 = brand_img2;
    }

    public void setBrand_img(String brand_img) {
        this.brand_img = brand_img;
    }

    public String getId() {
        return id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getBrand_img2() {
        return brand_img2;
    }

    public String getBrand_img() {
        return brand_img;
    }
}
