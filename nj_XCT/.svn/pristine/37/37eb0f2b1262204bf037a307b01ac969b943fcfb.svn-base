package com.xguanjia.hx.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 授权管理
 * @author ytg
 * @date 2012-09-12
 */
public class AuthorizeManager {
	
	private Context context;
	
	public AuthorizeManager(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 标记授权结果
	 * @param date 授权日期
	 * @param auth 授权结果｛true 已授权,false 未授权｝
	 */
	public void signAuth(String date,boolean auth){
		SharedPreferences sf = context.getSharedPreferences("auth_result", Context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString("date", date);
		editor.putBoolean("auth", auth);		
		editor.commit();		
	}
	
	/**
	 * 判断当天是否授权
	 * @return auth
	 */
	public boolean isAuth(){
		boolean flag = false;
		SharedPreferences sf = context.getSharedPreferences("auth_result", Context.MODE_PRIVATE);
		String date = sf.getString("date", "");	
		boolean auth = sf.getBoolean("auth", false);
		if(getCurrentDate().compareTo(date) > 0){
			flag = false;
		}else{
			flag = true;
		}
		if(auth){
			flag = flag&&true;
		}else{
			flag = false;
		}
		return flag;
	}	
	
	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String getCurrentDate(){		
		Calendar calendar = Calendar.getInstance();
		String strCurrentDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return strCurrentDate;
	}
}
