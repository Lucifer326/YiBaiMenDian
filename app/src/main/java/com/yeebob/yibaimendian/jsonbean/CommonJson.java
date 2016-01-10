package com.yeebob.yibaimendian.jsonbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.jsonbean
 */
public class CommonJson<T> implements Serializable {

    /**
     * 判断返回值是否成功
     */
    private  int status;
    private  String message;
    /**
     * 数据
     */
    private List<T> mDatas;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }
}
