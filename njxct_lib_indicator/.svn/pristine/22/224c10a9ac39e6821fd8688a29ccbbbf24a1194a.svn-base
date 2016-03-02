package com.haoqee.chatsdk.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class TCMessageResult implements Serializable{

	private static final long serialVersionUID = -1436465487871L;
	
	public TCMessage mMessageInfo;
	public TCState mState;
	
	public TCMessageResult(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("state")){
				mState = new TCState(json.getJSONObject("state"));
			}
			
			if(!json.isNull("data")){
				mMessageInfo = new TCMessage(json.getJSONObject("data"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
