package com.yeebob.yibaimendian.jsonbean;

import java.util.List;

/**
 * 推荐商品列表
 * Created by WGl on 2016-1-14.
 */
public class TagListBean {

    /**
     * status : 1
     * message : success
     * data : [{"tag_id":"1","tag_name":"天天特价","tag_href":"http://www.baidu.com","tag_image":"http://iwshop.yeebob.com/uploads/tag/1.jpg"}]
     */

    private int status;
    private String message;
    /**
     * tag_id : 1
     * tag_name : 天天特价
     * tag_href : http://www.baidu.com
     * tag_image : http://iwshop.yeebob.com/uploads/tag/1.jpg
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
        private String tag_id;
        private String tag_name;
        private String tag_href;
        private String tag_image;

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public void setTag_href(String tag_href) {
            this.tag_href = tag_href;
        }

        public void setTag_image(String tag_image) {
            this.tag_image = tag_image;
        }

        public String getTag_id() {
            return tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public String getTag_href() {
            return tag_href;
        }

        public String getTag_image() {
            return tag_image;
        }
    }
}
