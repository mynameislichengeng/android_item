package com.haoqee.chat.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Version implements Serializable{

	private static final long serialVersionUID = 19874543545465L;
	/**
	 * "hasNewVersion": 1,
        "currVersion": "1.3",
        "url": "112.124.14.169/qichuang/Data/app/app_menu.apk",
        "description": "1.不错\r\n2.要得\r\n3.可以\r\n4.恩。",
        "updateType": "0"

	 */
	public String version;
	public String downloadUrl;
	public String description;
	public boolean mHasNewVersion = false;
	public int updateType;
	public long updateTime = 0;
	
	public Version(){}
	
	public Version(JSONObject json){
		try {
			
			if(!json.isNull("currVersion")){
				version = json.getString("currVersion");
			}
			
			if(!json.isNull("url")){
				downloadUrl = json.getString("url");
			}
			
			if(!json.isNull("description")){
				description = json.getString("description");
			}
			
			if(!json.isNull("hasNewVersion")){
				int hasNewVersion = json.getInt("hasNewVersion");
				mHasNewVersion =  hasNewVersion == 1 ? true : false;
			}
			
			if(!json.isNull("updateType")){
				updateType = json.getInt("updateType");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
