package com.xguanjia.hx.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class Tools {
	/**
	 * 格式化文件大小
	 * @param filesize
	 * @return string
	 */
	public static String FileSizeForm(long filesize){
		DecimalFormat df=new DecimalFormat("#.00");
		String result="";
		if(filesize<1024L){result=df.format((double)filesize)+"B";}
		else if(filesize<1048576L){result=df.format((double)filesize/1024L)+"KB";}
		else if(filesize<1073741824L){result=df.format((double)filesize/1048576L)+"M";}
		
		return result;
	}
	
	/**
	 * 检查sd卡是否可用
	 * 
	 * @return boolean
	 */
	public static  boolean isSdCardAvailable() {
		boolean sdCardAvailable = false;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			sdCardAvailable = true;
		}
		return sdCardAvailable;
	}
	
	/**
	 *  得到显示的时间
	 * @param time
	 * @return
	 */
	public static String getShowTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(new Date());
		String showTime = "";
		if (currentDate.equals(time.substring(0, 10))) {
			showTime = time.substring(11, 16);
		} else if (currentDate.substring(0, 7).equals(time.substring(0, 7))) {
			showTime = time.substring(8, 10) + "日";
		} else if (currentDate.substring(0, 4).equals(time.substring(0, 4))) {
			showTime = time.substring(5, 7) + "月" + time.substring(8, 10) + "日";
		} else {
			showTime = time.substring(0, 4) + "年" + time.substring(5, 7) + "月" + time.substring(8, 10) + "日";
		}
		return showTime;
	}

	
}
