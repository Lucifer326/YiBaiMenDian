package com.yeebob.yibaimendian.utils;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CommonUtil {



    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
            // /data/data/
        }
    }

    public static boolean checkNetState(Context context) {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }

    public static void showToask(Context context, String tip) {
        if (context != null) {
            if (tip != null) {
                Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static DisplayImageOptions getOptions() {
        return new DisplayImageOptions.Builder().cacheOnDisk(true)
                .cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)

                        // .displayer(new RoundedBitmapDisplayer(50))
                .build();
    }

    public static DisplayImageOptions getOptions(int circular) {
        return new DisplayImageOptions.Builder().cacheOnDisk(true)
                .cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(circular)).build();
    }

    @SuppressLint("NewApi")
    public static ActionBar.LayoutParams getLayoutParams() {
        return new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    /**
     * @param data
     * @return 设定文件 boolean 返回类型
     * @throws
     * @Title: validEmptyAndNull
     * @Description: TODO(验证字符串是否为空或者null)
     */
    public static boolean validEmptyAndNull(String data) {
        if (data == null || data.trim().equals("")) {
            return false;
        }
        return true;
    }

    public static boolean validEmailFormat(String data) {
        if (data == null || data.trim().equals("")) {
            return false;
        }
        if (!data.matches("[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+")) {
            return false;
        }
        return true;
    }

    /**
     * @param data
     * @param length
     * @return
     * @throws
     * @Title: validStringLength
     * @Description: TODO()
     */
    public static boolean validStringLength(String data, int length) {
        if (!validEmptyAndNull(data)) {
            return false;
        } else {
            return data.length() == length;
        }
    }

    public static boolean validStringLength(String data, int min, int max) {
        if (!validEmptyAndNull(data)) {
            return false;
        } else {
            return data.length() <= max && data.length() >= min;
        }
    }

    //创建图片倒影效果
    public static Bitmap createReflectedImage(Bitmap originalImage) {

        final int reflectionGap = 0;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        //实现图片反转
        //matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 5, width, height / 5, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 5), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff,
                Shader.TileMode.MIRROR);

        paint.setShader(shader);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    //图片圆角处理
    public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
        Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstbmp);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return dstbmp;
    }

}
