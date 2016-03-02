package com.xguanjia.hx.openfire.until;

import com.google.gson.Gson;

public class DataUntil {

	/**
	 * 把json数据转换成Bean
	 * **/
	public static <T> T getBeanFromJson(Class<T> cl, String json) {
		if (null == json || "".equals(json)) {
			return null;
		}
		T t = null;
		Gson gson = new Gson();
		t = gson.fromJson(json, cl);
		return t;
	}

}
