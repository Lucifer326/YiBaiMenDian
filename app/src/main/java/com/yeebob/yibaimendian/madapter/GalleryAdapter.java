package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yeebob.yibaimendian.R;

import java.util.List;

/**
 * 商品详情图片适配器 GalleryAdapter
 * Created by WGl on 2016-1-8.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private LayoutInflater mInflate;
    private List<String> mDatas;
    private OnItemClickLitener mOnItemClickLitener;
    private DisplayImageOptions options;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    // 构造方法赋初始值
    public GalleryAdapter(Context context, List<String> datas) {
        this.mDatas = datas;
        this.mInflate = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.xq1)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.xq1)  //设置图片加载/解码过程中错误时候显示的图片
                .displayer(new RoundedBitmapDisplayer(10)) //圆角处理
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflate.inflate(R.layout.activity_detail_gallery_item, parent, false);

        return new GalleryViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, final int position) {
       // holder.mImageView.setImageResource(mDatas.get(position));
        ImageLoader.getInstance().displayImage(mDatas.get(position),holder.mImageView,options);
        Log.v("postion", mDatas.get(position));

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public GalleryViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.id_index_gallery_item_image);
        }
    }
}