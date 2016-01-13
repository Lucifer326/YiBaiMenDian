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
                intent.putExtra("beanid", 5);
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

        TagBean tagBean1 = new TagBean();
        tagBean1.setTagId(1);
        tagBean1.setTagName("本周上新");
        tagBean1.setTagImage(R.drawable.benzhou);

        mDatas.add(tagBean1);

        TagBean tagBean2 = new TagBean();
        tagBean2.setTagId(2);
        tagBean2.setTagName("天天低价");
        tagBean2.setTagImage(R.drawable.dijia);

        mDatas.add(tagBean2);

        TagBean tagBean3 = new TagBean();
        tagBean3.setTagId(3);
        tagBean3.setTagName("女神新衣");
        tagBean3.setTagImage(R.drawable.nvshen);

        mDatas.add(tagBean3);

        TagBean tagBean4 = new TagBean();
        tagBean4.setTagId(4);
        tagBean4.setTagName("天天特价");
        tagBean4.setTagImage(R.drawable.tejia);

        mDatas.add(tagBean4);

        TagBean tagBean5 = new TagBean();
        tagBean5.setTagId(5);
        tagBean5.setTagName("本周热卖");
        tagBean5.setTagImage(R.drawable.remai);

        mDatas.add(tagBean5);

        TagBean tagBean6 = new TagBean();
        tagBean6.setTagId(6);
        tagBean6.setTagName("本周上新");
        tagBean6.setTagImage(R.drawable.benzhou);

        mDatas.add(tagBean6);

        TagBean tagBean7 = new TagBean();
        tagBean7.setTagId(7);
        tagBean7.setTagName("天天低价");
        tagBean7.setTagImage(R.drawable.dijia);

        mDatas.add(tagBean7);

        TagBean tagBean8 = new TagBean();
        tagBean8.setTagId(8);
        tagBean8.setTagName("女神新衣");
        tagBean8.setTagImage(R.drawable.nvshen);

        mDatas.add(tagBean8);
      /*  for (int i = 0; i < 10; i++) {

            TagBean tagBean = new TagBean();
            tagBean.setTagId(i);
            tagBean.setTagName("tagname: " + i);
            tagBean.setTagImage(R.drawable.c);

            mDatas.add(tagBean);
        }*/
    }

}
