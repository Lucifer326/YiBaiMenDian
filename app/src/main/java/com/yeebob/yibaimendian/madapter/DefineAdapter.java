package com.yeebob.yibaimendian.madapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 自定义商品分类适配器
 * Created by wgl on 2016/1/10.
 * com.yeebob.yibaimendian.madapter
 */
public class DefineAdapter extends BaseRecyclerViewAdapter{


    /**
     * @param list the datas to attach the adapter
     */
    public DefineAdapter(List list) {
        super(list);
    }

    @Override
    protected void bindDataToItemView(SparseArrayViewHolder sparseArrayViewHolder, Object item) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    }
}
