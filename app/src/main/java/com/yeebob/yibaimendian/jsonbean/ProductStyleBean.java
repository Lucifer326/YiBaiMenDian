package com.yeebob.yibaimendian.jsonbean;

/**
 * 商品型号
 * Created by WGl on 2016-1-27.
 */
public class ProductStyleBean {

    /**
     * product_id : 8160
     * spec_det_id1 : 2
     * sale_price : 666.00
     * instock : 100
     * spec_name : s
     */

    private String product_id;
    private String spec_det_id1;
    private String sale_price;
    private String instock;
    private String spec_name;

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setSpec_det_id1(String spec_det_id1) {
        this.spec_det_id1 = spec_det_id1;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public void setInstock(String instock) {
        this.instock = instock;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getSpec_det_id1() {
        return spec_det_id1;
    }

    public String getSale_price() {
        return sale_price;
    }

    public String getInstock() {
        return instock;
    }

    public String getSpec_name() {
        return spec_name;
    }
}
