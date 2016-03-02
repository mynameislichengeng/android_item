package com.haoqee.chat.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginResult implements Serializable{

	private static final long serialVersionUID = 113454353454L;
	
	public User mUser;
	public AppState mState;

	
	public LoginResult(){}
	
	public LoginResult(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			
			if(!json.isNull("data")){
				mUser = new User(json.getJSONObject("data"));
			}
			
			if(!json.isNull("state")){
				mState = new AppState(json.getJSONObject("state"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
