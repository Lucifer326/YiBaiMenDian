package com.yeebob.yibaimendian.jsonbean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 通用gson 泛型解析
 * Created by wgl on 2016/1/18.
 * com.yeebob.yibaimendian.jsonbean
 */
public class CommonJsonList<T> implements Serializable {
    /**
     * 是否成功
     * 1 成功 0 失败
     */
    private Integer status;
    /**
     * 是否成功
     */
    private String message;

    /**
     * 数据集合
     */
    private List<T> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static CommonJsonList fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonJsonList.class, clazz);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
