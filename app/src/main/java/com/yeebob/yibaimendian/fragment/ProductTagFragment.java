package com.yeebob.yibaimendian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.CommonJsonList;
import com.yeebob.yibaimendian.jsonbean.TagBean;
import com.yeebob.yibaimendian.madapter.TagCateAdapter;
import com.yeebob.yibaimendian.mainactivity.ProductsListActivity;
import com.yeebob.yibaimendian.utils.SharedPreferencesUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ProductTagFragment extends Fragment {

    private List<TagBean> mDatas = new ArrayList<>();
    private TagCateAdapter mTagAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    private ImageButton mImagebtnLeft;
    private ImageButton mImagebtnRight;
    private boolean move = false;

    private int mIndex = 0; //可见item postion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_tag, container, false);

        mImagebtnLeft = (ImageButton) view.findViewById(R.id.tag_arrow_left);
        mImagebtnRight = (ImageButton) view.findViewById(R.id.tag_arrow_right);

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.product_tag_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mTagAdapter = new TagCateAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mTagAdapter);

        // item点击事件监听
        mTagAdapter.setOnItemClickLitener(new TagCateAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
               /* Toast.makeText(getActivity(), mDatas.get(position).getTagName() + " click",
                        Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(getActivity(), ProductsListActivity.class);
//                intent.putExtra("beanid", 5);
                startActivity(intent);  // 打开自定义推广商品列表
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        //arrow button 事件
        mImagebtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
              /*  Log.v("Item", String.valueOf(firstItem));
                Log.v("Item", String.valueOf(lastItem));*/
                if (lastItem + 1 < mDatas.size()) {
                    if ((mDatas.size() - lastItem - 1) > 5) {
                        mRecyclerView.smoothScrollToPosition(lastItem + 5);
                        mImagebtnLeft.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.smoothScrollToPosition(lastItem + (mDatas.size() - lastItem - 1) + lastItem);
                        mImagebtnRight.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        mImagebtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                Log.v("Item", String.valueOf(firstItem));
                Log.v("Item", String.valueOf(lastItem));
                if (firstItem > 0) {
                    if (firstItem > 5) {
                        mRecyclerView.smoothScrollToPosition(firstItem - 4);
                        mImagebtnRight.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.smoothScrollToPosition(0);
                        mImagebtnLeft.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        //滚动监听
        mRecyclerView.addOnScrollListener(new RecyclerViewListener());
        getTagDates();
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取自定义推广商品
        //getTagDates();
    }

    private void getTagDates() {
       /* mDatas = new ArrayList<>();

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

        mDatas.add(tagBean8);*/
      /*  for (int i = 0; i < 10; i++) {

            TagBean tagBean = new TagBean();
            tagBean.setTagId(i);
            tagBean.setTagName("tagname: " + i);
            tagBean.setTagImage(R.drawable.c);

            mDatas.add(tagBean);
        }*/
        Integer shopId = (Integer) SharedPreferencesUtil.getData(x.app(), "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(x.app(), "token", "");

        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/Banner/tag_list");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //  Log.v("result:tag_list", result);
//                CommonJsonList<TagBean> resultObj = CommonJsonList.fromJson(result, TagBean.class);
                CommonJsonList resultObj = CommonJsonList.fromJson(result, TagBean.class);
                Log.v("bean", resultObj.toString());
                mDatas.clear();
                mDatas.addAll(resultObj.getData());
                mTagAdapter.notifyDataSetChanged();
                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (mDatas.size() > lastItem + 1) {
                    mImagebtnRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == recyclerView.SCROLL_STATE_IDLE) {
                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (firstItem > 0) {
                    mImagebtnLeft.setVisibility(View.VISIBLE);
                }else{
                    mImagebtnLeft.setVisibility(View.INVISIBLE);
                }
                if (lastItem + 1 < mDatas.size()){
                    mImagebtnRight.setVisibility(View.VISIBLE);
                }else{
                    mImagebtnRight.setVisibility(View.INVISIBLE);
                }
            }
        }

        public RecyclerViewListener() {
            super();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
