package com.yeebob.yibaimendian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
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
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    private ImageButton mImagebtnLeft;
    private ImageButton mImagebtnRight;


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
        mTagAdapter = new TagCateAdapter(getActivity(), mDatas, mImageLoader);
        mRecyclerView.setAdapter(mTagAdapter);

        // item点击事件监听
        mTagAdapter.setOnItemClickLitener(new TagCateAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                /*Toast.makeText(x.app(), mDatas.get(position).getTag_id() + " click",
                        Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(getActivity(), ProductsListActivity.class);
                intent.putExtra("tag_id", mDatas.get(position).getTag_id());
                startActivity(intent);  // 打开自定义推广商品列表*/
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        //arrow button 事件
        mImagebtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
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
        Integer shopId = (Integer) SharedPreferencesUtil.getData(x.app(), "shopid", 0);
        String token = (String) SharedPreferencesUtil.getData(x.app(), "token", "");

        RequestParams params = new RequestParams("http://iwshop.yeebob.com/?/Banner/tag_list");
        params.addBodyParameter("shop_id", String.valueOf(shopId));
        params.addBodyParameter("token", token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CommonJsonList resultObj = CommonJsonList.fromJson(result, TagBean.class);
                mDatas.clear();
                mDatas.addAll(resultObj.getData());
                mTagAdapter.notifyDataSetChanged();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (mDatas.size() > lastItem + 1) {
                    mImagebtnRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), " 未知错误...", Toast.LENGTH_LONG).show();
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
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:    //正在滑动
                    mImageLoader.resume();  //停止加载图片
                    int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                    int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                    if (firstItem > 0) {
                        mImagebtnLeft.setVisibility(View.VISIBLE);
                    } else {
                        mImagebtnLeft.setVisibility(View.INVISIBLE);
                    }
                    if (lastItem + 1 < mDatas.size()) {
                        mImagebtnRight.setVisibility(View.VISIBLE);
                    } else {
                        mImagebtnRight.setVisibility(View.INVISIBLE);
                    }
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING: //滑动停止
                    mImageLoader.resume();// 恢复加载图片
                    break;
                default:
                    break;

            }
           /* if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (firstItem > 0) {
                    mImagebtnLeft.setVisibility(View.VISIBLE);
                } else {
                    mImagebtnLeft.setVisibility(View.INVISIBLE);
                }
                if (lastItem + 1 < mDatas.size()) {
                    mImagebtnRight.setVisibility(View.VISIBLE);
                } else {
                    mImagebtnRight.setVisibility(View.INVISIBLE);
                }
            }*/
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
