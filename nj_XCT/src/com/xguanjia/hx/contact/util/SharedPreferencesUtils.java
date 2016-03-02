package com.xguanjia.hx.contact.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
	private static final String PREFERENCENAME = "contact_info";
	
	/**
	 * 
	 * @param 保存更新通讯录异常事情
	 * @param 2014-11-27
	 */
	public static void setFlag(Context context, String flag) {
		SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
		Editor editor = mSharedPreferences.edit();
		editor.putString("isfocus", flag);
		editor.commit();
	}
	
	/**
	 * 
	 * @param 获取更新通讯录异常情况
	 * @param 2014-11-27
	 */
	public static String getFlag(Context context) {
		SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
		return mSharedPreferences.getString("isfocus", "");
	}

	/**
	 * 保存用户名
	 */
	public static void saveUserName(Context context, String string) {
		try {
			SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
			Editor editor = mSharedPreferences.edit();
			editor.putString("user_name", string);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户名
	 */
	public static String getUserName(Context context) {
		try {
			SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
			return mSharedPreferences.getString("user_name", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 保存密码
	 */
	public static void savePasswrod(Context context, String string) {
		try {
			SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
			Editor editor = mSharedPreferences.edit();
			editor.putString("passwd", string);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取密码
	 */
	public static String getPassword(Context context) {
		try {
			SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE);
			return mSharedPreferences.getString("passwd", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
