package com.yeebob.yibaimendian.clickinterface;

import android.view.View;

/**
 * click interface
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.clickinterface
 */
public interface OnItemClickListener<T> {

    void onClick(View view, T item);
}
