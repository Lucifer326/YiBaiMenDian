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
     * data : {"product_id":"8160","product_name":"你到网卡配置目","catimg":"http://iwshop.yeebob.com/uploads/product_hpic/1453282677569f5575a4679.jpg","product_images":["http://iwshop.yeebob.com/uploads/product_hpic/1452244974568f7fee33b9b.jpg","http://iwshop.yeebob.com/uploads/product_hpic/1453282693569f55853be9e.jpg","http://iwshop.yeebob.com/uploads/product_hpic/1453282711569f5597c82f0.jpg"],"twc_image":"http://iwshop.yeebob.com/?/Index/getqrcode&str=aHR0cDovL3d3dy5iYWlkdS5jb20=","twc_title":"http://iwshop.yeebob.com/?/vProduct/view/id=8160","attr":[{"product_id":"8160","spec_det_id1":"2","sale_price":"666.00","instock":"100","spec_name":"s"},{"product_id":"8160","spec_det_id1":"3","sale_price":"667.00","instock":"200","spec_name":"m"},{"product_id":"8160","spec_det_id1":"4","sale_price":"668.00","instock":"300","spec_name":"l"},{"product_id":"8160","spec_det_id1":"6","sale_price":"669.00","instock":"400","spec_name":"xxl"}]}
     */

    private int status;
    private String message;
    /**
     * product_id : 8160
     * product_name : 你到网卡配置目
     * catimg : http://iwshop.yeebob.com/uploads/product_hpic/1453282677569f5575a4679.jpg
     * product_images : ["http://iwshop.yeebob.com/uploads/product_hpic/1452244974568f7fee33b9b.jpg","http://iwshop.yeebob.com/uploads/product_hpic/1453282693569f55853be9e.jpg","http://iwshop.yeebob.com/uploads/product_hpic/1453282711569f5597c82f0.jpg"]
     * twc_image : http://iwshop.yeebob.com/?/Index/getqrcode&str=aHR0cDovL3d3dy5iYWlkdS5jb20=
     * twc_title : http://iwshop.yeebob.com/?/vProduct/view/id=8160
     * attr : [{"product_id":"8160","spec_det_id1":"2","sale_price":"666.00","instock":"100","spec_name":"s"},{"product_id":"8160","spec_det_id1":"3","sale_price":"667.00","instock":"200","spec_name":"m"},{"product_id":"8160","spec_det_id1":"4","sale_price":"668.00","instock":"300","spec_name":"l"},{"product_id":"8160","spec_det_id1":"6","sale_price":"669.00","instock":"400","spec_name":"xxl"}]
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
         * product_id : 8160
         * spec_det_id1 : 2
         * sale_price : 666.00
         * instock : 100
         * spec_name : s
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
    }
}
