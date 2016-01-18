package com.yeebob.yibaimendian.utils;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
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
	 * @Title: validEmptyAndNull
	 * @Description: TODO(验证字符串是否为空或者null)
	 * @param data
	 * @return 设定文件 boolean 返回类型
	 * @throws
	 */
	public static boolean validEmptyAndNull(String data) {
		if (data == null || data.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	public static boolean validEmailFormat(String data)
	{
		if (data == null || data.trim().equals("")) {
			return false;
		}
		if(!data.matches("[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+"))
		{
			return false; 
		}
		return true;
	}

	/**
	 * @Title: validStringLength
	 * @Description: TODO()
	 * @param data
	 * @param length
	 * @return
	 * @throws
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

}
