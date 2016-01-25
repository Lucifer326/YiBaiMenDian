package com.yeebob.yibaimendian.madapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yeebob.yibaimendian.R;

import java.util.List;

/**
 * Created by wgl on 2016/1/23.
 * com.yeebob.yibaimendian.madapter
 */
public class LoopAdapter extends LoopPagerAdapter {
    /*   private int[] imgs = {
               R.drawable.banner1,
               R.drawable.banner2,
               R.drawable.banner3,
               R.drawable.banner4,
       };*/
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<String> mUrls;

    public LoopAdapter(RollPagerView viewPager,Context context, List<String> urls) {
        super(viewPager);
        this.mInflater = LayoutInflater.from(context);
        this.mUrls = urls;
        this.options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnFail(R.drawable.banner1) //下载失败后加载的图片
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        //view.setImageResource(imgs[position]);
        ImageLoader.getInstance().displayImage(mUrls.get(position), view, options);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return mUrls.size();
    }

}

