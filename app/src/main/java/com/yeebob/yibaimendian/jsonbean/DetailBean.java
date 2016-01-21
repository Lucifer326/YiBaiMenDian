package com.yeebob.yibaimendian.jsonbean;

import java.util.List;

/**
 * 商品详情实体 bean
 * Created by WGL on 2016-1-21.
 */
public class DetailBean {


    /**
     * status : 1
     * message : success
     * data : {"product_id":"8172","product_name":"测试商品0104","catimg":"http://iwshop.yeebob.com/uploads/product_hpic/1453260419569efe834ece6.jpg","product_images":["http://iwshop.yeebob.com/uploads/product_hpic/1452224140568f2e8caceb7.jpg","http://iwshop.yeebob.com/uploads/product_hpic/1453283379569f583375bbb.png","http://iwshop.yeebob.com/uploads/product_hpic/1453283383569f583759854.png","http://iwshop.yeebob.com/uploads/product_hpic/1453283386569f583a06b22.png","http://iwshop.yeebob.com/uploads/product_hpic/1453283389569f583d8ab32.png"],"twc_image":"http://iwshop.yeebob.com/?/index/getqrcode&str=aHR0cDovL3d3dy5iYWlkdS5jb20=","twc_title":"http://iwshop.yeebob.com/?/vProduct/view/id=8172","attr":[{"product_id":"1","spec_det_id1":1,"sale_price":1000,"instock":200},{"product_id":"1","spec_det_id1":2,"sale_price":1010,"instock":10}]}
     */

    private int status;
    private String message;
    /**
     * product_id : 8172
     * product_name : 测试商品0104
     * catimg : http://iwshop.yeebob.com/uploads/product_hpic/1453260419569efe834ece6.jpg
     * product_images : ["http://iwshop.yeebob.com/uploads/product_hpic/1452224140568f2e8caceb7.jpg","http://iwshop.yeebob.com/uploads/product_hpic/1453283379569f583375bbb.png","http://iwshop.yeebob.com/uploads/product_hpic/1453283383569f583759854.png","http://iwshop.yeebob.com/uploads/product_hpic/1453283386569f583a06b22.png","http://iwshop.yeebob.com/uploads/product_hpic/1453283389569f583d8ab32.png"]
     * twc_image : http://iwshop.yeebob.com/?/index/getqrcode&str=aHR0cDovL3d3dy5iYWlkdS5jb20=
     * twc_title : http://iwshop.yeebob.com/?/vProduct/view/id=8172
     * attr : [{"product_id":"1","spec_det_id1":1,"sale_price":1000,"instock":200},{"product_id":"1","spec_det_id1":2,"sale_price":1010,"instock":10}]
     */

    private DataEntity data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String product_id;
        private String product_name;
        private String catimg;
        private String twc_image;
        private String twc_title;
        private List<String> product_images;
        /**
         * product_id : 1
         * spec_det_id1 : 1
         * sale_price : 1000
         * instock : 200
         */

        private List<AttrEntity> attr;

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public void setCatimg(String catimg) {
            this.catimg = catimg;
        }

        public void setTwc_image(String twc_image) {
            this.twc_image = twc_image;
        }

        public void setTwc_title(String twc_title) {
            this.twc_title = twc_title;
        }

        public void setProduct_images(List<String> product_images) {
            this.product_images = product_images;
        }

        public void setAttr(List<AttrEntity> attr) {
            this.attr = attr;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getCatimg() {
            return catimg;
        }

        public String getTwc_image() {
            return twc_image;
        }

        public String getTwc_title() {
            return twc_title;
        }

        public List<String> getProduct_images() {
            return product_images;
        }

        public List<AttrEntity> getAttr() {
            return attr;
        }

        public static class AttrEntity {
            private String product_id;
            private int spec_det_id1;
            private int sale_price;
            private int instock;

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public void setSpec_det_id1(int spec_det_id1) {
                this.spec_det_id1 = spec_det_id1;
            }

            public void setSale_price(int sale_price) {
                this.sale_price = sale_price;
            }

            public void setInstock(int instock) {
                this.instock = instock;
            }

            public String getProduct_id() {
                return product_id;
            }

            public int getSpec_det_id1() {
                return spec_det_id1;
            }

            public int getSale_price() {
                return sale_price;
            }

            public int getInstock() {
                return instock;
            }
        }
    }
}
