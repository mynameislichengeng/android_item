package com.haoqee.chatsdk.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class TCUser implements Serializable{

	private static final long serialVersionUID = -11564546534654L;
	
	private String mUid = "";													//用户ID
	private String mName = "";													//用户名称
	private String mHead = "";													//用户头像
	private String mExtendStr = "";												//用户扩展JSON数据
	private HashMap<String, String> mExtendMap = new HashMap<String, String>();	//用户扩展Map
	
	public TCUser(){}
	
	public TCUser(JSONObject json){
		
		try {
			if(!json.isNull("uid")){
				mUid = json.getString("uid");
			}
			
			if(!json.isNull("name")){
				mName = json.getString("name");
			}
			
			if(!json.isNull("head")){
				mHead = json.getString("head");
			}
			
			if(!json.isNull("extend")){
				Object object = json.get("extend");
				if(object instanceof JSONObject){
					JSONObject extend = (JSONObject) object;
					if(extend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = extend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mExtendMap.put(key, extend.getString(key));
						}
						
						if(mExtendMap.size() != 0){
							mExtendStr = extend.toString();
						}
					}
				}
				
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置用户ID
	 * @param uid
	 */
	public void setUserID(String uid){
		this.mUid = uid;
	}
	
	/**
	 * 获取用户ID
	 * @return
	 */
	public String getUserID() {
		return mUid;
	}
	
	/**
	 * 设置用户扩展JSON数据
	 * @param extendStr
	 */
	public void setExtendStr(String extendStr){
		this.mExtendStr = extendStr;
		try {
			JSONObject extend = new JSONObject(extendStr);
			if(extend != null){
				@SuppressWarnings("unchecked")
				Iterator<String> keys = extend.keys();
				while(keys.hasNext()){
					String key = keys.next();
					mExtendMap.put(key, extend.getString(key));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取用户扩展JSON数据
	 * @return
	 */
	public String getExtendStr(){
		return mExtendStr;
	}
	
	/**
	 * 获取用户扩展MAP
	 * @return
	 */
	public HashMap<String, String> getExtendMap() {
		return mExtendMap;
	}
	
	/**
	 * 设置用户名称
	 * @param name					名称
	 */
	public void setName(String name){
		this.mName = name;
	}
	
	/**
	 * 设置用户头像
	 * @param head				头像
	 */
	public void setHead(String head){
		this.mHead = head;
	}
	
	/**
	 * 获取用户名称
	 * @return
	 */
	public String getName(){
		return mName;
	}
	
	/**
	 * 获取用户头像
	 * @return
	 */
	public String getHead(){
		return mHead;
	}
}
