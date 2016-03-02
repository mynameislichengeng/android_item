package com.xguanjia.hx.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.chinamobile.salary.R;

public class AppUtils {

	/**
	 * 点击选中，动画平移
	 * 
	 * @param view
	 * @param startX
	 * @param toX
	 * @param startY
	 * @param toY
	 */
	public static void SetImageSlide(View view, int startX, int toX, int startY, int toY) {
		TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
		anim.setDuration(100);
		anim.setFillAfter(true);
		view.startAnimation(anim);
	}
	
	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWidthPixels(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	
	public static int getHeighPixels(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	
	/*
	 * 为照片命名
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	//得到当前的日期
	public static String getToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	//得到当前的日期
		public static String getYear() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			String dateString = formatter.format(currentTime);
			return dateString;
		}

	/**
	 * 获取屏幕的宽度
	 * 
	 * @return
	 */
	public static int getScreenDisplayMetrics(Context mContext, int type) {
		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		// 宽度
		if (type == 0) {
			int screenWidth = display.getWidth();
			return screenWidth;
		} else if (type == 1) {
			// 高度
			int screenHeight = display.getHeight();
			return screenHeight;
		}
		return type;
	}

	/**
	 * 根据手机的分辨率从 dp的单位转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素)的单位转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 从路径中得到图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromPath(String path) {
		Bitmap bitmap = null;
		FileInputStream fs = null;
		BufferedInputStream bs = null;
		WeakReference<Bitmap> bitWeakRef = null;
		try {
			File file = new File(path);
			fs = new FileInputStream(file);
			bs = new BufferedInputStream(fs);
			bitmap = BitmapFactory.decodeStream(bs);
			bitWeakRef = new WeakReference<Bitmap>(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fs != null)
					fs.close();
				if (bs != null) {
					bs.close();
				}
			} catch (Exception e) {
			}
		}
		return bitWeakRef.get();
	}

	public static void storeImageToSD(Bitmap bitmap, String imagePath) {
		File imageFile = new File(Constants.SAVE_IMAGE_PATH + "HeadAvatar/", imagePath);
		try {
			imageFile.getParentFile().mkdirs();
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void showNotification(){
		
	}
	
	public static DisplayImageOptions options = null;
	public LayoutParams layoutParams;
	protected static ImageLoader mImageLoader = ImageLoader.getInstance();

	public static ImageLoader getImageLoader() {
		if (mImageLoader == null) {
			mImageLoader = ImageLoader.getInstance();
		}
		return mImageLoader;
	}

	public static DisplayImageOptions getDisplayImageOptions() {
		if (options == null) {
			options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory().cacheOnDisc()
					.displayer(new RoundedBitmapDisplayer(10)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		}
		return options;
	}

}
