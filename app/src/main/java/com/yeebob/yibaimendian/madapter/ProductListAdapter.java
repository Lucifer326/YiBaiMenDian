package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.ProductListBean;

import java.util.List;

/**
 * 商品自定义列表详情适配器 ProductListAdapter
 * Created by WGL on 2016-1-6.
 * */


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private LayoutInflater mInflate;
    private List<ProductListBean> mDatas;
    private DisplayImageOptions options;
    private OnItemClickLitener mOnItemClickLitener;


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    // 点击事件接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // 构造方法赋初始值
    public ProductListAdapter(Context context, List<ProductListBean> datas) {
        this.mDatas = datas;
        this.mInflate = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.brand_2)
                .showImageForEmptyUri(R.drawable.brand_2)
                .displayer(new RoundedBitmapDisplayer(15)) //圆角处理
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_tag_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       // holder.mImageView.setImageResource(mDatas.get(position).getCatImg());
        ImageLoader.getInstance().displayImage(mDatas.get(position).getCatimg(),holder.mImageView,options);
        holder.mDescribe.setText(mDatas.get(position).getProduct_name());
        holder.mPrice.setText("￥ "+mDatas.get(position).getSell_price());
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mDescribe;
        public TextView mPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.id_tag_image);
            mDescribe = (TextView) itemView.findViewById(R.id.id_tag_description);
            mPrice = (TextView) itemView.findViewById(R.id.id_price);
        }
    }
}

