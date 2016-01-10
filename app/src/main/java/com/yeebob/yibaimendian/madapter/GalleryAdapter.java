package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yeebob.yibaimendian.R;

import java.util.List;

/**
 * 商品详情图片适配器 GalleryAdapter
 * Created by WGl on 2016-1-8.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private LayoutInflater mInflate;
    private List<Integer> mDatas;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    // 构造方法赋初始值
    public GalleryAdapter(Context context, List<Integer> datas) {
        this.mDatas = datas;
        this.mInflate = LayoutInflater.from(context);
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflate.inflate(R.layout.activity_detail_gallery_item, parent, false);

        return new GalleryViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, final int position) {
        holder.mImageView.setImageResource(mDatas.get(position));
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

/*
class GalleryViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public GalleryViewHolder(View itemView) {
        super(itemView);
    }
}
*/
