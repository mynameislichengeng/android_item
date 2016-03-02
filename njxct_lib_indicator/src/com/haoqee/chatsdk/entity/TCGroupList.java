package com.haoqee.chatsdk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 群列表解析数据类
 *
 */
public class TCGroupList implements Serializable{

	private static final long serialVersionUID = -11454246587L;

	public List<TCGroup> mGroupList;					//群列表
	public TCState mState;							//结果状态信息
	public TCPageInfo mPageInfo;						//分页信息
	
	public TCGroupList(){}
	
	public TCGroupList(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			
			if(!json.isNull("state")){
				mState = new TCState(json.getJSONObject("state"));
				if(mState != null && mState.code == 0){
					Object object = json.get("data");
					if(object instanceof JSONArray){
						JSONArray data = (JSONArray) object;
						if(data != null && data.length() != 0){
							mGroupList = new ArrayList<TCGroup>();
							for (int i = 0; i < data.length(); i++) {
								mGroupList.add(new TCGroup(data.getJSONObject(i)));
							}
						}
					}
					
					if(!json.isNull("pageInfo")){
						mPageInfo = new TCPageInfo(json.getJSONObject("pageInfo"));
					}
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
