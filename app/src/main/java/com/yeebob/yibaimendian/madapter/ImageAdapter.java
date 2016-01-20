package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yeebob.yibaimendian.R;
import com.yeebob.yibaimendian.jsonbean.BannerBean;

import java.util.List;

/**
 * Created by wgl on 2016/1/11.
 * com.yeebob.yibaimendian.madapter
 */
public class ImageAdapter extends BaseAdapter {

    private int[] mPics;
    private List<BannerBean> mDatas;
    private DisplayImageOptions options;
    private LayoutInflater mInflater;

    public ImageAdapter(Context mContext, int[] mPics, List<BannerBean> datas) {
        this.mPics = mPics;
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(mContext);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(15)) //圆角处理
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return position % mDatas.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.banner_item, parent, false);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.baner_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageView.setImageResource(mPics[position % mPics.length]);
        ImageLoader.getInstance().displayImage(mDatas.get(position % mDatas.size()).getBanner_image(), viewHolder.mImageView, options);
        return convertView;
    }

    static class ViewHolder {
        ImageView mImageView;
    }
}
