package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by wgl on 2016/1/11.
 * com.yeebob.yibaimendian.madapter
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private int[] mPics;

    public ImageAdapter(Context mContext, int[] mPics) {
        this.mContext = mContext;
        this.mPics = mPics;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return position % mPics.length;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //建立圖片
        ImageView img = new ImageView(this.mContext);
        //將圖片置入img，置入的圖片為目前位置的圖片除以圖片總數取餘數，此餘數為圖片陣列的圖片位置
        img.setImageResource(mPics[position % mPics.length]);
        //保持圖片長寬比例
        img.setAdjustViewBounds(true);
        //縮放為置中
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //設置圖片長寬
        img.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.MATCH_PARENT));

        //回傳此建立的圖片
        return img;
    }
}
