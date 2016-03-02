package com.xguanjia.hx.common;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class DeviceUtil {

	/**
	 * 检测当前的服务是什么状态
	 * 
	 * @param context
	 * @param serviceName
	 * @return
	 */
	public static boolean checkServiceIsAlive(Context context, String serviceName) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceName.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
