package com.haoqee.chat.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPwd implements Serializable{
	private static final long serialVersionUID = -11789798764545L;
	
	public String mPassword = "";
	public AppState mState;
	
	public ModifyPwd(){}
	
	public ModifyPwd(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("data")){
				JSONObject data = json.getJSONObject("data");
				if(!data.isNull("password")){
					mPassword = data.getString("password");
				}
			}
			
			if(!json.isNull("state")){
				mState = new AppState(json.getJSONObject("state"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
