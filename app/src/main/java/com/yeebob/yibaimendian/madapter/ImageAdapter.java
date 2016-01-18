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
        //建立图片
        ImageView img = new ImageView(this.mContext);
        //將图片置入img，置入的圖片為目前位置的圖片除以圖片總數取餘數，此餘數為圖片陣列的圖片位置
        img.setImageResource(mPics[position % mPics.length]);
        //保持图片长宽比例
        img.setAdjustViewBounds(true);
        //缩放为置中
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //设定图片长宽
        img.setLayoutParams(new Gallery.LayoutParams(1400, Gallery.LayoutParams.MATCH_PARENT));

        //回传建立的图片
        return img;
    }
}
