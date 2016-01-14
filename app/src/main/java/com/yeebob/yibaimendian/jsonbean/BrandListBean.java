package com.yeebob.yibaimendian.jsonbean;

import java.util.List;

/**
 * 品牌列表
 * Created by WGL on 2016-1-14.
 */
public class BrandListBean {

    /**
     * status : 1
     * message : success
     * data : [{"banner_name":"asd","banner_href":"","banner_image":"http://iwshop.yeebob.com/uploads/banner/1452238271568f65bfb3051.jpg","banner_id":"2"}]
     */

    private int status;
    private String message;
    /**
     * banner_name : asd
     * banner_href :
     * banner_image : http://iwshop.yeebob.com/uploads/banner/1452238271568f65bfb3051.jpg
     * banner_id : 2
     */

    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String banner_name;
        private String banner_href;
        private String banner_image;
        private String banner_id;

        public void setBanner_name(String banner_name) {
            this.banner_name = banner_name;
        }

        public void setBanner_href(String banner_href) {
            this.banner_href = banner_href;
        }

        public void setBanner_image(String banner_image) {
            this.banner_image = banner_image;
        }

        public void setBanner_id(String banner_id) {
            this.banner_id = banner_id;
        }

        public String getBanner_name() {
            return banner_name;
        }

        public String getBanner_href() {
            return banner_href;
        }

        public String getBanner_image() {
            return banner_image;
        }

        public String getBanner_id() {
            return banner_id;
        }
    }
}
