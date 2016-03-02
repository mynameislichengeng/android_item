package com.xguanjia.hx.filecabinet.adaptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SelectedUserID {
	public static Map<String,String> map = new HashMap<String, String>();
	
	public static void selectedUser(String userId){
		if(map == null){
			map = new HashMap<String,String>();
		}
		map.put(userId, userId);
	}
	
	public static void unSelectedUser(String userId){
		if(map == null){
			map = new HashMap<String,String>();
		}
		map.remove(userId);
	}
	
	public static String getUserId(String id){
		if(map == null){
			map = new HashMap<String,String>();
		}
		return map.get(id);
	}
	/**
	 * 获取人员ID号列表
	 * @return
	 */
	public static Collection<String> getUserIds(){	
		return map.values();
	}
}
