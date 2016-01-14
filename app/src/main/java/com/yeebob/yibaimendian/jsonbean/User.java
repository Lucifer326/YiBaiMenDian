package com.yeebob.yibaimendian.jsonbean;

/**
 * 登录用户
 * Created by WGL on 2016-1-14.
 */
public class User {

    /**
     * status : 1
     * message : success
     * data : {"uid":1,"name":"word","href":"http://jms.yeebob.com/wangli.com","shop_id":1,"token":"sadhaskhtjyl47fdmd"}
     */

    private int status;
    private String message;
    /**
     * uid : 1
     * name : word
     * href : http://jms.yeebob.com/wangli.com
     * shop_id : 1
     * token : sadhaskhtjyl47fdmd
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
        private int uid;
        private String name;
        private String href;
        private int shop_id;
        private String token;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUid() {
            return uid;
        }

        public String getName() {
            return name;
        }

        public String getHref() {
            return href;
        }

        public int getShop_id() {
            return shop_id;
        }

        public String getToken() {
            return token;
        }
    }
}
