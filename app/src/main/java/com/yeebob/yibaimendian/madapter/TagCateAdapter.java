package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.TagBean;
import com.yeebob.yibaimendian.utils.CommonUtil;

import java.util.List;

/**
 * 自定义推广商品列表适配器 CategoryAdapter
 * Created by WGL on 2016-1-6.
 */
public class TagCateAdapter extends RecyclerView.Adapter<TagCateAdapter.MyViewHolder> {

    private LayoutInflater mInflate;
    private List<TagBean> mDatas;
    private OnItemClickLitener mOnItemClickLitener;
    protected DisplayImageOptions options;
    private ImageLoader mImageLoader;


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    // 点击事件接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // 构造方法赋初始值
    public TagCateAdapter(Context context, List<TagBean> datas,ImageLoader imageLoader) {
        this.mDatas = datas;
        this.mInflate = LayoutInflater.from(context);
        this.mImageLoader = imageLoader;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.productlisterror)
                .showImageOnFail(R.drawable.productlisterror)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_list_tag, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
      /*  holder.mImageView.setImageResource(mDatas.get(position).getTagImage());*/
        mImageLoader.displayImage(mDatas.get(position).getTag_image(), holder.mImageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                holder.progressBar.setProgress(0);
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
                Bitmap bitmap = CommonUtil.getRCB(loadedImage, 12);
                holder.mImageView.setImageBitmap(CommonUtil.createReflectedImage(bitmap));
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        // Log.v("image", mDatas.get(position).getTag_image());
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
        public ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.id_tag_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.tag_progress_loding);
        }
    }

}

