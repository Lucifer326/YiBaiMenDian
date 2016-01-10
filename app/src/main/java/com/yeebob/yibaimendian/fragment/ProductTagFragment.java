package com.yeebob.yibaimendian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.TagBean;
import com.yeebob.yibaimendian.madapter.TagCateAdapter;
import com.yeebob.yibaimendian.mainactivity.ProductsListActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductTagFragment extends Fragment {

    private List<TagBean> mDatas;

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
        TagCateAdapter mTagAdapter = new TagCateAdapter(getActivity(), mDatas);
        // set adpater recyclerview
        mRecyclerView.setAdapter(mTagAdapter);

        // 事件监听
        mTagAdapter.setOnItemClickLitener(new TagCateAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
               /* Toast.makeText(getActivity(), mDatas.get(position).getTagName() + " click",
                        Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(getActivity(), ProductsListActivity.class);
                startActivity(intent);  // 打开自定义推广商品列表
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
        // 获取自定义推广商品
        getTagDates();
    }

    private void getTagDates() {
        mDatas = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            TagBean tagBean = new TagBean();
            tagBean.setTagId(i);
            tagBean.setTagName("tagname: " + i);
            tagBean.setTagImage(R.drawable.c);

            mDatas.add(tagBean);
        }
    }

}
