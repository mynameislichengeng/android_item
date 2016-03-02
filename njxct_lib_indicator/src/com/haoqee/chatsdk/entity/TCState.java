package com.haoqee.chatsdk.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class TCState implements Serializable{

	private static final long serialVersionUID = 149681634654564865L;
	
	public int code = -1;
	public String errorMsg = "";
	public String debugMsg = "";
	
	public TCState mState;
	
	public TCState(){}
	
	protected JSONObject init_state(String reString) throws JSONException{
		JSONObject json = new JSONObject(reString);
		if(json != null && !json.isNull("state")){
			mState = new TCState(json.getJSONObject("state"));
		}
		
		return json;
		
	}
	
	public TCState(String reString) {
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("state")){
				mState = new TCState(json.getJSONObject("state"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public TCState(JSONObject json) {
		try {
			if(!json.isNull("code")){
				code = json.getInt("code");
			}
			
			if(!json.isNull("msg")){
				errorMsg = json.getString("msg");
			}
			
			if(!json.isNull("debugMsg")){
				debugMsg = json.getString("debugMsg");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
