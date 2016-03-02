package com.xguanjia.hx.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

/**
 * 网络管理
 * @author ytg
 * @date 2012-08-27
 */
public class NetworkManager {
	public static final String TAG = "NetworkManager";
	public static final int ACTION_REQUEST_CODE_WIFI = 1314;
	/**
	 * 判断当前网络是否WIFI
	 * @return{true 是WIFI,flase 不是否WIFI}
	 * @return
	 */
	public static boolean currentNetworkIsWifi(Context context){
		ConnectivityManager mag = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);    
		NetworkInfo info = mag.getActiveNetworkInfo();    
		if(info == null){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	//判断网络状态
	public static boolean isNetworkConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	         if (mNetworkInfo != null) {  
	             return mNetworkInfo.isAvailable();  
	         }  
	     }  
	     return false;  
	 }
	
	/**
	 * 设置无线网络
	 * @param activity
	 */
	public static void setNetwork(Activity activity){		
		if(activity == null){
			Log.i(TAG, "activity is null");
		}
		activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), ACTION_REQUEST_CODE_WIFI);
		//activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), ACTION_REQUEST_CODE_WIFI);		
	}
	
	/**
	 * 设置界面
	 * @param activity
	 */
	public static void setNetSetting(Activity activity){		
		if(activity == null){
			Log.i(TAG, "activity is null");
		}
//		activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), ACTION_REQUEST_CODE_WIFI);
		activity.startActivityForResult(new Intent(Settings.ACTION_SETTINGS), ACTION_REQUEST_CODE_WIFI);		
	}
}
