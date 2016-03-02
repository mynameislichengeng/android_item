package com.xguanjia.hx.common.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

//用于尺寸的适配
public class MyDimensAdapter {
	public static int mwidth = 0;
	public static int height = 0;
	public static double mdensity = 0.0;
	public static int mdensityDpi = 0;
	private static double currentDen = 2.0;

	public static int setCurrentSize(int size) {
		if (mdensity == currentDen) {
			return size;
		} else {
			double scale = mdensity / currentDen;
			double msize = scale * size;
			return (int) msize;
		}
	}

	public static void getPhoneSecrren(Activity mContext) {
		DisplayMetrics metric = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);
		mwidth = metric.widthPixels; // 屏幕宽度（像素）
		height = metric.heightPixels; // 屏幕高度（像素）
		mdensity = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		mdensityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
	}

}
