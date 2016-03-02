package com.haoqee.chatsdk.entity;

import java.io.Serializable;
import java.util.HashMap;

import org.json.JSONObject;

/**
 * 聊天文本消息
 *
 */
public class BaseMessageBody implements Serializable{


	private static final long serialVersionUID = -11545454554L;
	
	private String mExtendStr = "";								//扩展JSON字符串
	private HashMap<String, String> mExtendMap;					//扩展MAP

	public BaseMessageBody(){
		
	}
	
	/**
	 * 设置扩展JSON字符串
	 * @param extendStr
	 */
	public void setExtendStr(String extendStr){
		mExtendStr = extendStr;
	}
	
	/**
	 * 获取扩展JSON字符串
	 * @return
	 */
	public String getExtendStr(){
		return mExtendStr;
	}
	
	/**
	 * 获取扩展MAP
	 * @return
	 */
	public HashMap<String, String> getExtendMap(){
		return mExtendMap;
	}
	
	/**
	 * 设置扩展MAP
	 * @param map						扩展Map的值
	 */
	public void setExtendMap(HashMap<String, String> map){
		this.mExtendMap = map;
		
		if(mExtendMap != null && !mExtendMap.isEmpty()){
			mExtendStr = new JSONObject(mExtendMap).toString();
		}
	}
	
}
