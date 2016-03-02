package com.haoqee.chatsdk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 群解析类
 *
 */
public class TCGroup implements Serializable{

	private static final long serialVersionUID = -1543436346354L;

	private String mGroupID = "";				//群ID
	private String mCreatorID = "";				//群创建者ID
	private String mCreatorName = "";			//群创建者名称
	private String mGroupName = "";				//群名称
	private String mGroupLogoSmall = "";		//群小头像
	private String mGroupLogoLarge = "";		//群大头像
	private String mGroupDescription = "";		//群简介
	private int mGroupMenberCount = 0;			//群成员数
	private long mCreateTime = 0;				//创建时间
	private int mGroupRole = 0;					//群角色		0--成员		1--群主
	private int mGroupIsJoin = 0;				//是否加入		0--未加入		1--已加入
	private int mGroupGetMsg = 1;				//是否接收消息	0--不接收		1--接收
	private String mJoinUids = "";				//已加入成员ID
	private List<TCUser> mUserList;				//临时会话中已存在的用户列表
	private HashMap<String, String> mExtendMap = new HashMap<String, String>();
	
	public TCGroup(){
		
	}
	
	public TCGroup(JSONObject json){
		try {
			mGroupID = json.getString("id");
			if(!json.isNull("uid")){
				mCreatorID = json.getString("uid");
			}
			
			if(!json.isNull("creator")){
				mCreatorName = json.getString("creator");
			}
			
			if(!json.isNull("name")){
				mGroupName = json.getString("name");
			}
			
			if(!json.isNull("logosmall")){
				mGroupLogoSmall = json.getString("logosmall");
			}
			
			if(!json.isNull("logolarge")){
				mGroupLogoLarge = json.getString("logolarge");
			}
			
			if(!json.isNull("description")){
				mGroupDescription = json.getString("description");
			}
			
			if(!json.isNull("count")){
				mGroupMenberCount = json.getInt("count");
			}
			
			if(!json.isNull("createtime")){
				mCreateTime = json.getLong("createtime");
			}
			
			if(!json.isNull("isjoin")){
				mGroupIsJoin = json.getInt("isjoin");
			}
			
			if(!json.isNull("role")){
				mGroupRole = json.getInt("role");
			}
			
			if(!json.isNull("getmsg")){
				mGroupGetMsg = json.getInt("getmsg");
			}
			
			if(!json.isNull("uids")){
				mJoinUids = json.getString("uids");
			}
			
			if(!json.isNull("list")){
				Object object = json.get("list");
				if(object instanceof JSONArray){
					JSONArray array = (JSONArray) object;
					if(array != null && array.length() != 0){
						mUserList = new ArrayList<TCUser>();
						for (int i = 0; i < array.length(); i++) {
							mUserList.add(new TCUser(array.getJSONObject(i)));
						}
					}
				}
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
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取群ID
	 * @return
	 */
	public String getGroupID(){
		return mGroupID;
	}
	
	/**
	 * 获取群创建者ID
	 * @return
	 */
	public String getCreatorID(){
		return mCreatorID;
	}
	
	/**
	 * 获取创建者名称
	 * @return
	 */
	public String getCreatorName(){
		return mCreatorName;
	}
	
	/**
	 * 获取群名称
	 * @return
	 */
	public String getGroupName(){
		return mGroupName;
	}
	
	/**
	 * 获取群小头像URL
	 * @return
	 */
	public String getGroupLogoSmall(){
		return mGroupLogoSmall;
	}
	
	/**
	 * 获取群大头像URL
	 * @return
	 */
	public String getGroupLogoLarge(){
		return mGroupLogoLarge;
	}
	
	/**
	 * 获取群描述
	 * @return
	 */
	public String getGroupDescription(){
		return mGroupDescription;
	}
	
	/**
	 * 获取群成员数
	 * @return
	 */
	public int getGroupMenberCount(){
		return mGroupMenberCount;
	}
	
	/**
	 * 获取群创建时间
	 * @return
	 */
	public long getGroupCreateTime(){
		return mCreateTime;
	}
	
	/**
	 * 获取在群中的角色
	 * @return	0--普通成员		1--管理员
	 */
	public int getGroupRole(){
		return mGroupRole;
	}
	
	/**
	 * 获取是否加入群
	 * @return	0--未加入			1--已加入
	 */
	public int getGroupIsJoin(){
		return mGroupIsJoin;
	}
	
	/**
	 * 获取是否接收消息
	 * @return	0--不接收			1--接收
	 */
	public int getGroupGetMsg(){
		return mGroupGetMsg;
	}
	
	/**
	 * 设置群ID
	 * @param groupid		群ID
	 */
	public void setGroupID(String groupid){
		this.mGroupID = groupid;
	}
	
	/**
	 * 设置群创建者ID
	 * @param creatorID			群创建者ID
	 */
	public void setGroupCreatorID(String creatorID){
		this.mCreatorID = creatorID;
	}
	
	/**
	 * 设置群创建者昵称
	 * @param creatorName		群创建者昵称
	 */
	public void setGroupCreatorName(String creatorName){
		this.mCreatorName = creatorName;
	}
	
	/**
	 * 设置群名称
	 * @param groupName			群名称
	 */
	public void setGroupName(String groupName){
		this.mGroupName = groupName;
	}
	
	/**
	 * 设置群小头像URL
	 * @param groupLogoSmall	群小头像URL
	 */
	public void setGroupLogoSmall(String groupLogoSmall){
		this.mGroupLogoSmall = groupLogoSmall;
	}
	
	/**
	 * 设置群大头像URL
	 * @param groupLogoLarge	群大头像URL
	 */
	public void setGroupLogoLarge(String groupLogoLarge){
		this.mGroupLogoLarge = groupLogoLarge;
	}
	
	/**
	 * 设置群描述
	 * @param groupDescription	群描述
	 */
	public void setGroupDescription(String groupDescription){
		this.mGroupDescription = groupDescription;
	}
	
	/**
	 * 设置群成员数
	 * @param count				群成员数
	 */
	public void setGroupMenberCount(int count){
		this.mGroupMenberCount = count;
	}
	
	/**
	 * 设置群创建时间
	 * @param time				群创建时间
	 */
	public void setGroupCreateTime(long time){
		this.mCreateTime = time;
	}
	
	/**
	 * 设置群角色
	 * @param role				群角色	0--普通群成员		1--管理员 
	 */
	public void setGroupRole(int role){
		this.mGroupRole = role;
	}
	
	/**
	 * 设置是否加入该群
	 * @param isJoin			是否加入	0--未加入			1--已加入
	 */
	public void setGroupIsJoin(int isJoin){
		this.mGroupIsJoin = isJoin;
	}
	
	/**
	 * 设置是否接收该群消息通知
	 * @param getMsg			是否接收	0--不接收			1--接收 			默认接收
	 */
	public void setGroupGetMsg(int getMsg){
		this.mGroupGetMsg = getMsg;
	}
	
	/**
	 * 获取已加入的成员ID串
	 * @return
	 */
	public String getJoinUids(){
		return mJoinUids;
	}

	/**
	 * 获取临时会话成员列表
	 * @return
	 */
	public List<TCUser> getUserList(){
		return mUserList;
	}
	
	/**
	 * 设置群成员列表
	 * @param userList
	 */
	public void setUserList(List<TCUser> userList){
		mUserList = userList;
	}
	
	/**
	 * 获取群扩展Map
	 * @return
	 */
	public HashMap<String, String> getExtendMap(){
		return mExtendMap;
	}
}
