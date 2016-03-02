package com.haoqee.chat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserList implements Serializable{

	private static final long serialVersionUID = 1156435454541L;
	
	public List<User> mUserList;
	public AppState mState;
	public PageInfo mPageInfo;

	public UserList(){}
	
	public UserList(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("data")){
				JSONArray array = json.getJSONArray("data");
				if(array != null){
					mUserList = new ArrayList<User>();
					for (int i = 0; i < array.length(); i++) {
						mUserList.add(new User(array.getJSONObject(i)));
					}
				}
			}
			
			if(!json.isNull("state")){
				mState = new AppState(json.getJSONObject("state"));
			}
			
			if(!json.isNull("pageInfo")){
				mPageInfo = new PageInfo(json.getJSONObject("pageInfo"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
