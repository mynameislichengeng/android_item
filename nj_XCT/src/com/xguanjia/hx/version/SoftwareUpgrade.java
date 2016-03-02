package com.xguanjia.hx.version;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * 软件升级
 * @author ytg
 * @date 2012-09-15
 */
public class SoftwareUpgrade {
	private static final String TAG = "SoftwareUpgrade";
	private Context context;
	public SoftwareUpgrade(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 设置最新的版本
	 */
	public void setCurrentVersion(String version){
		SharedPreferences sf = context.getSharedPreferences("soft_info", Context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString("version", version);	
		editor.commit();
	}
	
	/**
	 * 检查版本是否有更新
	 * @author ytg
	 * @date 2012-09-15
	 */
	public boolean isUpadte(String version){
		boolean flag = false;
		SharedPreferences sf = context.getSharedPreferences("soft_info", Context.MODE_PRIVATE);
		String v = sf.getString("version", "");	
		if(version.compareTo(v) > 0){
			try {
				upgrade(version);
				setCurrentVersion(version);
			} catch (Exception e) {				
				Log.e(TAG, e.getMessage(), e);
			}
			flag = true;
			
		}		
		return flag;
	}
	
	/**
	 * 升级软件后修改表结构
	 */
	public void upgrade(String version) throws Exception{
		Log.i(TAG, "The software is upgraded to the "+version+" version");
	}
}
