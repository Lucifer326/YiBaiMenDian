package com.yeebob.yibaimendian.jsonbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 自定义推广分类
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.jsonbean
 */
@Table(name = "tagbean")
public class TagBean {

    @Column(name = "tag_id", isId = true,autoGen = false)
    private int tag_id;
    @Column(name = "tag_name")
    private String tag_name;
    @Column(name = "tag_href")
    private String tag_href;
    @Column(name = "tag_image")
    private String tag_image;

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_href() {
        return tag_href;
    }

    public void setTag_href(String tag_href) {
        this.tag_href = tag_href;
    }

    public String getTag_image() {
        return tag_image;
    }

    public void setTag_image(String tag_image) {
        this.tag_image = tag_image;
    }
}
