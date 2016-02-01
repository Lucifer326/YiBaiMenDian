package com.yeebob.yibaimendian.app;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yeebob.yibaimendian.utils.CommonUtil;
import com.yeebob.yibaimendian.utils.FileHelper;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * 翼百门店app YiApp
 * Created by WGL on 2015-12-29.
 */
public class YiApp extends Application {
    //数据库初始化
    public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("yibaishop.db")
            .setDbVersion(1);

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils3.1.2 加载初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);   //是否打开debug调试


        init();
    }

    public void init() {
        initImageLoader(this);
    }

    public static void initImageLoader(Context context) {
        String path = CommonUtil.getRootFilePath() + "/community/image/";
        FileHelper.createDirectory(path);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheExtraOptions(480, 800)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(new File(path)))
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


}
