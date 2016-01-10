package com.yeebob.yibaimendian.clickinterface;

import android.view.View;

/**
 * long click
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.clickinterface
 */
public interface OnItemLongClickListener<T> {

    void onLongClick(View view, T item);
}
