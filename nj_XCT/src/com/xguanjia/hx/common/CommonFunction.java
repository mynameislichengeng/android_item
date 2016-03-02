package com.xguanjia.hx.common;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * 
 * @author huke
 * 
 * 说明：封装基本功能的公共方法
 * 
 * 日期：2013-06-17
 *
 */
public class CommonFunction {
	
	private static final String TAG = "CommonFunction";

	public CommonFunction() {
		// TODO Auto-generated constructor stub
	}
	
	//单例模式
	private static CommonFunction commonFunction = null;
	public static CommonFunction getInstance() {
		if(commonFunction == null) {
			commonFunction = new CommonFunction();
		}
		return commonFunction;
	}
	
	/**
	 * 获取系统当前时间
	 * @return  YYYY-MM-DD hh:mm:ss
	 */
	public String getCurrentDateTime() {
		
		String strCurrentDateTime = "";
		
		try {
			
			long lSystemTime = System.currentTimeMillis();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(lSystemTime);
			strCurrentDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d",
		               c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH),
		               c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
			
			return strCurrentDateTime;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "获取系统当前时间时抛出异常");
			return strCurrentDateTime;
		}
		
	}
	
	/**
	 * 判断指定号码是否为合法的手机号码
	 * @param strPhoneNum
	 * @return  true表示为合法的手机号码，false表示为非法的手机号码
	 */
	public boolean checkPhoneNum(String strPhoneNum) {
		//判断输入的号码是否有效
		String expression = "((^(13|15|18|14|12)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|" +
				"(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|" +
				"(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = strPhoneNum;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		Log.i("DeviceManagerActivity", "号码判断：" + matcher.matches());
        if(!matcher.matches())
        {       	
        	return false;
        } else {
        	return true;
        }
	}
	
	/**
	  * 验证邮箱地址是否正确
	  * @param email
	  * @return true表示邮箱地址正确，false表示邮箱地址不正确
	  */
	 public boolean checkEmail(String email){
		 
		  boolean flag = false;
		  
		  try{
			   String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			   Pattern regex = Pattern.compile(check);
			   Matcher matcher = regex.matcher(email);
			   flag = matcher.matches();
		  }catch(Exception e){
		   flag = false;
		  }
	  
		  return flag;
	 }

}
