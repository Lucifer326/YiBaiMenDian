package com.yeebob.yibaimendian.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.madapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductTagFragment extends Fragment {

    private List<String> mDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // init fragment
        View view = inflater.inflate(R.layout.fragment_product_tag, container, false);
        //get a reference to recyclerView
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.product_tag_recyclerview);
        // set layoutManger 水平滚动
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        // init adapter
        CategoryAdapter mTagAdapter = new CategoryAdapter(getActivity(), mDatas);
        // set adpater recyclerview
        mRecyclerView.setAdapter(mTagAdapter);

        // 事件监听
        mTagAdapter.setOnItemClickLitener(new CategoryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化商品分类数据
        initTagDates();
    }

    private void initTagDates() {
        mDatas = new ArrayList<>();

        for (int i = 'A'; i < 'c'; i++) {
            mDatas.add("" + (char) i);
        }
    }

}
