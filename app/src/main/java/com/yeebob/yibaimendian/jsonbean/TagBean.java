package com.yeebob.yibaimendian.jsonbean;

/**
 * 自定义推广分类
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.jsonbean
 */
public class TagBean {

    private int tagId;
    private String tagName;
    private String tagHref;
    private int tagImage;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagHref() {
        return tagHref;
    }

    public void setTagHref(String tagHref) {
        this.tagHref = tagHref;
    }

    public int getTagImage() {
        return tagImage;
    }

    public void setTagImage(int tagImage) {
        this.tagImage = tagImage;
    }
}
