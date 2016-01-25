package com.yeebob.yibaimendian.jsonbean;

import java.util.List;

/**
 * 待机广告实体类
 * Created by wgl on 2016/1/21.
 * com.yeebob.yibaimendian.jsonbean
 */
public class CarouselBean {

    /**
     * advert_id : 广告id
     * advert_name : 广告名称
     * advert_type : 广告类型标记：1图片，2视频
     * advert_resource :  广告资源路径
     */

    private String advert_id;
    private String advert_name;
    private String advert_type;
    private List<String> advert_resource;

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public void setAdvert_name(String advert_name) {
        this.advert_name = advert_name;
    }

    public void setAdvert_type(String advert_type) {
        this.advert_type = advert_type;
    }

    public void setAdvert_resource(List<String> advert_resource) {
        this.advert_resource = advert_resource;
    }

    public String getAdvert_id() {
        return advert_id;
    }

    public String getAdvert_name() {
        return advert_name;
    }

    public String getAdvert_type() {
        return advert_type;
    }

    public List<String> getAdvert_resource() {
        return advert_resource;
    }
}
